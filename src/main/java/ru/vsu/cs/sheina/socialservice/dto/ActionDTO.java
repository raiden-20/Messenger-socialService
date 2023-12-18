package ru.vsu.cs.sheina.socialservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.vsu.cs.sheina.socialservice.entity.enums.RelationAction;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ActionDTO {

    UUID secondUser;

    RelationAction action;
}
