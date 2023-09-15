# cucumber-shadow-dom
This repository is a showcase of automated tests where application under test (AUT) is based totally on shadow doms. It was made possible by using a third party, so WebElement declarations were made relatively easier in Page Object Model files (POM).

# Prerequisites
* Java Development Kit (JDK): JDK 8 or higher.
* Maven: For managing dependencies and building the project.
* Set environment variables for Java, Maven.
* IDE (Integrated Development Environment): Any Java IDE like Eclipse, IntelliJ IDEA etc.

# Getting Started
1. Clone this repository to your local machine:
2. Clone the repository
3. Open the project in the chosen IDE.

# Running Tests
Run test on local machine:
* for chrome: mvn clean verify -Dbrowser=chrome  -Dcucumber.filter.tags=@shadowDom
* for firefox: mvn clean verify -Dbrowser=firefox  -Dcucumber.filter.tags=@shadowDom

Run test on cloud platform i.e. Saucelabs, Browserstack etc. for this purpose enter your credentials for bcUser, bcKey, server in configs/browserCloud.json: 
* for chrome_mac: mvn clean verify -Dbrowser=browserCloud -DbrowserCloudEnv=chrome_mac  -Dcucumber.filter.tags=@shadowDom
* for firefox_mac: mvn clean verify -Dbrowser=browserCloud -DbrowserCloudEnv=firefox_mac  -Dcucumber.filter.tags=@shadowDom

# Reporting
Test reports can be found in the target/site directory. Or just click a link like the following which will host the report in cucumber cloud for 24 hours.


<img width="536" alt="report" src="https://github.com/abidali7/cucumber-shadow-dom/assets/17843941/665ea7fd-8706-4a83-8be1-da5b23f0e408">


