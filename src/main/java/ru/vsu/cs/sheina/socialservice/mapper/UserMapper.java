package ru.vsu.cs.sheina.socialservice.mapper;

import org.mapstruct.Mapper;
import ru.vsu.cs.sheina.socialservice.dto.UserFullDTO;
import ru.vsu.cs.sheina.socialservice.dto.UserRegistrationDTO;
import ru.vsu.cs.sheina.socialservice.dto.UserShortDTO;
import ru.vsu.cs.sheina.socialservice.entity.UserDataEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDataEntity fromUserRegistrationDTO(UserRegistrationDTO userRegistrationDTO);

    UserFullDTO toUserFullDTO(UserDataEntity userDataEntity);

    UserShortDTO toUserShortDTO(UserDataEntity userDataEntity);
}
