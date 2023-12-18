package ru.vsu.cs.sheina.socialservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class UserRegistrationDTO {

    UUID id;

    String name;

    Timestamp birthDate;
}
