package com.insynctiveSauce;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Listeners({SauceOnDemandTestListener.class})
public class SauceNewLoginTest implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    public SauceOnDemandAuthentication authentication;

    private WebDriver driver;

    @Parameters({"username", "key", "os", "browser", "browserVersion"})
    @BeforeMethod
    // Note: XP is tested as Windows 2003 Server on the Sauce Cloud
    public void setUp
            (String username,
             String key,
             String os,
             String browser,
             String browserVersion, Method method) throws Exception {

        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(key)) {
            authentication = new SauceOnDemandAuthentication(username, key);
        } else {
            authentication = new SauceOnDemandAuthentication();
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser);
        capabilities.setCapability("version", browserVersion);
        capabilities.setCapability("platform", Platform.valueOf(os));
        capabilities.setCapability("name", method.getName());
        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"), capabilities);
    }

    @Override
    public String getSessionId() {
        SessionId sessionId = ((RemoteWebDriver)driver).getSessionId();
        return (sessionId == null) ? null : sessionId.toString();
    }

    //@Test
    public void webDriverWithHelper() throws Exception {
        driver.get("http://www.amazon.com/");
        assertEquals("Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more", driver.getTitle());
    }
    @Test
    public void testLoginSuccsess () throws Exception {


        driver.get("https://alpha.insynctiveapps.com");
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
    @AfterMethod
    public void tearDown() throws Exception {
        driver.quit();
    }

    @Override
    public SauceOnDemandAuthentication getAuthentication() {
        return authentication;
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