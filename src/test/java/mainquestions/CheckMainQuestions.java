package mainquestions;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pom.MainPage;


import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class CheckMainQuestions {


    //Ожидаемый результат(Текст в выпадашке)
    private final String expectedText;
    //Индекс строки в Аккордеоне
    private final int accordionLine;

    WebDriver driver;

    public CheckMainQuestions(String expectedText,int accordionLine) {
        this.expectedText = expectedText;
        this.accordionLine = accordionLine;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][] {
                {  "Сутки — 400 рублей. Оплата курьеру — наличными или картой.", 0},
                {  "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.", 1},
                {  "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.", 2},
                {  "Только начиная с завтрашнего дня. Но скоро станем расторопнее.", 3},
                {  "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.", 4},
                {  "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.", 5},
                {  "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.", 6},
                {  "Да, обязательно. Всем самокатов! И Москве, и Московской области.", 7},
        };
    }


    @Before
    public void startUp() {

        //Настройка браузера
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

//        WebDriverManager.firefoxdriver().setup();
//        driver = new FirefoxDriver();
    }

    @Test
    public void rightTextAfterClickInMainQuestion()  {
        MainPage mainPage = new MainPage(driver);

        mainPage
                //Открываем главную страницу
                .openPage()
                //Скролим до выбранного поля Аккордеона
                .scrollToAccordion(accordionLine)
                //Кликаем оп Заголовку Аккордеона
                .clickAccordion1(accordionLine)
                //Ожидаем появления текста в выппадашке
                .waitElemetAccordionOpen(accordionLine);

        //Проверяем правильность заполнения
        MatcherAssert.assertThat(mainPage.returnTextInAccordion(accordionLine), is(expectedText));


    }
    @After
    public void tearDown() {
        driver.quit();
    }
}
