package ru.netology.web;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.web.Generator.name;
import static ru.netology.web.Generator.*;



public class CardDeliveryTest {

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");

    }
    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void mustScheduleAndRescheduleTheDate() {
        String date1 = data();
        String date2 = data();

        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(date1);
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + date1))
                .shouldHave(visible);
        $("[data-test-id=date] input").doubleClick().append(date2);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__title")
                .shouldHave(exactText("Необходимо подтверждение"))
                .shouldHave(visible);
        $("[data-test-id=replan-notification] .notification__content")
                .shouldHave(textCaseSensitive("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldBe(exactText("Встреча успешно запланирована на " + date2))
                .shouldHave(visible);

    }

    @Test
    void redWarningMessageShouldAppearDeliveryToTheSelectedCityIsNotAvailableForTheCityField() {
        $("[data-test-id=city] input").setValue(invalidCity());
        $("[data-test-id=date] input").doubleClick().append(data());
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=city].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Доставка в выбранный город недоступна"))
                .shouldHave(visible);
    }
    @Test
    void mustScheduleAndRescheduleDateWithTheSameValue() {
        String date1 = data();

        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(date1);
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] div.notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldHave(visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + date1))
                .shouldHave(visible);
        $("[data-test-id=date] input").doubleClick().append(date1);
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__title")
                .shouldHave(exactText("Необходимо подтверждение"))
                .shouldHave(visible);
        $("[data-test-id=replan-notification] .notification__content")
                .shouldHave(textCaseSensitive("у вас уже есть запись на текущую дату"));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification] div.notification__title").shouldBe(exactText("Успешно!"))
                .shouldHave(visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldBe(exactText("Встреча успешно запланирована на " + date1))
                .shouldHave(visible);

    }

    @Test
    void redWarningMessageShouldAppearInvalidDateEnteredForTheDateField() {
        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(invalidData());
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=date] .input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Неверно введена дата"))
                .shouldHave(visible);
    }

    @Test
    void redWarningMessageShouldAppearTheFirstAndLastNameSpecifiedIsIncorrectForTheLastNameField() {
        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(data());
        $("[data-test-id=name] input").setValue(invalidName());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=name].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."))
                .shouldHave(visible);
    }


    @Test
    void redWarningMessageShouldAppearAnOrderForTheSelectedDateIsNotPossibleForTheDateField() {
        String invalidDataNow = invalidDataNow();
        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(invalidDataNow);
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=date] .input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Заказ на выбранную дату невозможен")).shouldHave(visible);
    }

    @Test
    void redWarningMessageShouldAppearThisFieldIsRequiredForTheCityField() {
        $("[data-test-id=city] input").setValue(zero());
        $("[data-test-id=date] input").doubleClick().append(data());
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=city].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Поле обязательно для заполнения"))
                .shouldHave(visible);
    }

    @Test
    void RedWarningMessageShouldAppearIncorrectDate() {
        //не найден по инвалиду
        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, invalidData(), Keys.DELETE));
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=date] .input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Неверно введена дата")).shouldHave(visible);

    }

    @Test
    void redWarningMessageShouldAppearTheFieldMustBeFilledInForTheLastNameFirstNameField() {
        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(data());
        $("[data-test-id=name] input").setValue(zero());
        $("[data-test-id=phone] input").setValue(phone());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=name].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Поле обязательно для заполнения"))
                .shouldHave(visible);
    }

    @Test
    void redWarningMessageShouldAppearTheFieldMustBeFilledInForThePhoneField() {
        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(data());
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(zero());
        $("[data-test-id=agreement] span.checkbox__box").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=phone].input_theme_alfa-on-white.input_invalid .input__sub")
                .shouldBe(exactText("Поле обязательно для заполнения"))
                .shouldHave(visible);

    }

    @Test
    void theMessageOfTheCheckBoxShouldChangeColorToRed() {
        $("[data-test-id=city] input").setValue(validCity());
        $("[data-test-id=date] input").doubleClick().append(data());
        $("[data-test-id=name] input").setValue(name());
        $("[data-test-id=phone] input").setValue(phone());
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=agreement].input_invalid span.checkbox__text")
                .shouldBe(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"))
                .shouldHave(visible);
    }
}
