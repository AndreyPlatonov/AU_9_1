package ru.netology.web;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private DataGenerator() {

    }

    public static RegistrationByClientInfo generatedByInfo() {
        Faker faker = new Faker(new Locale(("ru")));

        return new RegistrationByClientInfo(
                faker.address().city(),
                (faker.name().lastName() + " " + faker.name().firstName()).replaceAll("ё", "е"),
                faker.phoneNumber().phoneNumber());
    }

    public static class FormatDate {
        private FormatDate() {

        }

        public static String currentPlusDays(int days) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.now();
            date = date.plusDays(days);
            String formatDate = dtf.format(date);
            return formatDate;
        }

    }
}

