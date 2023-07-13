package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.util.Random;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;


public class CallbackTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void submitSuccessRePlannedRequest() {

        var dateMeeting = DataGenerator.FormatDate.currentPlusDays(4);
        var infoClient = DataGenerator.generatedByInfo();

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='city'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='phone'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=city] input").setValue(infoClient.getCity());

        $("[data-test-id=date] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue(infoClient.getFullName());
        $("[data-test-id=phone] input").setValue(infoClient.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + dateMeeting), Duration.ofSeconds(5));

        dateMeeting = DataGenerator.FormatDate.currentPlusDays(14);

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='city'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='phone'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=city] input").setValue(infoClient.getCity());
        $("[data-test-id=date] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue(infoClient.getFullName());
        $("[data-test-id=phone] input").setValue(infoClient.getPhone());
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__content").shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(5));
        $$("button").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + dateMeeting), Duration.ofSeconds(5));

    }

    @Test
    void submitSuccessRequest() {

        var dateMeeting = DataGenerator.FormatDate.currentPlusDays(4);
        var infoClient = DataGenerator.generatedByInfo();

        $("[data-test-id=city] input").setValue(infoClient.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue(infoClient.getFullName());
        $("[data-test-id=phone] input").setValue(infoClient.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + dateMeeting), Duration.ofSeconds(5));

    }

    @Test
    void submitFailedNullCity() {

        var dateMeeting = DataGenerator.FormatDate.currentPlusDays(4);
        var infoClient = DataGenerator.generatedByInfo();


        $("[data-test-id=city] input").setValue("");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue(infoClient.getFullName());
        $("[data-test-id=phone] input").setValue(infoClient.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения")).shouldBe(Condition.visible);

    }

    @Test
    void submitFailedWrongCity() {

        var dateMeeting = DataGenerator.FormatDate.currentPlusDays(4);
        var infoClient = DataGenerator.generatedByInfo();

        $("[data-test-id=city] input").setValue("ввввввв");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue(infoClient.getFullName());
        $("[data-test-id=phone] input").setValue(infoClient.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible);

    }

    @Test
    void submitFailedNullDate() {

        var infoClient = DataGenerator.generatedByInfo();

        $("[data-test-id=city] input").setValue(infoClient.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue("");
        $("[data-test-id=name] input").setValue(infoClient.getFullName());
        $("[data-test-id=phone] input").setValue(infoClient.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=date] .input.input_invalid .input__sub").shouldHave(Condition.exactText("Неверно введена дата")).shouldBe(Condition.visible);


    }

    @Test
    void submitSuccessBoundDate() {

        var dateMeeting = DataGenerator.FormatDate.currentPlusDays(3);
        var infoClient = DataGenerator.generatedByInfo();

        $("[data-test-id=city] input").setValue(infoClient.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue(infoClient.getFullName());
        $("[data-test-id=phone] input").setValue(infoClient.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").shouldHave(Condition.text("Встреча успешно запланирована на " + dateMeeting), Duration.ofSeconds(5));

    }

    @Test
    void submitFailedBeforeDate() {

        var dateMeeting = DataGenerator.FormatDate.currentPlusDays(1);
        var infoClient = DataGenerator.generatedByInfo();

        $("[data-test-id=city] input").setValue(infoClient.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue(infoClient.getFullName());
        $("[data-test-id=phone] input").setValue(infoClient.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=date] .input.input_invalid .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен")).shouldBe(Condition.visible);

    }

    @Test
    void submitFailedNullName() {

        var dateMeeting = DataGenerator.FormatDate.currentPlusDays(4);
        var infoClient = DataGenerator.generatedByInfo();

        $("[data-test-id=city] input").setValue(infoClient.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue(infoClient.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=name].input_invalid.input .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения")).shouldBe(Condition.visible);

    }

    @Test
    void submitFailedWrongName() {

        var dateMeeting = DataGenerator.FormatDate.currentPlusDays(4);
        var infoClient = DataGenerator.generatedByInfo();

        $("[data-test-id=city] input").setValue(infoClient.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue("Иванов Иван@@222");
        $("[data-test-id=phone] input").setValue(infoClient.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=name].input.input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible);

    }

    @Test
    void submitFailedNullPhone() {

        var dateMeeting = DataGenerator.FormatDate.currentPlusDays(4);
        var infoClient = DataGenerator.generatedByInfo();

        $("[data-test-id=city] input").setValue(infoClient.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue(infoClient.getFullName());
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=phone].input.input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения")).shouldBe(Condition.visible);

    }

    @Test
    void submitFailedNotCheck() {

        var dateMeeting = DataGenerator.FormatDate.currentPlusDays(4);
        var infoClient = DataGenerator.generatedByInfo();

        $("[data-test-id=city] input").setValue(infoClient.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(dateMeeting);
        $("[data-test-id=name] input").setValue(infoClient.getFullName());
        $("[data-test-id=phone] input").setValue(infoClient.getPhone());
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }

}
