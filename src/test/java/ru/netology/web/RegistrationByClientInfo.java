package ru.netology.web;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data

public class RegistrationByClientInfo {
    private final String city;
    private final String fullName;
    private final String phone;
}



