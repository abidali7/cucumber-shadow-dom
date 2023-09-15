package helpers;

import io.github.sukgu.Shadow;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.List;

import static helpers.Utilities.*;

public class InnerBasicPage {
    // contain all functions which are inner details of BasicPage
    public static WebDriver innerDriver;
    public static Shadow shadow;
    public static WebElement ele;
    public static List<WebElement> eles;
    public static String eleName;
    public static WebElement ele2;
    public static List<WebElement> eles2;
    public static String eleSec;
    public static int index;
    private static WebElement element;
    private static List<WebElement> elements;

    public static WebDriverWait waitBy() {
        return new WebDriverWait(innerDriver, Duration.ofSeconds(30));
    }

    public static boolean ecElement(ExpectedCondition<WebElement> ec) {
        try {
            waitBy().until(ec);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean ecBoolean(ExpectedCondition<Boolean> ec) {
        try {
            waitBy().until(ec);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean waitVisibility() {
        return ecElement(ExpectedConditions.visibilityOf(ele));
    }

    public static JavascriptExecutor executor() {
        return (JavascriptExecutor) innerDriver;
    }

    public static boolean executorPageReady() {
        try {
            executor().executeScript("return document.readyState").equals("complete");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean getObjectByName(Object object, String str) {
        String byStr = null;
        boolean isTrue = false;

        try {
            try {
                element = (WebElement) getObjectByString(object, str); // check if returned object is WebElement

            } catch (Exception e) {
                try {
                    byStr = (String) getObjectByString(object, str); // check if returned object is String if not WebElement

                } catch (Exception ee) {
                    elements = (List<WebElement>) getObjectByString(object, str); // returned object will be List<WebElement> if neither WebElement nor String
                }
            }
            isTrue = true;
        } catch (Exception ignored) {
        }

        if (elements != null || byStr != null) {
            // if str contain List, initialise shadow as elements otherwise as single element
            try {
                shadow.setExplicitWait(8, 2);
                if (str.contains("List")) elements = shadow.findElementsByXPath(byStr);
                else element = shadow.findElementByXPath(byStr);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // assign elements to utilities based values for either 1 or 2
        if (isTrue) {
            if (eleSec != null) {
                eleSec = str;
                ele2 = element;
                eles2 = elements;

            } else {
                eleName = str;
                ele = element;
                eles = elements;
            }
        }

        element = null;
        elements = null;
        return isTrue;
    }

    //returns object by searching in class or superclass for it
    protected static Object getObjectByString(Object object, String eleName) {
        Class<?> pageClass = object.getClass();
        Class<?> superPageClass = pageClass.getSuperclass();

        Field pageField = null;
        Object obj = null;

        try {
            try {
                pageField = pageClass.getDeclaredField(eleName);
            } catch (NoSuchFieldException e) {
                pageField = superPageClass.getDeclaredField(eleName);
            }

        } catch (NoSuchFieldException e) {
            //System.out.println("Field '" + webElementName + "' could neither be found in class nor in superclass: " + ex);
        }

        pageField.setAccessible(true);
        try {
            obj = pageField.get(object);
        } catch (IllegalAccessException ignored) {
        }
        return obj;
    }

    public static boolean getElementByIndex(int index) {
        ele = eles.get(index);
        return ele != null;
    }

    public static void getElementByIndex() {
        getElementByIndex(index);
    }

    public static boolean getElementAssign() {
        if (eleSec != null) {
            eles = eles2;
            ele = ele2;
            if (ele == null || ele.toString().contains("by id or name")) getElementByIndex();
        }
        return ele != null;
    }

    public static void getElementNull() {
        ele = null;
        eles = null;
        eleName = null;
        ele2 = null;
        eles2 = null;
        eleSec = null;
    }

    public static String getText() {
        waitVisibility();
        return ele.getText();
    }

    public static boolean getText(String text) {
        String isText = getText();
        return isText.equalsIgnoreCase(text) || isText.contains(text);
    }

    public static boolean getTextByText(String text) {
        int list = getSize();
        boolean isTrue = false;

        for (int i = 0; i < list; i++) {
            isTrue = getElementByIndex(i) && getText(text);
            if (isTrue) {
                index = i;
                break;
            }
        }
        return isTrue;
    }

    public static int getSize() {
        return eles.size();
    }

    public static boolean scrollByElement() {
        try {
            executor().executeScript("window.scrollTo(0, " + (ele.getLocation().getY() - 300) + ")");
            sleep(2);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean cookiesEmpty() {
        try {
            return innerDriver.manage().getCookies().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public static void cookiesDeleteAll() {
        try {
            innerDriver.manage().deleteAllCookies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cookiesDelete() {
        if (executorPageReady() && !cookiesEmpty()) {
            cookiesDeleteAll();
            if (!cookiesEmpty()) cookiesDeleteAll();
        }
    }
}
