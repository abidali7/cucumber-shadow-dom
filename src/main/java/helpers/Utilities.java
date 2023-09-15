package helpers;

import com.google.common.base.Strings;
import io.cucumber.java.Scenario;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.List;

public class Utilities {
    // declarations used across different files
    public static JSONObject configs;
    public static String BC_USER = System.getenv("BC_USER");
    public static String BC_KEY = System.getenv("BC_KEY");
    public static String BASEURL;
    public static String browserName;
    public static boolean isMobile;
    public static Scenario cucumberScenario;

    static {
        try {
            configs = (JSONObject) new JSONParser().parse(new FileReader("configs/browserCloud.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static boolean isStrNull(String str) {
        return Strings.isNullOrEmpty(str);
    }

    public static boolean isBrowser(String browser) {
        return (browserName.split("_")[0]).matches(browser);
    }

    public static boolean sleep(int sec) {
        try {
            Thread.sleep(1000 * sec);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void browserCloudCredentials() {
        BC_USER = isStrNull(BC_USER) ? (String) configs.get("bcUser") : null;
        BC_KEY = isStrNull(BC_KEY) ? (String) configs.get("bcKey") : null;
    }
}
