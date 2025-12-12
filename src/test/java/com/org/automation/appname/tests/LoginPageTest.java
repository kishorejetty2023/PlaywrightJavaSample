package com.org.automation.appname.tests;

import com.org.automation.appname.base.BaseTest;
import com.org.automation.appname.pages.HomePage;
import com.org.automation.appname.pages.LoginPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
public class LoginPageTest extends BaseTest {

    private HomePage homePage;


    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        log.info("Setting up HomePage instance before each test method.");
        super.setUp();
        homePage = new LoginPage(page, url).Login(uName, uPass);
    }

    @Test(groups = {"smoke", "regression"}, description = "Admin Login Test")
    public void verifyAdminLoginTest() {
        assertThat(homePage.getHomePageHeader()).containsText("App Name Dashboard");
        log.info("Admin Login Test executed successfully.");
        assertThat(homePage.getSearch()).isVisible();
    }


}
