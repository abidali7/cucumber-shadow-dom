package pageobjects.Configurator;

import helpers.Utilities;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfiguratorDesktop extends ConfiguratorAbstract {
    // common page object for desktop
    private final String fuelType = "//*[contains(@aria-label,'Fuel type')]";
    private final String fuelTypeTextList = "//ccwb-multi-select//ccwb-checkbox";
    private final String fuelTypeCheckboxList = "//ccwb-multi-select//ccwb-checkbox//ccwb-icon";
    private final String priceList = "//span[contains(@class,'cc-motorization-header__price')]";

    public ConfiguratorDesktop() {
        super();
    }
}
