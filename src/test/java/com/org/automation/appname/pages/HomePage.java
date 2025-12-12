package com.org.automation.appname.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.org.automation.appname.pages.admin.AdminPage;

public class HomePage extends BasePage {

  private Page page;

  public HomePage(Page page) {
      super(page);
      this.page = page;
  }

  public Locator getHomePageHeader() {
    return this.page.locator("div#content h2");
  }
  public Locator getSearch() {
    return this.page.locator("xpath=//input[@name='search']");
  }

  public String getSearchBtn() {
    return "div#search button";
  }

  public String getMyAccountBtn() {
    return "//span[normalize-space()='My Account']";
  }

  public String getLoginBtn() {
    return "xpath=//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Login']";
  }

  public AdminPage goToAdminPage() {
      this.getAdminMenu().click();
      // wait for the first admin menu item to be visible before interacting
      this.getAdminMenueItems().first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
      this.getAdminMenueItems().nth(0).click();
      return new AdminPage(page);
  }
}
