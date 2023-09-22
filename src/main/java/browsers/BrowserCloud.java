package browsers;

import com.browserstack.local.Local;
import org.json.simple.JSONObject;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.RemoteWebDriver;
import static helpers.BasePage.*;
import static helpers.Utilities.*;

import java.net.URL;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrowserCloud implements InterfaceBrowsers {
    private Local localTunnel;
    private static final Logger logger = Logger.getLogger(BrowserCloud.class.getName());
    Map<String, String> localArgs = new HashMap<>();
    Map<String, Object> cloudOptions = new HashMap<>();
    private MutableCapabilities caps;
    private boolean local = false;

    public BrowserCloud() {
        caps = new MutableCapabilities();
    }

    public void createBrowser() throws Exception {
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--ignore-certificate-errors", "--remote-allow-origins=*");
        chromeOptions.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        if (isBrowser("chrome")) caps = caps.merge(chromeOptions);

        pasrseJSON("capabilities");
        pasrseJSON("platforms");

        if (local) {
            // start local tunnel for firefox browser
            localTunnel = new Local();
            logger.info("start local tunnel");

            localArgs.put("key", BC_KEY);
            localArgs.put("-force-local", "");

            localTunnel.start(localArgs);
            logger.info("wait for local tunnel");

            Instant startedAt = Instant.now();
            while (!localTunnel.isRunning()) {
                if (startedAt.plusSeconds(2).isBefore(Instant.now())) break;
            }
            logger.info("local tunnel started");
        }

        driver = new RemoteWebDriver(new URL("https://" + BC_USER + ":" + BC_KEY + "@" + configs.get("server") + "/wd/hub"), caps);
        driver.manage().window().maximize();
    }

    @Override
    public void stop() {
        // stop the local tunnel
        try {
            if (localTunnel != null) localTunnel.stop(localArgs);
        } catch (Exception e) {
            logger.warning("Could not stop tunnel");
        }
    }

    public void pasrseJSON(String string) {
        // parse the platforms and capabilities from configs/browserCloud.json
        JSONObject json = (JSONObject) configs.get(string);
        if (string.equals("platforms")) json = (JSONObject) json.get(browserName);


        Map<String, Object> node = (Map<String, Object>) json;
        for (Map.Entry<String, Object> entry : node.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();

            cloudOptions.put(key, val);
            if (key.equals("local")) local = true;
        }

        caps.setCapability("bstack:options", cloudOptions); // bstack:options for browserstack
    }
}