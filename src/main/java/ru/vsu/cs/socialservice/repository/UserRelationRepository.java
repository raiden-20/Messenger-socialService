package ru.vsu.cs.socialservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.socialservice.entity.UserRelationEntity;

public interface UserRelationRepository extends JpaRepository<UserRelationEntity, Integer> {
}
