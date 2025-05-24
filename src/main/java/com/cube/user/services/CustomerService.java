package com.cube.user.services;

import com.cube.user.dtos.internal.ExceptionCode;
import com.cube.user.dtos.response.Customer;
import com.cube.user.exceptions.NotFoundException;
import com.cube.user.mappers.UserMapper;
import com.cube.user.models.InternalUser;
import com.cube.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {
    private final UserMapper userMapper;

    private final UserRepository userRepository;

    public Customer getCustomer(String customerId, String cpfCnpj) {
        log.info("Start getting user with customerId {} and cpfCnpj {}", customerId, cpfCnpj);

        if (!customerId.isBlank()) return getCustomerByCpfCnpj(cpfCnpj);
        return getCustomerByMail(customerId);
    }

    public Customer getCustomerByCpfCnpj(String cpfCnpj) {
        log.info("Start getting user with cpfCnpj {}", cpfCnpj);
        InternalUser internalUser = getUserByCpfCnpjOrThrow(cpfCnpj);

        log.info("Successfully got user with cpfCnpj {}", cpfCnpj);
        return userMapper.internalToCustomer(internalUser);
    }

    public Customer getCustomerByMail(String mail) {
        log.info("Start getting user with mail {}", mail);
        InternalUser internalUser = getUserByMailOrThrow(mail);

        log.info("Successfully got user with mail {}", mail);
        return userMapper.internalToCustomer(internalUser);
    }

    private InternalUser getUserByCpfCnpjOrThrow(String cpfCnpj) throws NotFoundException {
        Optional<InternalUser> internalUser = userRepository.findByCpfCnpj(cpfCnpj);

        if (internalUser.isEmpty()) {
            throw new NotFoundException(ExceptionCode.USER_WITH_CPF_CNPJ_DOESNT_EXIST);
        }

        return internalUser.get();
    }

    private InternalUser getUserByMailOrThrow(String mail) throws NotFoundException {
        Optional<InternalUser> internalUser = userRepository.findByMail(mail);

        if (internalUser.isEmpty()) {
            throw new NotFoundException(ExceptionCode.USER_WITH_MAIL_DOESNT_EXIST);
        }

        return internalUser.get();
    }

}
