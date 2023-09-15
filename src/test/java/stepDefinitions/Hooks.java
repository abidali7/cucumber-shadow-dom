package stepDefinitions;

import driver.BrowserCloud;
import driver.BrowserInterface;
import driver.Chrome;
import driver.Firefox;
import io.cucumber.java.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static helpers.BasicPage.*;
import static helpers.Utilities.*;


public class Hooks {

    private static BrowserInterface browserStrategy;
    private static final String systemUserName = System.getProperty("user.name");
    private static final String browserChoice = System.getProperty("browser");
    private static final String browserCloudEnv = System.getProperty("browserCloudEnv");
    private static String projectName = System.getenv("GITHUB_ACTOR"); // User will act as project name
    private static String buildName = System.getenv("GITHUB_RUN_ID"); // Build Id
    private static boolean isFailed;

    @BeforeAll
    public static void beforeAll() {
        BASEURL = (String) configs.get("base_url_prod"); // Set up the BASEURL
        String tag = System.getProperty("cucumber.filter.tags"); // get cucumber runtime tags

        browserCloudCredentials(); // set up browserCloud credentials
        browserName = browserChoice.equals("browserCloud") && !isStrNull(browserCloudEnv) ? browserCloudEnv : browserChoice; // Set up browser, either local or cloud based

        // initilise browserCloud based items
        projectName = !isStrNull(projectName) ? projectName : systemUserName;
        buildName = !isStrNull(buildName) ? buildName + System.getenv("GITHUB_RUN_NUMBER") : "test_local";
        String sessionName = buildName + "[" + tag + "]";

        // overwrite existing null values for browserCloud
        JSONObject json = (JSONObject) configs.get("capabilities");
        json.replace("sessionName", sessionName);
        json.replace("projectName", projectName);
        json.replace("buildName", buildName);

        // Start the browsers
        browserStrategy = browserChoice.equals("browserCloud") ? new BrowserCloud() : (browserChoice.equals("chrome") ? new Chrome() : new Firefox());
        try {
            browserStrategy.createWebdriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @AfterAll
    public static void afterAll() throws URISyntaxException, IOException {
        String sessionID = ((RemoteWebDriver) driver).getSessionId().toString();
        URI uri = new URI("https://" + BC_USER + ":" + BC_KEY + "@api.browserstack.com/automate/sessions/" + sessionID + ".json");
        HttpPut putRequest = new HttpPut(uri);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add((new BasicNameValuePair("status", isFailed ? "FAILED" : "PASSED")));
        //nameValuePairs.add((new BasicNameValuePair("reason", "")));

        putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpClientBuilder.create().build().execute(putRequest);

        if (driver != null) driver.quit();
        if (browserStrategy != null) browserStrategy.stop();
    }

    @Before
    public void initializeTests(Scenario scenario) {
        cucumberScenario = scenario;
        //Collection<String> tags = scenario.getSourceTagNames();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            isFailed = true;
            takeScreenshot();
        }
    }
}
