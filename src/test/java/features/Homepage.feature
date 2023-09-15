@shadowDom
Feature: shadowDom

    @dom1
    Scenario: Validate A Class models price are between £15,000 and £60,000
        Given user is at the page ""
        Then assert that loaded page, contain "?group=all&subgroup=see-all&view=BODYTYPE" in url
        Then assert that title of the loaded page is "Mercedes-Benz Passenger Cars"
        And click on "acceptCookiesAll"
        And click on "modelList" for "Hatchbacks"
        Then assert that loaded page, contain "?group=all&subgroup=all.hatchback&view=BODYTYPE" in url
        And mouse over on "aClassModelList" at index 0
        And click on "buildYourCar"
        Then assert that loaded page, contain "/car-configurator.html" in url
        Then assert that "fuelType" is visible
        And click on "fuelType"
        And click on "fuelTypeCheckboxList" for "Diesel" by using text of "fuelTypeTextList"
        Then assert that "fuelType" activated as "selected 1 items" by att "aria-label"
        Then take the screenshot and attach it to report
        Then Save the highest and lowest price from "priceList" results in a text file and attached in report
