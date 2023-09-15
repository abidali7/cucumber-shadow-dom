package pageobjects.Homepage;

import helpers.Utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class HomepageDesktop extends HomepageAbstract {
    // common page object for desktop
    private final String acceptCookiesAll = "//*[@data-test='handle-accept-all-button']";
    private final String modelList = "//*[@id='filters-placeholder']/div/div/section[2]//button/span";
    private final String aClassModelList = "//*[@class=' dh-io-vmos_dsRAb ']//a[contains(@href,'/models/hatchback/a-class/overview.html')]";
    private final String buildYourCar = "//*[contains(@class,' dh-io-vmos_dsRAb ')]//a[contains(text(),'Build your car')]";

    //@FindBy(how = How.CSS, using = ".button button--accept-all wb-button hydrated")
    //private WebElement acceptCookiesAll;

    public HomepageDesktop() {
        super();
    }
}
