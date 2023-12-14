package ru.vsu.cs.sheina.socialservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "user_data")
public class UserDataEntity {
    @Id
    @Column(name = "id")
    UUID id;

    @Column(name = "name")
    String name;

    @Column(name = "birth_date")
    Timestamp birthDate;

    @Column(name = "avatar_url")
    String avatarUrl;

    @Column(name = "cover_url")
    String coverUrl;

    @Column(name = "bio")
    String bio;
}
