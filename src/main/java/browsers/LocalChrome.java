package browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import static helpers.BasePage.*;

import java.util.logging.Level;

public class LocalChrome implements InterfaceBrowsers {
    @Override
    public void createBrowser() {
        MutableCapabilities caps = new MutableCapabilities();
        WebDriverManager.chromedriver().setup();

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        logPrefs.enable(LogType.DRIVER, Level.ALL);

        caps.setCapability("goog:loggingPrefs", logPrefs);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito", "--start-fullscreen");
        chromeOptions.addArguments("--ignore-certificate-errors", "--remote-allow-origins=*");
        chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        chromeOptions = chromeOptions.merge(caps);

        driver = new ChromeDriver(chromeOptions);
        System.out.println("driver chrome: " + driver);
    }
}

