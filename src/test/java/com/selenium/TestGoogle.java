package com.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestGoogle {
    private WebDriver driver;

    @BeforeTest
    public void setup() {
        // Use WebDriverManager to automatically handle ChromeDriver setup
        WebDriverManager.chromedriver().setup();

        // Configure ChromeOptions for headless mode (necessary for Docker)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run Chrome in headless mode
        options.addArguments("--no-sandbox"); // Bypass OS security model in Docker
        options.addArguments("--disable-dev-shm-usage"); // Overcome limited resource issues

        // Initialize WebDriver with options
        driver = new ChromeDriver(options);
    }

    @Test
    public void testGoogleTitle() {
        driver.get("https://www.google.com/");
        String title = driver.getTitle();
        System.out.println("Page Title: " + title);
        Assert.assertTrue(title.contains("Google"), "Page title does not contain 'Google'");
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}