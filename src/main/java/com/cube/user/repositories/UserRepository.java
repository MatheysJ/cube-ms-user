package com.cube.user.repositories;

import com.cube.user.models.InternalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<InternalUser, Long> {

    Optional<InternalUser> findById(Long id);

    Optional<InternalUser> findByMail(String mail);

    Optional<InternalUser> findByCpfCnpj(String cpfCnpj);
}
