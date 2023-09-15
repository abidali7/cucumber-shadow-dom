package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import static helpers.BasicPage.*;


public class Firefox implements BrowserInterface {

    @Override
    public void createWebdriver() {
        // Use firefox driver from here
        WebDriverManager.firefoxdriver().setup();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        //FirefoxOptions options = new FirefoxOptions().merge(capabilities);

        capabilities.setCapability("marionette", false);
        driver = new FirefoxDriver();
    }
}