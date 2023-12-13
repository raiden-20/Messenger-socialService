package ru.vsu.cs.socialservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.socialservice.entity.UserDataEntity;

import java.util.UUID;

public interface UserDataRepository extends JpaRepository<UserDataEntity, UUID> {
}
