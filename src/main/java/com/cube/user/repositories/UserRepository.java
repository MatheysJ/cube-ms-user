package com.cube.user.repositories;

import com.cube.user.models.internal.InternalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<InternalUser, Long> {

    @Override
    Optional<InternalUser> findById(Long id);
}
