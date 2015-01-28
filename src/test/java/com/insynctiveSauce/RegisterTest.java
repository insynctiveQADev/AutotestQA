package com.insynctiveSauce;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;

import static org.junit.Assert.assertTrue;

public class RegisterTest extends TestbaseSauce {
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Test
    public void registerTest() throws Exception {
        driver.get(baseUrl + "/Insynctive.Hub/Login.aspx?ReturnUrl=%2fInsynctive.Hub%2fProtected%2fInvitations.aspx%3fpage&page");
        driver.findElement(By.xpath("//input[@id='login_UserName_I']")).clear();
        driver.findElement(By.xpath("//input[@id='login_UserName_I']")).sendKeys("ivolf@insynctive.com");
        driver.findElement(By.id("login_Password_I")).clear();
        driver.findElement(By.id("login_Password_I")).sendKeys("Iakov1893250");
        driver.findElement(By.id("login_Login_CD")).click();
        driver.findElement(By.xpath("//input[@name='login$Login']")).click();
        driver.findElement(By.id("tds_img_Invitations")).click();
        driver.findElement(By.xpath("//div[@id='tds_body_mainTab_TPTCR_btnAddPerson_0_CD']/span")).click();
        driver.findElement(By.id("tds_body_popupAddPerson_txtFirstName_I")).clear();
        driver.findElement(By.id("tds_body_popupAddPerson_txtFirstName_I")).sendKeys("Morgan");
        driver.findElement(By.id("tds_body_popupAddPerson_txtLastName_I")).clear();
        driver.findElement(By.id("tds_body_popupAddPerson_txtLastName_I")).sendKeys("Freeman");
        driver.findElement(By.id("tds_body_popupAddPerson_txtEmail_I")).clear();
        driver.findElement(By.id("tds_body_popupAddPerson_txtEmail_I")).sendKeys("volf1212+4@gmail.com");
        driver.findElement(By.id("tds_body_popupAddPerson_chkInvite_S_D")).click();
        driver.findElement(By.id("tds_body_popupAddPerson_btnAddHRAdmin")).click();
        driver.findElement(By.xpath("//input[@name='ctl00$ctl00$tds$body$popupAddPerson$btnAddHRAdmin']")).click();
        driver.findElement(By.id("txtNewPassword_I")).clear();
        driver.findElement(By.id("txtNewPassword_I")).sendKeys("Iakov1893250");
        driver.findElement(By.id("txtConfirmNewPassword_I")).clear();
        driver.findElement(By.id("txtConfirmNewPassword_I")).sendKeys("Iakov1893250");
        driver.findElement(By.xpath("//div[@id='btnChangePassword_CD']/span")).click();
        driver.findElement(By.xpath("//input[@name='btnChangePassword']")).click();
        driver.findElement(By.id("pcMessage_TPCFm1_btnOk_CD")).click();
        driver.findElement(By.xpath("//input[@name='pcMessage$TPCFm1$ctl00$btnOk']")).click();
        driver.findElement(By.id("tds_body_newsTab_TPTCR_imgMore_0")).click();
        try {
            assertTrue(driver.findElement(By.id("lblName")).getText().matches("^[\\s\\S]*Morgan[\\s\\S]*Freeman[\\s\\S]*$"));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
        // ERROR: Caught exception [Error: locator strategy either id or name must be specified explicitly.]
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