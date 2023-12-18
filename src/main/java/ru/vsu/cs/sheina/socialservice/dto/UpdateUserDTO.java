package ru.vsu.cs.sheina.socialservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
public class UpdateUserDTO {

    String name;

    Timestamp birthDate;

    String bio;

    Boolean deleteAvatarFlag;

    Boolean deleteCoverFlag;
}
