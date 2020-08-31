package com.eale.provider.service.impl;

import com.eale.provider.entity.User;
import com.eale.provider.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author Admin
 * @Date 2020/8/7
 * @Description
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {


    @Override
    public User getUser(Integer id) {
        return new User(1, "provider-user1", "123456", 18);
    }

    @Override
    public User saveUser(User user) {
        System.out.println("保存一个 provider "+user.toString());
        return user;
    }
}
