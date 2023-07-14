package ru.netology.web.test;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;


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
        DashboardPage.cleanFields();
    }

    @Test
    public void submitSuccessRePlannedRequest() {

        var request = DataHelper.validRandomCardRequest();
        DashboardPage.sendRequest(request);
        DashboardPage.checkSuccessNotification(request);
        DashboardPage.cleanFields();
        request = DataHelper.getReplantedDateRequest(request);
        DashboardPage.sendRequestNoAgreement(request);
        DashboardPage.checkSuccessReplannedDateRequest(request);

    }

    @Test
    public void submitSuccessRequest() {

        var request = DataHelper.validRandomCardRequest();
        DashboardPage.sendRequest(request);
        DashboardPage.checkSuccessNotification(request);

    }

    @Test
    public void submitFailedNullCity() {

        var request = DataHelper.getNullCityRandomRequest();
        DashboardPage.sendRequest(request);
        DashboardPage.checkNullCityRequest();

    }


    @Test
    void submitFailedWrongCity() {

        var request = DataHelper.getWrongCityRandomRequest();
        DashboardPage.sendRequest(request);
        DashboardPage.checkWrongCityRequest();

    }


    @Test
    void submitFailedNullDate() {

        var request = DataHelper.getNullDateRandomRequest();
        DashboardPage.sendRequest(request);
        DashboardPage.checkNullDateRequest();

    }

    @Test
    void submitSuccessBoundDate() {

        var request = DataHelper.boundDateRandomCardRequest();
        DashboardPage.sendRequest(request);
        DashboardPage.checkSuccessNotification(request);

    }

    @Test
    void submitFailedBeforeDate() {

        var request = DataHelper.beforeDateRandomCardRequest();
        DashboardPage.sendRequest(request);
        DashboardPage.checkBeforeDateRequest();

    }

    @Test
    void submitFailedNullFullName() {

        var request = DataHelper.getNullFullNameRandomRequest();
        DashboardPage.sendRequest(request);
        DashboardPage.checkNullFullNameRequest();

    }

    @Test
    void submitFailedWrongName() {

        var request = DataHelper.getWrongFullNameRandomRequest();
        DashboardPage.sendRequest(request);
        DashboardPage.checkWrongFullNameRequest();

    }

    @Test
    void submitFailedNullPhone() {

        var request = DataHelper.getNullPhoneRandomRequest();
        DashboardPage.sendRequest(request);
        DashboardPage.checkNullPhoneRequest();

    }

    @Test
    void submitFailedNotCheck() {

        var request = DataHelper.validRandomCardRequest();
        DashboardPage.sendRequestNoAgreement(request);
        DashboardPage.checkNotCheckAgreementRequest();
       
    }

}
