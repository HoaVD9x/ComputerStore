package com.example.computerstore.service;

import com.example.computerstore.Payload.LoginPayload;
import com.example.computerstore.model.User;

public interface LoginService {

    User login (LoginPayload loginPayload);
}
