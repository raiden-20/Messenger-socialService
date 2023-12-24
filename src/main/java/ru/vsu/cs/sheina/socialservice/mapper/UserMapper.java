package ru.vsu.cs.sheina.socialservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.vsu.cs.sheina.socialservice.dto.UserFullDTO;
import ru.vsu.cs.sheina.socialservice.dto.UserRegistrationDTO;
import ru.vsu.cs.sheina.socialservice.dto.UserShortDTO;
import ru.vsu.cs.sheina.socialservice.entity.UserDataEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "bio", defaultValue = "")
    @Mapping(target = "avatarUrl", defaultValue = "")
    @Mapping(target = "coverUrl", defaultValue = "")
    UserDataEntity fromUserRegistrationDTO(UserRegistrationDTO userRegistrationDTO);

    UserFullDTO toUserFullDTO(UserDataEntity userDataEntity);

    UserShortDTO toUserShortDTO(UserDataEntity userDataEntity);
}
