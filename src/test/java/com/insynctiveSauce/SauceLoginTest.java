package com.insynctiveSauce;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SauceLoginTest extends TestbaseSauce {
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriver driver;

    @Test
    public void testLogin() throws Exception {
        driver.get(baseUrl + "");
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (isElementPresent(By.id("login_Login_CD"))) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("login_UserName_I")).sendKeys("ppetrea@mystaffdesk.com");
        driver.findElement(By.id("PasswordLabel")).click();
        driver.findElement(By.id("login_Password_I")).sendKeys("123qwe");
        driver.findElement(By.id("login_Login_CD")).click();
        for (int second = 0;; second++) {
            if (second >= 70) fail("timeout");
            try { if (isElementPresent(By.id("tds_body_newsTab_AT0T"))) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }

        try {
            assertEquals("GETTING STARTED", driver.findElement(By.id("tds_body_newsTab_AT0T")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }


    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
