package ru.vsu.cs.sheina.socialservice.dto.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UrlDTO {

    String sourceId;

    String url;

    FileSource source;
}
