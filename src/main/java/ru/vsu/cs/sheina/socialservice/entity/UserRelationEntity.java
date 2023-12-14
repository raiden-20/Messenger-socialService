package ru.vsu.cs.sheina.socialservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.vsu.cs.sheina.socialservice.entity.enums.RelationStatus;

import java.util.UUID;

@Entity
@Data
@Table(name = "user_relation")
public class UserRelationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "first_user")
    UUID first_user;

    @Column(name = "second_user")
    UUID second_user;

    @Column(name = "status")
    RelationStatus status;
}
