package com.example.service;

import com.example.pojo.Result;

public interface IMailService {
    Result getCode(String username, String email);
}
