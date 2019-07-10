package com.itacademy.database.repository;

import com.itacademy.database.dto.LoginDto;

public interface ExtendedUserRepository  {

    LoginDto findByLogin(String login);

}
