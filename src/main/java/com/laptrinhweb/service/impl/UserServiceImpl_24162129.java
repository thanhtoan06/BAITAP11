package com.laptrinhweb.service.impl;

import java.util.List;

import com.laptrinhweb.dao.IUserDao_24162129;
import com.laptrinhweb.dao.impl.UserDaoImpl_24162129;
import com.laptrinhweb.model.Users_24162129;
import com.laptrinhweb.service.IUserService_24162129;
import com.laptrinhweb.util.EmailUtil_24162129;

public class UserServiceImpl_24162129 implements IUserService_24162129 {

    private final IUserDao_24162129 userDao;

    public UserServiceImpl_24162129() {
        this(new UserDaoImpl_24162129());
    }

    public UserServiceImpl_24162129(IUserDao_24162129 userDao) {
        this.userDao = userDao;
    }

    @Override
    public Users_24162129 findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public Users_24162129 findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDao.checkExistUsername(username);
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public Users_24162129 login(String username, String password) {
        return userDao.login(username, password);
    }

    @Override
    public boolean register(String username, String email, String password, String fullname, String phone) {
        if (userDao.checkExistUsername(username) || userDao.checkExistEmail(email)) {
            return false;
        }

        String otp = EmailUtil_24162129.getRandomOTP();
        Users_24162129 user = new Users_24162129();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setCode(otp);
        user.setStatus(0);
        user.setRoleId(2);
        userDao.insert(user);

        String body = "<p>Mã OTP kích hoạt tài khoản của bạn là: <strong>" + otp + "</strong></p>";
        return EmailUtil_24162129.sendEmail(email, "Mã OTP kích hoạt tài khoản", body);
    }

    @Override
    public boolean verifyOTP(String email, String otp) {
        Users_24162129 user = userDao.findByEmail(email);
        if (user == null || user.getCode() == null || !user.getCode().equals(otp)) {
            return false;
        }

        userDao.updateStatus(email, 1);
        userDao.updateCode(email, null);
        return true;
    }

    @Override
    public void insert(Users_24162129 user) {
        userDao.insert(user);
    }

    @Override
    public List<Users_24162129> findAll(int page, int pageSize) {
        return userDao.findAll(page, pageSize);
    }

    @Override
    public int count() {
        return userDao.count();
    }

    @Override
    public Users_24162129 findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public void update(Users_24162129 user) {
        userDao.update(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }
}