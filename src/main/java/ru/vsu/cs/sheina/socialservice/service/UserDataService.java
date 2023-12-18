package ru.vsu.cs.sheina.socialservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.cs.sheina.socialservice.dto.UpdateUserDTO;
import ru.vsu.cs.sheina.socialservice.dto.UserFullDTO;
import ru.vsu.cs.sheina.socialservice.dto.UserRegistrationDTO;
import ru.vsu.cs.sheina.socialservice.dto.UserShortDTO;
import ru.vsu.cs.sheina.socialservice.dto.fields.IdDTO;
import ru.vsu.cs.sheina.socialservice.dto.rabbitmq.FileDTO;
import ru.vsu.cs.sheina.socialservice.entity.UserDataEntity;
import ru.vsu.cs.sheina.socialservice.entity.UserRelationEntity;
import ru.vsu.cs.sheina.socialservice.exception.FileTooBigException;
import ru.vsu.cs.sheina.socialservice.exception.UserAlreadyExistsException;
import ru.vsu.cs.sheina.socialservice.exception.UserDoesntExistException;
import ru.vsu.cs.sheina.socialservice.mapper.UserMapper;
import ru.vsu.cs.sheina.socialservice.repository.UserDataRepository;
import ru.vsu.cs.sheina.socialservice.repository.UserRelationRepository;
import ru.vsu.cs.sheina.socialservice.util.JwtTokenUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final UserDataRepository userDataRepository;
    private final UserRelationRepository userRelationRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final RabbitSender rabbitSender;
    private final Integer FILE_MAX_SIZE = 2 * 1024 * 1024;
    private final UserMapper userMapper;


    public void createUser(IdDTO idDTO) {
        if (userDataRepository.existsById(idDTO.getId())) {
            throw new UserAlreadyExistsException();
        }
        UserDataEntity userDataEntity = new UserDataEntity();
        userDataEntity.setId(idDTO.getId());
        userDataEntity.setAvatarUrl("");
        userDataEntity.setCoverUrl("");
        userDataEntity.setBio("");

        userDataRepository.save(userDataEntity);
    }

    public UserFullDTO getUserData(UUID id, String token) {
        UUID currentId = jwtTokenUtil.retrieveIdClaim(token);
        if (!userDataRepository.existsById(currentId)) {
            throw new UserDoesntExistException();
        }
        UserDataEntity userDataEntity = userDataRepository.getUserDataEntityById(id).orElseThrow(UserDoesntExistException::new);
        UserFullDTO userFullDTO = userMapper.toUserFullDTO(userDataEntity);


        Optional<UserRelationEntity> userRelationEntity = userRelationRepository.getUserRelationEntitiesByFirstUserAndSecondUser(currentId,id);
        userRelationEntity.ifPresent(relationEntity -> userFullDTO.setStatus(relationEntity.getStatus()));

        return userFullDTO;
    }

    public void setUserData(UpdateUserDTO updateUserDTO, MultipartFile avatar, MultipartFile cover, String token) {
        UUID currentId = jwtTokenUtil.retrieveIdClaim(token);
        UserDataEntity userDataEntity = userDataRepository.getUserDataEntityById(currentId).orElseThrow(UserDoesntExistException::new);

        userDataEntity.setName(updateUserDTO.getName());
        userDataEntity.setBirthDate(updateUserDTO.getBirthDate());
        userDataEntity.setBio(updateUserDTO.getBio());

        if (updateUserDTO.getDeleteAvatarFlag()) {
            FileDTO fileDTO = new FileDTO(userDataEntity.getAvatarUrl(), null);
            rabbitSender.sendRequestToFileService(fileDTO);
            userDataEntity.setAvatarUrl("");
        }

        if (!avatar.isEmpty()) {
            if (avatar.getSize() > FILE_MAX_SIZE) {
                throw new FileTooBigException();
            }
            FileDTO fileDTO = new FileDTO(userDataEntity.getAvatarUrl(), avatar);
            userDataEntity.setAvatarUrl(rabbitSender.sendRequestToFileService(fileDTO));
        }

        if (!cover.isEmpty()) {
            if (cover.getSize() > FILE_MAX_SIZE) {
                throw new FileTooBigException();
            }
            FileDTO fileDTO = new FileDTO(userDataEntity.getCoverUrl(), cover);
            userDataEntity.setCoverUrl(rabbitSender.sendRequestToFileService(fileDTO));
        }

        userDataRepository.save(userDataEntity);
    }

    public void register(UserRegistrationDTO userRegistrationDTO) {
        UUID id = userRegistrationDTO.getId();
        if (!userDataRepository.existsById(id)) {
            throw new UserDoesntExistException();
        }

        UserDataEntity userDataEntity = userMapper.fromUserRegistrationDTO(userRegistrationDTO);
        userDataEntity.setId(id);

        userDataRepository.save(userDataEntity);
    }

    public List<UserShortDTO> getAllUsers() {
        return userDataRepository.findAll().stream()
                .map(userMapper::toUserShortDTO)
                .toList();
    }
}
