package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Data

public class DashboardPage {

    private static final SelenideElement city = $("[data-test-id='city'] input");
    private static final SelenideElement date = $("[data-test-id='date'] input");
    private static final SelenideElement fullName = $("[data-test-id='name'] input");
    private static final SelenideElement phone = $("[data-test-id='phone'] input");
    private static final SelenideElement agreementRadio = $("[data-test-id=agreement]");
    private static final SelenideElement plannedButton = $$("button").find(exactText("Запланировать"));
    private static final SelenideElement replantedButton = $$("button").find(exactText("Перепланировать"));
    private static final SelenideElement successNotification = $("[data-test-id=success-notification] .notification__content");
    private static final SelenideElement replantedNotification = $("[data-test-id=replan-notification] .notification__content");
    private static final SelenideElement invalidCity = $("[data-test-id=city].input_invalid .input__sub");
    private static final SelenideElement invalidDate = $("[data-test-id=date] .input.input_invalid .input__sub");
    private static final SelenideElement invalidName = $("[data-test-id=name].input_invalid.input .input__sub");
    private static final SelenideElement invalidPhone = $("[data-test-id=phone].input.input_invalid .input__sub");
    private static final SelenideElement invalidAgreement = $("[data-test-id=agreement].input_invalid .checkbox__text");


    private static final SelenideElement header = $(".heading");

    public DashboardPage() {

        header.shouldHave(exactText("Карта с доставкой!")).shouldBe(Condition.visible);
        city.shouldBe(Condition.visible);
        fullName.shouldBe(Condition.visible);
        phone.shouldBe(Condition.visible);
        date.shouldBe(Condition.visible);
        agreementRadio.shouldBe(visible);

    }

    public static void cleanFields() {

        city.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        date.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        fullName.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        phone.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);

    }

    public static void sendRequest(DataHelper.CardRequest request) {

        putRequest(request);
        checkAgreement();
        plannedButton.click();
    }

    public static void sendRequestNoAgreement(DataHelper.CardRequest request) {

        putRequest(request);
        plannedButton.click();
    }

    public static void checkAgreement() {

        agreementRadio.click();
    }

    public static void putRequest(DataHelper.CardRequest request) {

        city.setValue(request.getRequestCity());
        date.setValue(request.getRequestDate());
        fullName.setValue(request.getRequestFullName());
        phone.setValue(request.getRequestPhone());

    }

    public static void checkSuccessNotification(DataHelper.CardRequest request) {

        successNotification.shouldHave(Condition.text("Встреча успешно запланирована на " + request.getRequestDate()), Duration.ofSeconds(5));
    }

    public static void checkSuccessReplannedDateRequest(DataHelper.CardRequest request) {

        replantedNotification.shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(5));
        replantedButton.click();
        successNotification.shouldHave(Condition.text("Встреча успешно запланирована на " + request.getRequestDate()), Duration.ofSeconds(5));
    }

    public static void checkNullCityRequest() {

        invalidCity.shouldHave(Condition.text("Поле обязательно для заполнения")).shouldBe(Condition.visible);

    }

    public static void checkWrongCityRequest() {

        invalidCity.shouldHave(Condition.exactText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible);
    }

    public static void checkNullDateRequest() {

        invalidDate.shouldHave(Condition.exactText("Неверно введена дата")).shouldBe(Condition.visible);

    }

    public static void checkBeforeDateRequest() {

        invalidDate.shouldHave(Condition.exactText("Заказ на выбранную дату невозможен")).shouldBe(Condition.visible);

    }

    public static void checkNullFullNameRequest() {

        invalidName.shouldHave(Condition.exactText("Поле обязательно для заполнения")).shouldBe(Condition.visible);

    }

    public static void checkWrongFullNameRequest() {

        invalidName.shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible);

    }

    public static void checkNullPhoneRequest() {

        invalidPhone.shouldHave(Condition.exactText("Поле обязательно для заполнения")).shouldBe(Condition.visible);

    }

    public static void checkNotCheckAgreementRequest() {

        invalidAgreement.shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }
}



