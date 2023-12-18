package ru.vsu.cs.sheina.socialservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.vsu.cs.sheina.socialservice.entity.UserRelationEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRelationRepository extends JpaRepository<UserRelationEntity, Integer> {

    boolean existsByFirstUserAndSecondUser(UUID firstId, UUID secondId);

    @Query("SELECT COUNT(e) FROM UserRelationEntity e WHERE e.firstUser = :firstId AND CAST(e.status AS string) = :status")
    Integer countByFirstUserAndStatus(@Param("firstId") UUID firstId,
                                      @Param("status") String status);

    Optional<UserRelationEntity> getUserRelationEntitiesByFirstUserAndSecondUser(UUID firstId, UUID secondId);

    @Query("SELECT e FROM UserRelationEntity e WHERE e.firstUser = :firstId AND CAST(e.status AS string) = :status")
    List<UserRelationEntity> getUserRelationEntitiesByFirstUserAndStatus(@Param("firstId") UUID firstId,
                                                                         @Param("status") String status
    );


}
