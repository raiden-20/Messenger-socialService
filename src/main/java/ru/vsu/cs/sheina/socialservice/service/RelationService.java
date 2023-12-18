package ru.vsu.cs.sheina.socialservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.sheina.socialservice.dto.ActionDTO;
import ru.vsu.cs.sheina.socialservice.dto.UserShortDTO;
import ru.vsu.cs.sheina.socialservice.entity.UserDataEntity;
import ru.vsu.cs.sheina.socialservice.entity.UserRelationEntity;
import ru.vsu.cs.sheina.socialservice.entity.enums.RelationRequest;
import ru.vsu.cs.sheina.socialservice.entity.enums.RelationStatus;
import ru.vsu.cs.sheina.socialservice.exception.RelationAlreadyExistException;
import ru.vsu.cs.sheina.socialservice.exception.RelationDoesntExistException;
import ru.vsu.cs.sheina.socialservice.exception.UserDoesntExistException;
import ru.vsu.cs.sheina.socialservice.mapper.UserMapper;
import ru.vsu.cs.sheina.socialservice.repository.UserDataRepository;
import ru.vsu.cs.sheina.socialservice.repository.UserRelationRepository;
import ru.vsu.cs.sheina.socialservice.util.JwtTokenUtil;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RelationService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDataRepository userDataRepository;
    private final UserRelationRepository userRelationRepository;
    private final UserMapper userMapper;
    private final Integer RANDOM_FRIENDS = 6;
    private final Random random = new Random();

    public List<UserShortDTO> getRandomFriends(UUID id) {
        List<UserRelationEntity> relations = userRelationRepository.getUserRelationEntitiesByFirstUserAndStatus(id, RelationStatus.FRIENDS.toString());
        List<UserShortDTO> randomFriends = new ArrayList<>();

        for (int i = 0; i < RANDOM_FRIENDS; i++) {
            UserRelationEntity userRelationEntity = relations.get(random.nextInt(relations.size()));
            UserDataEntity userDataEntity = userDataRepository.getUserDataEntityById(userRelationEntity.getSecondUser())
                    .orElseThrow(UserDoesntExistException::new);

            UserShortDTO userShortDTO = userMapper.toUserShortDTO(userDataEntity);
            randomFriends.add(userShortDTO);

            relations.remove(userRelationEntity);
        }
        return  randomFriends;
    }

    public List<UserShortDTO> getUsers(UUID id, String relation) {
        RelationRequest relationRequest = RelationRequest.valueOf(relation.toUpperCase());
        List<UserRelationEntity> relations = switch (relationRequest) {
            case FRIENDS ->
                    userRelationRepository.getUserRelationEntitiesByFirstUserAndStatus(id, RelationStatus.FRIENDS.toString());
            case SUBSCRIBERS ->
                    userRelationRepository.getUserRelationEntitiesByFirstUserAndStatus(id, RelationStatus.SEND_SECOND.toString());
            case SUBSCRIPTIONS ->
                    userRelationRepository.getUserRelationEntitiesByFirstUserAndStatus(id, RelationStatus.SEND_FIRST.toString());
        };

        return relations.stream()
                .map(UserRelationEntity::getSecondUser)
                .map(userDataRepository::getUserDataEntityById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(userMapper::toUserShortDTO)
                .toList();
    }

    public Integer getCountUsers(UUID id, String relation) {
        RelationRequest relationRequest = RelationRequest.valueOf(relation.toUpperCase());
        Integer count = 0;

        switch (relationRequest) {
            case FRIENDS ->
                count = userRelationRepository.countByFirstUserAndStatus(id, RelationStatus.FRIENDS.toString());
            case SUBSCRIBERS ->
                count = userRelationRepository.countByFirstUserAndStatus(id, RelationStatus.SEND_SECOND.toString());
            case SUBSCRIPTIONS ->
                count = userRelationRepository.countByFirstUserAndStatus(id, RelationStatus.SEND_FIRST.toString());
        }
        return count;
    }

    public void action(ActionDTO actionDTO, String token) {
        UUID currentId = jwtTokenUtil.retrieveIdClaim(token);
        switch (actionDTO.getAction()) {
            case CREATE ->
                    createRelationRequest(currentId, actionDTO.getSecondUser());
            case DELETE_FRIEND ->
                    deleteFriend(currentId, actionDTO.getSecondUser());
            case ACCEPT ->
                    acceptRelationRequest(currentId, actionDTO.getSecondUser());
            case REJECT ->
                    rejectRelationRequest(currentId, actionDTO.getSecondUser());
            case DELETE_REQUEST ->
                    deleteRequest(currentId, actionDTO.getSecondUser());
        }
    }

    private void createRelationRequest(UUID firstId, UUID secondId){
        if (userRelationRepository.existsByFirstUserAndSecondUser(firstId, secondId)) {
            throw new RelationAlreadyExistException();
        }

        UserRelationEntity firstUserRelationEntity = new UserRelationEntity();
        firstUserRelationEntity.setFirstUser(firstId);
        firstUserRelationEntity.setSecondUser(secondId);
        firstUserRelationEntity.setStatus(RelationStatus.SEND_FIRST);

        UserRelationEntity secondUserRelationEntity = new UserRelationEntity();
        secondUserRelationEntity.setFirstUser(secondId);
        secondUserRelationEntity.setSecondUser(firstId);
        secondUserRelationEntity.setStatus(RelationStatus.SEND_SECOND);

        userRelationRepository.save(firstUserRelationEntity);
        userRelationRepository.save(secondUserRelationEntity);
    }

    private void deleteFriend(UUID firstId, UUID secondId){
        UserRelationEntity firstUserRelationEntity = userRelationRepository.getUserRelationEntitiesByFirstUserAndSecondUser(firstId, secondId)
                .orElseThrow(RelationDoesntExistException::new);
        UserRelationEntity secondUserRelationEntity = userRelationRepository.getUserRelationEntitiesByFirstUserAndSecondUser(secondId, firstId)
                .orElseThrow(RelationDoesntExistException::new);

        if (!firstUserRelationEntity.getStatus().equals(RelationStatus.FRIENDS)) {
            throw new RelationDoesntExistException();
        }

        firstUserRelationEntity.setStatus(RelationStatus.SEND_SECOND);
        secondUserRelationEntity.setStatus(RelationStatus.SEND_FIRST);

        userRelationRepository.save(firstUserRelationEntity);
        userRelationRepository.save(secondUserRelationEntity);
    }

    private void acceptRelationRequest(UUID firstId, UUID secondId){
        UserRelationEntity firstUserRelationEntity = userRelationRepository.getUserRelationEntitiesByFirstUserAndSecondUser(firstId, secondId)
                .orElseThrow(RelationDoesntExistException::new);
        UserRelationEntity secondUserRelationEntity = userRelationRepository.getUserRelationEntitiesByFirstUserAndSecondUser(secondId, firstId)
                .orElseThrow(RelationDoesntExistException::new);
        if (!firstUserRelationEntity.getStatus().equals(RelationStatus.SEND_SECOND)) {
            throw new RelationDoesntExistException();
        }

        firstUserRelationEntity.setStatus(RelationStatus.FRIENDS);
        secondUserRelationEntity.setStatus(RelationStatus.FRIENDS);

        userRelationRepository.save(firstUserRelationEntity);
        userRelationRepository.save(secondUserRelationEntity);
    }

    private void rejectRelationRequest(UUID firstId, UUID secondId){
        UserRelationEntity firstUserRelationEntity = userRelationRepository.getUserRelationEntitiesByFirstUserAndSecondUser(firstId, secondId)
                .orElseThrow(RelationDoesntExistException::new);
        UserRelationEntity secondUserRelationEntity = userRelationRepository.getUserRelationEntitiesByFirstUserAndSecondUser(secondId, firstId)
                .orElseThrow(RelationDoesntExistException::new);
        if (!firstUserRelationEntity.getStatus().equals(RelationStatus.SEND_SECOND)) {
            throw new RelationDoesntExistException();
        }

        userRelationRepository.delete(firstUserRelationEntity);
        userRelationRepository.delete(secondUserRelationEntity);
    }

    private void deleteRequest(UUID firstId, UUID secondId){
        UserRelationEntity firstUserRelationEntity = userRelationRepository.getUserRelationEntitiesByFirstUserAndSecondUser(firstId, secondId)
                .orElseThrow(RelationDoesntExistException::new);
        UserRelationEntity secondUserRelationEntity = userRelationRepository.getUserRelationEntitiesByFirstUserAndSecondUser(secondId, firstId)
                .orElseThrow(RelationDoesntExistException::new);

        if (!firstUserRelationEntity.getStatus().equals(RelationStatus.SEND_FIRST)) {
            throw new RelationDoesntExistException();
        }

        userRelationRepository.delete(firstUserRelationEntity);
        userRelationRepository.delete(secondUserRelationEntity);
    }
}
