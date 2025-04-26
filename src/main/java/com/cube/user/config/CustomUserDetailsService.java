package com.cube.user.config;

import com.cube.user.exceptions.UnauthorizedException;
import com.cube.user.dtos.internal.ExceptionCode;
import com.cube.user.models.InternalUser;
import com.cube.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InternalUser internalUser = userRepository.findByMail(username)
                .orElseThrow(() -> new UnauthorizedException(ExceptionCode.INVALID_CREDENTIALS));

        return new User(internalUser.getMail(), internalUser.getPassword(), internalUser.getAuthorities());
    }
}
