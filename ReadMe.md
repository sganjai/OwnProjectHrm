Driver Lifecycle Flow // update in main git

TestNG starts @Test
↓
@BeforeMethod → BaseTest.setup()
    ↓
    Load config.properties (browser, url, waits)
    ↓
    DriverFactory.initializeDriver()
        ↓
        switch(browser)
            chrome  → new ChromeDriver()
            edge    → new EdgeDriver()
            firefox → new FirefoxDriver()
    ↓
    DriverManager.setDriver(driver)
        (driver stored in ThreadLocal)
    ↓
    BaseTest fetches driver
        driver = DriverManager.getDriver()
    ↓
    Browser setup
        driver.manage().window().maximize()
    ↓
    Implicit wait applied
        driver.manage().timeouts().implicitlyWait()
    ↓
    URL opened
        driver.get(url)

@Test execution begins
↓
Test creates Page Object
    LoginPage loginPage = new LoginPage()

BasePage constructor runs
    ↓
    Driver fetched **AGAIN**
        this.driver = DriverManager.getDriver()
    ↓
    Explicit wait created
        wait = new WebDriverWait(driver,10)

Page actions execute
    ↓
    waitAndType()
    waitAndClick()
    waitAndGetText()

Assertions happen in test
    ↓
    Assert.assertEquals(...)

@AfterMethod → BaseTest.tearDown()
    ↓
    DriverManager.getDriver().quit()
        (browser closed)
    ↓
    DriverManager.unload()
        ThreadLocal.remove()

Test finished
*/


================
Project Structure:
ownProject
│
├── src
│   ├── main
│   │   └── java
│   │       └── com.myown.ownProject
│   │           ├── base
│   │           │   └── BaseTest.java
│   │           │
│   │           ├── core
│   │           │   ├── driver
│   │           │   │   └── DriverManager.java
│   │           │   │
│   │           │   ├── reports
│   │           │   │   ├── ExtentManager.java
│   │           │   │   └── ExtentTestManager.java
│   │           │   │
│   │           │   ├── listeners
│   │           │   │   └── TestListener.java
│   │           │   │
│   │           │   └── utilities
│   │           │       ├── ScreenshotUtil.java
│   │           │       └── ReportUtil.java
│   │           │
│   │           └── pages
│   │               └── (Page Classes...)
│   │
│   └── resources
│       ├── log4j2.xml
│       └── config.properties
│
├── src
│   └── test
│       └── java
│           └── com.myown.ownProject.tests
│               └── (Test Classes...)
│
├── test-output
│   └── (TestNG default reports)
│
├── reports
│   └── (Extent Report HTML files)
│
├── logs
│   └── (Log files)
│
└── testng.xml



Git initial Commit-
Changes to be committed:
  (use "git rm --cached <file>..." to unstage)
        new file:   .gitignore
        new file:   ReadMe.md
        new file:   Test Suites/Testng.xml
        new file:   logs/2026-05-10_18-30-20_validateLogin_17.log
        new file:   pom.xml
        new file:   reports/2026-05-10_18-30-47/Report.html
        new file:   src/main/java/com/myown/ownProject/core/config/configReader.java
        new file:   src/main/java/com/myown/ownProject/core/constants/Timeout.java
        new file:   src/main/java/com/myown/ownProject/core/driver/DriverFactory.java
        new file:   src/main/java/com/myown/ownProject/core/driver/DriverManager.java
        new file:   src/main/java/com/myown/ownProject/core/dummy.java
        new file:   src/main/java/com/myown/ownProject/core/pages/BasePage.java
        new file:   src/main/java/com/myown/ownProject/core/pages/DashBoardPage.java
        new file:   src/main/java/com/myown/ownProject/core/pages/HomePage.java
        new file:   src/main/java/com/myown/ownProject/core/pages/LoginPage.java
        new file:   src/main/java/com/myown/ownProject/core/pages/OrgGeneralInfo.java
        new file:   src/main/java/com/myown/ownProject/core/pages/PIMPage.java
        new file:   src/main/java/com/myown/ownProject/core/utilities/ExcelReader.java
        new file:   src/main/java/com/myown/ownProject/core/utilities/ExcelUtil.java
        new file:   src/main/java/com/myown/ownProject/core/utilities/ExtentManager.java
        new file:   src/main/java/com/myown/ownProject/core/utilities/ExtentTestManager.java
        new file:   src/main/java/com/myown/ownProject/core/utilities/Reporter.java
        new file:   src/main/java/com/myown/ownProject/core/utilities/ScreenshotUtil.java
        new file:   src/main/java/com/myown/ownProject/core/utilities/WaitUtil.java
        new file:   src/main/resources/config.properties
        new file:   src/main/resources/log4j2.xml
        new file:   src/test/java/com/myown/ownProject/core/utilities/ExcelDataProvider.java
        new file:   src/test/java/com/myownProject/test/listener/TestListener.java
        new file:   src/test/java/com/myownProject/test/test/BaseTest.java
        new file:   src/test/java/com/myownProject/test/test/DashBoardTest.java
        new file:   src/test/java/com/myownProject/test/test/EditOrgGeneralInfoTest.java
        new file:   src/test/java/com/myownProject/test/test/LoginTest.java
        new file:   src/test/java/com/myownProject/test/test/PIMTest.java
        new file:   src/test/resources/TestData/DataViaExcel.xlsx
