package com.course;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class User {

    static WebDriver browser;
    static String baseURL = "http://jira.hillel.it:8080/";
    static String username = "autorob";
    static String password = "forautotests";
    static long randomId;
    static String user_firstname = "TestF";
    static String user_lastname = "TestL";
    static String user_password = "123";

    @BeforeTest
    protected static void setUp() {
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized", "--incognito");
        browser = new ChromeDriver(options);
        browser.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        browser.get(baseURL);
    }

    @Test(description = "Login")
    private void logIn() {
        clearAndFill(By.cssSelector("input#login-form-username"), username);
        clearAndFill(By.cssSelector("input#login-form-password"), password).submit();

        Assert.assertEquals(username,
                browser.findElement(By.cssSelector("a#header-details-user-fullname")).getAttribute("data-username"));
    }

    @Test(description = "Create User", dependsOnMethods = {"Login"})
    private void createUser() throws InterruptedException {
        browser.findElement(By.cssSelector("a#admin_users_menu")).click();

        browser.findElement(By.cssSelector("a#create_user")).click();
        randomId = Math.round(Math.random() * 10000);
        clearAndFill(By.cssSelector("input#user-create-email"), "Test" + randomId);
        clearAndFill(By.cssSelector("input#luser-create-fullname"), user_firstname);
        clearAndFill(By.cssSelector("input#luser-create-fullname"), user_lastname);
        clearAndFill(By.cssSelector("input#luser-create-fullname"), user_password);
        browser.findElement(By.cssSelector("a.user-create-submit")).click();
    }

    @Test(description = "Verify created user", dependsOnMethods = {"createUser"})
    private void verifyCreatedUser() {
        browser.findElement(By.cssSelector("a#admin_users_menu")).click();
        filterByUser();
        browser.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        Assert.assertTrue(browser.getTitle().contains("Test" + randomId));
    }

    @Test(description = "Remove user", dependsOnMethods = {"verifyCreated?User"})
    private void removeUser() {
        filterByUser();
        browser.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        By row = By.cssSelector("#user_browser_table tr");
        String userId = browser.findElement(row).getAttribute("data-user");

        By.cssSelector("#user_browser_table tr aui-iconfont-moreaui-iconfont-more").findElement(browser).click();
        By.cssSelector("#deleteuser_link_" + userId).findElement(browser).click();

        filterByUser();
        browser.findElement(By.cssSelector("#jira-adbox jira-adbox-medium no-results user-browser__row-empty"));

    }

    @AfterTest
    private void finish() {
        browser.close();
    }

    public static WebElement clearAndFill(By selector, String data) {
        WebElement element = browser.findElement(selector);
        element.clear();
        element.sendKeys(data);

        return element;

    }

    public void filterByUser() {
        clearAndFill(By.cssSelector("#user-filter-userSearchFilter"), "Test" + randomId).submit();
    }

}