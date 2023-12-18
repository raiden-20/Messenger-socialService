package ru.vsu.cs.sheina.socialservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.vsu.cs.sheina.socialservice.entity.enums.RelationStatus;

import java.sql.Timestamp;

@Data
@RequiredArgsConstructor
public class UserFullDTO {

    String name;

    Timestamp birthDate;

    String avatarUrl;

    String coverUrl;

    String bio;

    RelationStatus status;
}
