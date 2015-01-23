package com.insynctive;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PasswordChange extends TestBase {
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Test
    public void testPasswordChange() throws Exception {
        driver.get(baseUrl + "/Insynctive.Hub/Login.aspx?ReturnUrl=%2fInsynctive.Hub%2f");
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (isElementPresent(By.id("login_UserName_I"))) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("login_UserName_I")).clear();
        driver.findElement(By.id("login_UserName_I")).sendKeys("dzonovm+19@gmail.com");
        driver.findElement(By.id("PasswordLabel")).click();
        driver.findElement(By.id("login_Password_I")).clear();
        driver.findElement(By.id("login_Password_I")).sendKeys("test123");
        driver.findElement(By.id("login_Login_CD")).click();
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (isElementPresent(By.xpath("//img[@onclick='javascript:popupAccount.Show();']"))) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.xpath("//img[@onclick='javascript:popupAccount.Show();']")).click();
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (isElementPresent(By.id("popupAccount_linkChangePass"))) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.id("popupAccount_linkChangePass")).click();
        try {
            assertEquals("Change password", driver.findElement(By.id("lblTitle")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.id("txtOldPassword_I")).clear();
        driver.findElement(By.id("txtOldPassword_I")).sendKeys("test123");
        driver.findElement(By.id("txtNewPassword_I")).clear();
        driver.findElement(By.id("txtNewPassword_I")).sendKeys("test123");
        driver.findElement(By.id("txtConfirmNewPassword_I")).clear();
        driver.findElement(By.id("txtConfirmNewPassword_I")).sendKeys("test123");
        driver.findElement(By.xpath("//div[@id='btnChangePassword_CD']/span")).click();

        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (isElementPresent(By.id("pcMessage_lblMessage"))) break;
            } catch (Exception e) {}
            Thread.sleep(1000);
        }
        try {
            assertEquals("Your password for accessing our website was updated successfully.", driver.findElement(By.id("pcMessage_lblMessage")).getText());
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        driver.findElement(By.id("pcMessage_TPCFm1_btnOk_CD")).click();

    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
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
