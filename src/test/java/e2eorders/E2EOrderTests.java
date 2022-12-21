package e2eorders;

import main.BrowserRule;
import org.hamcrest.MatcherAssert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pom.MainPage;
import pom.OrderPage;

import static org.hamcrest.CoreMatchers.containsString;


@RunWith(Parameterized.class)
public class E2EOrderTests {

    //Отвечает за выбор кнопки "Заказать" 2 - нижняя, 1(или любая другая) - верхняя
    private final int buttonOrder;
    //Имя пользователя
    private final String name;
    //Фамилия пользователя
    private final String family;
    //Адрес доставки
    private final String address;
    //Станция метро
    private final String metro;
    //Телефон пользователя
    private final String tel;
    //Дата доставки
    private final String date;
    //Выбранный перриод аренды(номер строки в выпадающем списке, начиная с верхней))
    private final int period;
    //Цвет самоката (два значения "grey" или "black")
    private final String colour;
    //Комментарий пользователя
    private final String comment;


    public E2EOrderTests(int buttonOrder, String name, String family, String address, String metro, String tel, String date, int period, String colour, String comment) {
        this.buttonOrder = buttonOrder;
        this.name = name;
        this.family = family;
        this.address = address;
        this.metro = metro;
        this.tel = tel;
        this.date = date;
        this.period = period;
        this.colour = colour;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][] {
                { 1, "Мария", "Колбаскина", "Москва, ул. Иванова , дом 6, кв. 11", "Каширская", "89123456789", "11.11.2022", 2, "grey", "Везите скорее!!!" },
                { 2, "Илон", "Маск", "Марс, ул. Илона Маска , дом 1, кв. 1", "Тульская", "89000000000", "15.06.2025", 4, "black", "И Теслу привезите." },
        };
    }

    // В @Rule вынесены параметры Before и After (включая настройку браузера)
    @Rule
    public BrowserRule browserRule = new BrowserRule();



    @Test
    public void e2eTestOrder() {

        MainPage mainPage = new MainPage(browserRule.getDriver());


        mainPage
                //открытие главной страницы проекта
                .openPage()
                //Нажатие кнопри Заказать
                .buttonOrderClick(buttonOrder);

        OrderPage orderPage = new OrderPage(browserRule.getDriver());

        // На странице Оформления заказа
        orderPage
                //Заполняем поле Имя
                .inputToName(name)
                //Заполняем поле Фамилия
                .inputToFamily(family)
                //Заполняем поле адрес
                .inputToAddress(address)
                //Выбираем станцию метро
                .inputMetroStation(metro)
                //Ожидание открытия поля ввода телефона, после скрытия выпадашки со станциями метро
                .waitTelephoneField()
                //Вводим номер телефона
                .inputTelNumbers(tel)
                //Кликаем по кнопке Далее
                .clickNextButton()

                //Ждем пока загрузится однин из нижних элементов страницы
                .waitBackButton()

                //ВЫбираем(вводим) дату доставки
                .inputWhenData(date)
                //ВЫбираем период аренды
                .selectPeriod(period)
                //Выбираем цвет самоката
                .selectColor(colour)
                //Вводим комментарий к заказу
                .inputComment(comment)
                //Нажимаем кнопку Заказать
                .clickOrderButton()
                //Нажимаем кнопку подтверждения заказа
                .clickOrderYes()

                //Ждем закрузки кнопки Посмотреть статус
                .waitOrderComplete();

        //Проверяем, что текст на появившейся плашке содержит текст "Заказ оформлен"
        MatcherAssert.assertThat(orderPage.finalOrderMessage(), containsString("Заказ оформлен"));
    }

}

