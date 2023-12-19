package ru.vsu.cs.sheina.socialservice.dto.rabbitmq;

public enum FileSource {
    AVATAR("AVATAR"),
    COVER("COVER"),
    POST("POST");

    private String name;

    FileSource(String name) {
        this.name = name;
    }
}
