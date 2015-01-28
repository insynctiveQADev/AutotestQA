package com.insynctiveSauce;

import com.insynctive.util.PropertyLoader;
import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.testng.SauceOnDemandAuthenticationProvider;
import com.saucelabs.testng.SauceOnDemandTestListener;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.*;
import ru.stqa.selenium.factory.WebDriverFactory;
import ru.stqa.selenium.factory.WebDriverFactoryMode;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * Base class for TestNG-based test classes
 */
@Listeners({SauceOnDemandTestListener.class})
public class TestbaseSauce implements SauceOnDemandSessionIdProvider, SauceOnDemandAuthenticationProvider {

  public SauceOnDemandAuthentication authentication;

  protected WebDriver driver;
  protected static String gridHubUrl;
  protected static String baseUrl;
  protected static Capabilities capabilities;



  @BeforeSuite
  public void initTestSuite() throws IOException {
    baseUrl = PropertyLoader.loadProperty("site.url");
    gridHubUrl = PropertyLoader.loadProperty("grid.url");
    if ("".equals(gridHubUrl)) {
      gridHubUrl = null;
    }
    //capabilities = PropertyLoader.loadCapabilities();
    WebDriverFactory.setMode(WebDriverFactoryMode.THREADLOCAL_SINGLETON);
  }

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



  @AfterMethod
  public void tearDown() throws Exception {
    driver.quit();
  }

  @Override
  public SauceOnDemandAuthentication getAuthentication() {
    return authentication;
  }
}