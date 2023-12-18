package ru.vsu.cs.sheina.socialservice.entity.enums;

public enum RelationStatus {
    SEND_FIRST("SEND_FIRST"),
    SEND_SECOND("SEND_SECOND"),
    FRIENDS("FRIENDS");

    private final String type;

    RelationStatus(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
