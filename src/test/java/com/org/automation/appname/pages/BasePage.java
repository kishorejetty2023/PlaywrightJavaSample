package com.org.automation.appname.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class BasePage {

    protected Page page;


    public BasePage(Page page) {
        this.page = page;
    }
    public Locator gtPageHeader(){
        return this.page.locator("mat-toolbar-row h1");
    }
    public void clickLogoutButton(){
        this.getLogoutButton().click();
    }
    public Locator getAdminMenu(){
        return this.page.locator("mat-icon[aria-label='Admin Menu']");
    }
    public Locator getAdminMenueItems(){
        return this.page.locator("div.mat-menu-content button.mat-menu-item");
    }

    private Locator getLogoutButton() {
        return this.page.locator("button[aria-label='Logout']");
    }
}
