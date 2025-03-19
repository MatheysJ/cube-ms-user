package com.cube.user.mappers;

import com.cube.user.models.internal.InternalUser;
import com.cube.user.models.response.ResponseUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ResponseUser internalToResponse(InternalUser internalUser);
}
