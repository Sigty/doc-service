package com.itacademy.service;

import com.itacademy.database.entity.User;
import com.itacademy.database.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        log.info("findAllUser service<-dao");
        List<User> allUser = new ArrayList<>();
        userRepository.findAll().forEach(allUser::add);
        return allUser;
    }
}