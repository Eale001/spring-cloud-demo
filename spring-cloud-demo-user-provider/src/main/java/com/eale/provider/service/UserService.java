package com.eale.provider.service;

import com.eale.provider.entity.User;

/**
 * @Author Admin
 * @Date 2020/8/7
 * @Description
 * @Version 1.0
 **/
public interface UserService {

    public User getUser(Integer id);

    User saveUser(User user);

}
