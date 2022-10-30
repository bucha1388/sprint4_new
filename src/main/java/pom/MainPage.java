package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    //url главной страницы
    private final String url = "https://qa-scooter.praktikum-services.ru/";
    //Заголовок первого пункта Аккордеона
    private final By accordionMainQuestion1 = By.xpath(".//div[@id = 'accordion__heading-0']/parent::div/parent::div");
    //Выпадашка первого пункта Аккордеона
    private final By accordionMainQuestion1Open = By.xpath(".//div[@id = 'accordion__panel-0']/p");
    //Верхняя кнопка Заказать
    private final By buttonOrderUP = By.xpath(".//button[@class='Button_Button__ra12g']");
    //Верхняя кнопка заказать
    private final By buttonOrderDown = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button");



    public WebDriver driver;
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    //Открытие главной страницы
    public MainPage openPage(){
        driver.get(url);
        return this;
    }

    //Создаем локатор от индекса заголовка Аккордкона
    public By createLocatorFromNumber(int i){
        String locator = ".//div[@id = 'accordion__heading-" + i + "']/parent::div/parent::div";
        return By.xpath(locator);
    }

    //Создаем локатор выпадашки от индекса выпадашки Аккордеона
    public By createLocatorOpenAccordion(int i){
        String locator = ".//div[@id = 'accordion__panel-" + i + "']/p";
        return By.xpath(locator);
    }

    //Выполняем прокрутку до нужного пункта Аккордкона
    public MainPage scrollToAccordion(int i){
        WebElement element = driver.findElement(createLocatorFromNumber(i));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        return this;
    }

    //Прокрутка до кнопки Заказать
    public void scrollToOrderButton(int n){
        WebElement element = driver.findElement(buttonOrder(n));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    //Кликаем по заголовку Аккордеона
    public MainPage clickAccordion1(int i){
        driver.findElement(createLocatorFromNumber(i)).click();
        return this;
    }

    //Ждем пока выпадашка прогрузится
    public void waitElemetAccordionOpen(int i){
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOf(driver.findElement(createLocatorOpenAccordion(i))));
    }

    //Получаем текс из выпадашки в соответствии с индексом Аккордеона
    public String returnTextInAccordion(int i){
        return driver.findElement(createLocatorOpenAccordion(i)).getText();
    }

    //Клик по кнопке Заказать в соответствии с выбранной кнопкой
    public void buttonOrderClick(int n){
        scrollToOrderButton(n);
        driver.findElement(buttonOrder(n)).click();
    }
    //ВЫбор кнопки Заказать в зависимости от переданного параметра
    public By buttonOrder(int n) {
        By link = buttonOrderUP;
        if (n == 2) {
            link = buttonOrderDown;
        }
        return link;
    }


}
