package ru.vsu.cs.sheina.socialservice.dto.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlDTO {

    String sourceId;

    String url;

    FileSource source;
}
