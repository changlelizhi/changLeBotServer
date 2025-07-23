package com.changle.service.impl;

import com.changle.entity.User;
import com.changle.mapper.UserMapper;
import com.changle.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : 长乐
 * @package : com.changle.service.impl
 * @project : changLeBotServer
 * @name : UserServiceImpl
 * @Date : 2025/7/23
 * @Description :
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(User user) {
        userMapper.insertUser(user);


        return false;
    }
}
