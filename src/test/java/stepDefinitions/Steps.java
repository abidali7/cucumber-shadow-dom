package stepDefinitions;

import helpers.BasePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pageobjects.Configurator.ConfiguratorAbstract;
import pageobjects.Configurator.ConfiguratorDesktop;
import pageobjects.Configurator.ConfiguratorMobile;
import pageobjects.Homepage.HomepageAbstract;
import pageobjects.Homepage.HomepageDesktop;
import pageobjects.Homepage.HomepageMobile;

import java.util.Arrays;

import static helpers.Utilities.*;
import static helpers.InnerBasePage.*;

public class Steps {
    private final BasePage base;
    private final HomepageAbstract homepage;
    private final ConfiguratorAbstract configurator;

    public Steps() {
        base = new BasePage();
        homepage = isMobile ? new HomepageMobile() : new HomepageDesktop();
        configurator = isMobile ? new ConfiguratorMobile() : new ConfiguratorDesktop();
    }

    @Given("^user is at the page \"([^\"]*)\"$")
    public void atPage(String url) {
        cookiesDelete();
        base.getUrlTo(url);
        Assert.assertTrue(executorPageReady());
    }

    @And("^click on \"([^\"]*)\"$")
    public void click(String ele) {
        getObject(ele);
        base.clickable();
    }

    @And("^click on \"([^\"]*)\" for \"([^\"]*)\"$")
    public void clickShadowList(String ele, String text) {
        getObject(ele);
        base.clickByText(text);
    }

    @And("^click on \"([^\"]*)\" for \"([^\"]*)\" by using text of \"([^\"]*)\"$")
    public void clickForText(String ele1, String text2, String ele2) {
        getObject(ele2, ele1);
        base.clickByText(text2, null);
    }

    @And("^mouse over on \"([^\"]*)\" at index (\\d+)$")
    public void mouseOverByIndex(String ele, int index) {
        getObject(ele);
        base.mouseOverByIndex(index);
    }

    @Then("assert that \"([^\"]*)\" is visible$")
    public void visible(String ele) {
        getObject(ele);
        Assert.assertTrue(base.isVisible());
    }

    @Then("assert that \"([^\"]*)\" activated as \"([^\"]*)\" by att \"([^\"]*)\"$")
    public void activated(String ele, String text, String attFor) {
        getObject(ele);
        Assert.assertTrue(base.waitByAtt(attFor, text));
    }

    @Then("^assert that loaded page, contain \"([^\"]*)\" in url$")
    public void urlContain(String text) {
        Assert.assertTrue(base.waitByUrl(text));
    }

    @Then("^assert that title of the loaded page is \"([^\"]*)\"$")
    public void titleContain(String text) {
        Assert.assertTrue(base.waitByTitle(text));
    }

    @Then("^take the screenshot and attach it to report$")
    public void screenshot() {
        BasePage.takeScreenshot();
    }

    @Then("^Save the highest and lowest price from \"([^\"]*)\" results in a text file and attached in report$")
    public void saveValues(String ele) {
        getObject(ele);
        base.priceRange();
    }

    public void getObject(String ele) {
        getElementNull();
        for (BasePage object : Arrays.asList(homepage, configurator)) {
            if (getObjectByName(object, ele)) break;
        }
    }

    public void getObject(String ele1, String ele2) {
        getElementNull();
        for (BasePage object : Arrays.asList(homepage, configurator)) {
            if (getObjectByName(object, ele1)) {
                eleSec = ele2;
                getObjectByName(object, ele2);
                break;
            }
        }
    }
}