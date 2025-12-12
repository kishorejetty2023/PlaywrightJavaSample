package com.org.automation.appname.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {

  private Page page;
  private final String launchUrl ;

  public LoginPage(Page page, String launchUrl) {
      super(page);
      this.page = page;
      this.launchUrl = launchUrl;
  }
  public Locator getUserNameInput(){ return this.page.locator("#username"); }
  public Locator getPasswordInput(){ return this.page.locator("#password"); }
  public Locator getLoginButton(){ return this.page.locator("button[name='login']"); }

  public String getLoginPageTitle() {
    return page.title();
  }

  public HomePage Login(String username, String pass) {
    this.page.navigate(launchUrl);
    getUserNameInput().fill(username);
    getPasswordInput().fill(pass);
    getLoginButton().click();
    return new HomePage(page);
  }
}
