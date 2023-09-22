package browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import static helpers.BasePage.*;


public class LocalFirefox implements InterfaceBrowsers {

    @Override
    public void createBrowser() {
        // Use firefox driver from here
        WebDriverManager.firefoxdriver().setup();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        //FirefoxOptions options = new FirefoxOptions().merge(capabilities);

        capabilities.setCapability("marionette", false);
        driver = new FirefoxDriver();
    }
}