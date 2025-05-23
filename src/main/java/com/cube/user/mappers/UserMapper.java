package com.cube.user.mappers;

import com.cube.user.dtos.internal.asaas.request.CreateCustomerBody;
import com.cube.user.dtos.request.RequestUser;
import com.cube.user.dtos.response.Customer;
import com.cube.user.dtos.response.InternalResponseUser;
import com.cube.user.models.InternalUser;
import com.cube.user.dtos.response.ResponseUser;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    ResponseUser internalToResponse(InternalUser internalUser);

    @Mapping( source = "mail", target = "email" )
    @Mapping( source = "phone", target = "mobilePhone" )
    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    CreateCustomerBody requestToAsaas(RequestUser requestUser);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    InternalUser requestToInternal(RequestUser requestUser);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    InternalResponseUser internalToInternalResponse(InternalUser internalUser);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    Customer internalToCustomer(InternalUser internalResponseUser);
}
