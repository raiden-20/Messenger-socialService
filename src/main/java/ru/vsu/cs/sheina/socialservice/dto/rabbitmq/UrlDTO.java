package ru.vsu.cs.sheina.socialservice.dto.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UrlDTO {

    UUID sourceId;

    String url;

    FileSource source;
}
