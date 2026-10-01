package com.laptrinhweb.dao;

import java.util.List;

import com.laptrinhweb.model.Users_24162129;

public interface IUserDao_24162129 {

    Users_24162129 findByUsername(String username);

    Users_24162129 findByEmail(String email);

    boolean checkExistUsername(String username);

    boolean checkExistEmail(String email);

    void insert(Users_24162129 user);

    void updateStatus(String email, int status);

    void updateCode(String email, String code);

    Users_24162129 login(String username, String password);

    List<Users_24162129> findAll(int page, int pageSize);

    int count();

    Users_24162129 findById(int id);

    void update(Users_24162129 user);

    void delete(int id);
}