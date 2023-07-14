package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private final static Faker faker = new Faker(new Locale("ru"));

    private DataHelper() {

    }

    public static String randomCity() {

        return faker.address().city();
    }

    public static String randomFullName() {

        return (faker.name().lastName() + " " + faker.name().firstName()).replaceAll("ё", "е");
    }

    public static String randomPhone() {

        return faker.phoneNumber().phoneNumber();
    }

    public static int randomNumber() {

        return faker.number().numberBetween(3, 1000);
    }

    public static String randomString() {

        return faker.regexify("[А-Яа-я0-9]{10,}");

    }


    public static String currentDatePlusDays(int days) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.now();
        date = date.plusDays(days);
        return dtf.format(date);
    }

    public static CardRequest validRandomCardRequest() {

        return new CardRequest(randomCity(), currentDatePlusDays(randomNumber()), randomFullName(), randomPhone());
    }

    public static CardRequest boundDateRandomCardRequest() {

        return new CardRequest(randomCity(), currentDatePlusDays(3), randomFullName(), randomPhone());
    }

    public static CardRequest beforeDateRandomCardRequest() {

        return new CardRequest(randomCity(), currentDatePlusDays(-randomNumber() + 5), randomFullName(), randomPhone());
    }

    public static CardRequest getReplantedDateRequest(CardRequest request) {

        return new CardRequest(request.getRequestCity(), currentDatePlusDays(randomNumber()), request.getRequestFullName(), request.getRequestPhone());

    }

    public static CardRequest getNullCityRandomRequest() {

        return new CardRequest("", currentDatePlusDays(randomNumber()), randomFullName(), randomPhone());

    }

    public static CardRequest getWrongCityRandomRequest() {

        return new CardRequest(randomString(), currentDatePlusDays(randomNumber()), randomFullName(), randomPhone());
    }

    public static CardRequest getNullDateRandomRequest() {

        return new CardRequest(randomCity(), "", randomFullName(), randomPhone());

    }

    public static CardRequest getNullFullNameRandomRequest() {

        return new CardRequest(randomCity(), currentDatePlusDays(randomNumber()), "", randomPhone());

    }

    public static CardRequest getWrongFullNameRandomRequest() {

        return new CardRequest(randomCity(), currentDatePlusDays(randomNumber()), randomString() + " " + randomString(), randomPhone());
    }

    public static CardRequest getNullPhoneRandomRequest() {

        return new CardRequest(randomCity(), currentDatePlusDays(randomNumber()), randomFullName(), "");

    }

    @Value
    public static class CardRequest {
        String requestCity;
        String requestDate;
        String requestFullName;
        String requestPhone;

    }

}

