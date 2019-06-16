package com.itacademy.service;

import com.itacademy.database.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        log.info("loadUserByLogin service<-dao");
        return Optional.of(login)
                .map(userRepository::findByLogin)
                .map(loginDTO -> org.springframework.security.core.userdetails.User.builder()
                        .username(loginDTO.getLogin())
                        .password(loginDTO.getPassword())
                        .authorities(loginDTO.getRole())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

