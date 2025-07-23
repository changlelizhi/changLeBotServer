package com.changle.service;

import com.changle.dto.request.UserRegisterRequest;
import com.changle.entity.User;

/**
 * @author : 长乐
 * @package : com.changle.service
 * @project : changLeBotServer
 * @name : UserService
 * @Date : 2025/7/23
 * @Description : 用户服务接口
 */
public interface UserService {


    boolean register(User user);
}
