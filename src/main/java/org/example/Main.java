package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    static void main() {
        String driverPath = "src/test/resources/drivers/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", driverPath);

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com");
//        driver.get(config.get());
        System.out.println(driver.getTitle());
    }
}
