package com.cube.user.mappers;

import com.cube.user.models.InternalUser;
import com.cube.user.dtos.response.ResponseUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ResponseUser internalToResponse(InternalUser internalUser);
}
