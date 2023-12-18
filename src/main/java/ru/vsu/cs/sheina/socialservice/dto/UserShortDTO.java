package ru.vsu.cs.sheina.socialservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class UserShortDTO {

    UUID id;

    String name;

    String avatarUrl;

    String coverUrl;

    String bio;
}
