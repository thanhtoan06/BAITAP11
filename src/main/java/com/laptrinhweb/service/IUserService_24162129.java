package com.laptrinhweb.service;

import java.util.List;

import com.laptrinhweb.model.Users_24162129;

public interface IUserService_24162129 {

    Users_24162129 findByUsername(String username);

    Users_24162129 findByEmail(String email);

    boolean checkExistUsername(String username);

    boolean checkExistEmail(String email);

    Users_24162129 login(String username, String password);

    boolean register(String username, String email, String password, String fullname, String phone);

    boolean verifyOTP(String email, String otp);

    void insert(Users_24162129 user);

    List<Users_24162129> findAll(int page, int pageSize);

    int count();

    Users_24162129 findById(int id);

    void update(Users_24162129 user);

    void delete(int id);
}