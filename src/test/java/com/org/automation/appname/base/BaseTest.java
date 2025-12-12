package com.org.automation.appname.base;

import com.microsoft.playwright.Page;
import com.org.automation.appname.factories.BrowserFactory;
import com.org.automation.appname.utils.AESUtil;
import com.org.automation.appname.utils.Retry;
import lombok.extern.slf4j.Slf4j;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@Slf4j
public class BaseTest {

  //Created now
  private final BrowserFactory browserFactory = new BrowserFactory();
  protected Properties properties = browserFactory.initializeProperties();
  protected Page page;
  public static String env;
  public static String url;
  protected String uName;
  protected String uPass;
  private String browserName;
    {

    }

    /**
   * Sets up the test environment before each test.
   * Initializes the browser and initializes the search and landing pages.
   * @throws IllegalArgumentException if the invalid browser name or headless value is provided.
   */
  public void setUp() {

    env =  System.getProperty("env", "dev");
    String headless = System.getProperty("headless", "false");
    browserName = properties.getProperty("browser");
    try {
      page = browserFactory.initializeBrowser(browserName, headless);
    }catch(IllegalArgumentException e){
        log.error("Invalid browser name or headless value provided: ", e);
    }
    url = properties.getProperty(BaseTest.env+".appname.hostname");
    String key = properties.getProperty("EncryptionKey");
    uName = properties.getProperty("username").trim();
    uPass = "";
    try {
      uPass = AESUtil.decrypt(properties.getProperty("encryptedPwd").trim(), key);
    }catch (Exception e){
        log.error("Error while decrypting the password: ", e);
    }

  }

  /**
   * Tears down the testing environment after each test.
   * Closes the browser and takes a screenshot if the test fails.
   *
   * @param result The result of the test execution.
   */
  @AfterMethod
  public void tearDown(ITestResult result) {
    String testName = result.getMethod().getTestClass().getXmlTest().getName();
    IRetryAnalyzer retry = result.getMethod().getRetryAnalyzer(result);
    int retryCount = 0;
    if (retry instanceof Retry){
      retryCount = ((Retry) retry).getRetryCount();
    }
    browserFactory.setTraces(testName+"_"+retryCount);
    if(result.getStatus() == ITestResult.FAILURE){
      LocalDateTime currentDateTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd_HH-mm-ss");
      String formattedDateTime = currentDateTime.format(formatter);
      String resultName = result.getName()+ "_"+env+"_"+browserName+"_"+formattedDateTime;

      Path screenshotPath = Paths.get("./target/Screenshots/", resultName + ".png");
        page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
      log.error("Test {} failed. Taking screenshot.", result.getName());
    }
    page.context().browser().close();
  }
}
