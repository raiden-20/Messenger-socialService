package ru.vsu.cs.sheina.socialservice.dto.fields;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class IdDTO {
    UUID id;
}
