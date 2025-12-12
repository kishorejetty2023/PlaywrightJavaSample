package com.org.automation.appname.factories;

import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BrowserFactory {

  private Properties properties;
  private BrowserContext browserContext;

    public Page initializeBrowser(String browserName, String headless) {
    boolean isHeadless = Boolean.parseBoolean(headless);
    Playwright playwright = Playwright.create();
    Browser browser = getBrowser(playwright,browserName,isHeadless);
    Browser.NewContextOptions options = new Browser.NewContextOptions().setViewportSize(1920, 1080)
            .setIgnoreHTTPSErrors(true)
            .setPermissions(Arrays.asList("clipboard-read","clipboard-write"));
    browserContext = browser.newContext(options);
    //set the default timeout
    browserContext.setDefaultTimeout(Long.parseLong(properties.getProperty("playwright.default.timeout")));

    //start tracing before creating / navigating a page.
    browserContext.tracing().start(new Tracing.StartOptions()
            .setScreenshots(Boolean.parseBoolean(properties.getProperty("playwright.tracing.screenshots")))
            .setSnapshots(Boolean.parseBoolean(properties.getProperty("playwright.tracing.snapshots")))
            .setSources(Boolean.parseBoolean(properties.getProperty("playwright.tracing.sources"))));
    return browserContext.newPage();
  }

  private Browser getBrowser(Playwright playwright, String browserName, boolean isHeadless) {
    JsonObject capabilities = new JsonObject();
   // JsonObject ltOptions = new JsonObject();
    capabilities.addProperty("browserName","Chrome");
    capabilities.addProperty("browserVersion","latest");

   // ltOptions.add
    return switch (browserName.toLowerCase()){
      case "chromium" -> {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions();
        options.setHeadless(isHeadless)
                .setChannel("chrome");
        yield playwright.chromium().launch(options);
      }
      case "firefox" -> {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions();
        options.setHeadless(isHeadless)
                .setChannel("firefox");
        yield playwright.firefox().launch(options);
      }
      case "webkit" -> {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions();
        options.setHeadless(isHeadless)
                .setChannel("webkit");
        yield playwright.webkit().launch(options);
      }
      default -> throw new IllegalStateException("Unexpected value: " + browserName);
    };
  }
  /** Initializes the configuration properties from the config.properties, <env>.data.properties files
   *
   * @return The Properties instance containing all the configuration properties
   */
  public Properties initializeProperties() {
    ClassLoader classLoader = getClass().getClassLoader();
    String configUrl = Objects.requireNonNull(classLoader.getResource("config.properties")).getPath();

    try(FileInputStream fis = new FileInputStream(configUrl)) {
      properties = new Properties();
      properties.load(fis);
    } catch (Exception e) {
      log.error("Exception occurred while loading config.properties file: ", e);
    }
    String env = System.getProperty("env", "dev");
    System.out.println("Running tests on environment: " + env);
    String dataConfig = env.toLowerCase() + ".data.properties";
    String dataConfigUrl = Objects.requireNonNull(classLoader.getResource(dataConfig)).getPath();
    try (FileInputStream dataFile = new FileInputStream(dataConfigUrl)) {
        properties.load(dataFile);
    } catch (Exception ex) {
        log.error("Exception occurred while loading {} file: ", dataConfig, ex);
    }

    return properties;
  }

  /**
   * Stops the tracing in the browser context and saves the trace to a file.
   */
  public void setTraces(String traceName){
    browserContext.tracing().stop(new Tracing.StopOptions()
            .setPath(Paths.get(properties.getProperty("traceDir")+traceName+".zip")));
  }
}
