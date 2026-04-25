package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

public class BaseTest {
    protected ConfigReader config;
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        config = new ConfigReader();
        System.setProperty("webdriver.chrome.driver", config.get("driverPath"));

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get(config.get("url"));
    }

}