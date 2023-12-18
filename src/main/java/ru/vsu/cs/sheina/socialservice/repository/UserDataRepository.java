package ru.vsu.cs.sheina.socialservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.sheina.socialservice.entity.UserDataEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDataRepository extends JpaRepository<UserDataEntity, UUID> {

    boolean existsById(UUID id);

    Optional<UserDataEntity> getUserDataEntityById(UUID id);

    List<UserDataEntity> findAll();
}
