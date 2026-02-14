package com.apnapg.mappers;
import com.apnapg.config.CentralMapperConfig;
import com.apnapg.dto.auth.UserResponseDTO;
import com.apnapg.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CentralMapperConfig.class)
public interface UserMapper {

    @Mapping(target = "role", expression = "java(user.getRole().name())")
    UserResponseDTO toDto(User user);
}
