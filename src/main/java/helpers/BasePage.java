package helpers;

import io.github.sukgu.Shadow;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;

import static helpers.InnerBasePage.*;
import static helpers.Utilities.*;

public class BasePage {
    // contain all functions which are directly linked with steps definitions
    public static WebDriver driver;

    public BasePage() {
        innerDriver = driver;
        shadow = new Shadow(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean waitByUrl(String url) {
        return ecBoolean(ExpectedConditions.urlContains(url));
    }

    public boolean waitByTitle(String text) {
        return ecBoolean(ExpectedConditions.titleContains(text));
    }

    public boolean waitByAtt(String attFor, String text) {
        return ecBoolean(ExpectedConditions.attributeContains(ele, attFor, text));
    }

    public void clickable() {
        try {
            waitBy().until(ExpectedConditions.elementToBeClickable(ele)).click();
        } catch (Exception e) {
            if (scrollByElement()) waitBy().until(ExpectedConditions.elementToBeClickable(ele)).click();
        }
    }

    public void clickByText(String text) {
        if (getTextByText(text)) clickable();
    }

    public void clickByText(String text1, String text2) {
        boolean isTrue = text2 == null && getTextByText(text1) && getElementAssign();
        if (isTrue) clickable();
    }

    public boolean isVisible() {
        return waitVisibility() && scrollByElement();
    }

    public void mouseOverByIndex(int index) {
        getElementByIndex(index);
        if (scrollByElement()) new Actions(driver).clickAndHold(ele).build().perform();
    }

    public void getUrlTo(String string) {
        if (isStrNull(string)) string = BASEURL;
        try {
            driver.get(string);
        } catch (Exception e) {
            cookiesDeleteAll();
            driver.navigate().to(string);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }
    }

    public void priceRange() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (WebElement ele : eles) {
            arrayList.add(ele.getText());
        }

        StringBuilder content = new StringBuilder();
        content.append("Highest").append("=").append(Collections.max(arrayList)).append(System.lineSeparator())
                .append("Lowest").append("=").append(Collections.min(arrayList));

        try {
            Files.write(Paths.get("priceRange.txt"), Collections.singleton(content));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        scenario.attach(content.toString(), "text/html", "highLowValues");
    }


    public static void takeScreenshot() {
        if (executorPageReady())
            scenario.attach(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png", "Screenshot");
    }
}
