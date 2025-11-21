package ru.itis.service;

import ru.itis.dao.UserDao;
import ru.itis.entity.UserEntity;
import ru.itis.util.HashUtil;

import java.sql.SQLException;

public class UserService {

    private final UserDao userDao = new UserDao();

//    public  void init() {
//    }

    public UserEntity login(String username, String password) {
        try {
            UserEntity user = userDao.findByUsername(username);
            if (user !=null && HashUtil.verify(password, user.getHashPassword())) {
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registration(String username, String password) {
        try {
            if (userDao.findByUsername(username) != null) {
                return  false;
            }

            UserEntity user = new UserEntity();
            user.setUsername(username);
            user.setHashPassword(HashUtil.hash(password));
            user.setRole("user");
            userDao.save(user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
