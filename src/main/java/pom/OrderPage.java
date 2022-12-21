package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    //url главной страницы
    private final String url = "https://qa-scooter.praktikum-services.ru/";
    //Поле Имя
    private final By fieldName = By.xpath(".//input[@placeholder ='* Имя']");
    //Поле Фамилия
    private final By fieldFamily = By.xpath(".//input[@placeholder ='* Фамилия']");
    //Поле Адресс
    private final By fieldAddress = By.xpath(".//input[@placeholder ='* Адрес: куда привезти заказ']");
    //Поле Станция метро
    private final By fieldMetroStation = By.xpath(".//input[@placeholder ='* Станция метро']");
    //Поле Номер телефона
    private final By fieldTelephoneNumber = By.xpath(".//input[@placeholder ='* Телефон: на него позвонит курьер']");
    //Кнопка Далее
    private final By buttonNext = By.xpath(".//div[@class ='Order_NextButton__1_rCA']/button");


    //Поле Когда привезти самокат
    private final By fieldWhen = By.xpath(".//input[@placeholder ='* Когда привезти самокат']");
    //Поле Срок аренды
    private final By fieldPeriod = By.xpath(".//div[@class ='Dropdown-placeholder']");

    //Селектор строки поля в завиимости от параметра номера строки выпадающего списка
    public By selectPeriodLine(int periodLine){
        String path =  ".//div[@class='Dropdown-menu']/div[" + periodLine + "]";
        return By.xpath(path);
    }

    //Селектор выбора цвета в зависимости от переданного параметра цвета
    public By fieldColorSelect(String color){
        String path =  ".//input[@id='" + color + "']";
        return By.xpath(path);
    }

    //Поле Комментария для курьера
    private final By fieldComment = By.xpath(".//input[@placeholder ='Комментарий для курьера']");
    //Кнопка заказать(Оформить) заказ
    private final By buttonCreateOrder = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");
    //Кнопка Назад
    private final By buttonBack = By.xpath(".//button[text()='Назад']");
    //Кнопка Подтвердить заказ ДА
    private final By buttonConfirmYes = By.xpath(".//button[text()='Да']");

    //Плашка с текстом Заказ оформлен...
    private final By orderComplete = By.xpath(".//div[@class='Order_ModalHeader__3FDaJ']");

    public WebDriver driver;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    //Открытие главной страницы
    public OrderPage openPage() {
        driver.get(url);
        return this;
    }

    //Ввод имени (передается из параметра)
    public OrderPage inputToName(String name) {
        driver.findElement(fieldName).sendKeys(name);
        return this;
    }

    //Ввод Фамилии (передается из параметра)
    public OrderPage inputToFamily(String family) {
        driver.findElement(fieldFamily).sendKeys(family);
        return this;
    }

    //Ввод Адреса(передается из параметра)
    public OrderPage inputToAddress(String address) {
        driver.findElement(fieldAddress).sendKeys(address);
        return this;
    }

    //Ввод станции метро (передается из параметра)
    public OrderPage inputMetroStation(String metro) {
        driver.findElement(fieldMetroStation).sendKeys(metro, Keys.ARROW_DOWN , Keys.ENTER);
        return this;
    }

    //Ввод Номера телефона (передается из параметра)
    public OrderPage inputTelNumbers(String telNumber) {
        driver.findElement(fieldTelephoneNumber).sendKeys(telNumber);
        return this;
    }

    //Клик по кнопке Далее
    public OrderPage clickNextButton(){
        driver.findElement(buttonNext).click();
        return this;
    }

    // Вввод даты доставки (передается из параметра)
    public OrderPage inputWhenData (String data){
        driver.findElement(fieldWhen).sendKeys(data, Keys.ENTER);
        return this;
    }


    //Выбор Периода Аренды (передается из параметра в виде номера строки выпадающего списка)
    public OrderPage selectPeriod (int period){
        driver.findElement(fieldPeriod).click();
        waitPeriodDropdown(period);
        driver.findElement(selectPeriodLine(period)).click();
        return this;
    }

    //ВЫбор цвета самоката (передается из параметра)
    public OrderPage selectColor(String color){
        driver.findElement(fieldColorSelect(color)).click();
        return this;
    }

    //Ввод комментария
    public OrderPage inputComment (String comment){
        driver.findElement(fieldComment).sendKeys(comment);
        return this;
    }

    // Клик по кнопке Заказать (Оформить)
    public OrderPage clickOrderButton(){
        driver.findElement(buttonCreateOrder).click();
        return this;
    }

    //Клик по кнопке ДА, подтверждения Заказа
    public OrderPage clickOrderYes(){
        driver.findElement(buttonConfirmYes).click();
        return this;
    }

    //Ожидание загрузки кнопки назад, при перереходе на 2 часть оформления заказа
    public OrderPage waitBackButton(){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(buttonBack));
        return this;
    }

    //Ожидание загрузки выпадающего списка срока аренды
    public OrderPage waitPeriodDropdown(int i){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(selectPeriodLine(i)));
        return this;
    }

    // Ожидание поля ввода номера телефона (иногда перекрывается полем выбора станции метро)
    public OrderPage waitTelephoneField(){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(fieldTelephoneNumber));
        return this;
    }

    //Ожидание загрузки всплывашки подтверждение заказа
    public OrderPage waitOrderComplete(){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(orderComplete));
        return this;
    }

    //Получение текста об успешном офорлении заказа
    public String finalOrderMessage(){
        return driver.findElement(orderComplete).getText();
    }
}