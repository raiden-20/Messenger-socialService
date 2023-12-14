package ru.vsu.cs.sheina.socialservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.cs.sheina.socialservice.entity.UserRelationEntity;

public interface UserRelationRepository extends JpaRepository<UserRelationEntity, Integer> {
}
