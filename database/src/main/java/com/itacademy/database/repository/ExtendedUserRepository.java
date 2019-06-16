package com.itacademy.database.repository;

import com.itacademy.database.dto.LoginDTO;

public interface ExtendedUserRepository {

    LoginDTO findByLogin(String login);
}
