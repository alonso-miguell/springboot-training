package com.training.spring.demo.mappers;

import java.util.List;

import com.training.spring.demo.dtos.UserMapStructDto;
import com.training.spring.demo.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring")
public interface UserMapperStruct {

    UserMapperStruct INSTANCE = Mappers.getMapper(UserMapperStruct.class);

    //User To UserMapperStructDto
    //This is for mapping fields with different names between classes
    @Mappings({
            @Mapping(source= "email", target="emailaddress"),
            @Mapping(source = "role", target="rolename"),
            @Mapping(source = "username", target="nickname"),

    })
    UserMapStructDto userToUserMsDto(User user);

    List<UserMapStructDto> usersToUserDtos(List<User> users);
}