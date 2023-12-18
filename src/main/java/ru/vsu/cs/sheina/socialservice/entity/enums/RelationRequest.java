package ru.vsu.cs.sheina.socialservice.entity.enums;

public enum RelationRequest {
    FRIENDS("friends"),
    SUBSCRIPTIONS("subscriptions"),
    SUBSCRIBERS("subscribers");

    private final String name;

    RelationRequest(String name) {
        this.name = name;
    }
}
