package main;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BrowserRule extends ExternalResource {

    private WebDriver driver;
    public WebDriver getDriver() {
        return driver;
    }

    @Override
    protected void before() throws  Throwable {

        //Настройка браузера

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

//      WebDriverManager.firefoxdriver().setup();
//      driver = new FirefoxDriver();

    }

    @Override
    //Закрытие окна браузера
    protected  void after(){
        driver.quit();
    }
}
