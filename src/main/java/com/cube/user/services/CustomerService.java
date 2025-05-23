package com.cube.user.services;

import com.cube.user.dtos.internal.ExceptionCode;
import com.cube.user.dtos.response.Customer;
import com.cube.user.exceptions.NotFoundException;
import com.cube.user.mappers.UserMapper;
import com.cube.user.models.InternalUser;
import com.cube.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {
    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public Customer getCustomerByCpfCnpj(String cpfCnpj) {
        InternalUser internalUser = getInternalUserByIdOrThrow(cpfCnpj);

        return userMapper.internalToCustomer(internalUser);
    }

    private InternalUser getInternalUserByIdOrThrow(String cpfCnpj) throws NotFoundException {
        Optional<InternalUser> internalUser = userRepository.findByCpfCnpj(cpfCnpj);

        if (internalUser.isEmpty()) {
            throw new NotFoundException(ExceptionCode.USER_WITH_CPF_CNPJ_DOESNT_EXIST);
        }

        return internalUser.get();
    }

}
