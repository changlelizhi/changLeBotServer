package com.changle.controller;

import com.changle.dto.ApiResult;
import com.changle.dto.request.UserRegisterRequest;
import com.changle.entity.User;
import com.changle.service.UserService;
import com.changle.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author : 长乐
 * @package : com.changle.controller
 * @project : changLeBotServer
 * @name : UserController
 * @Date : 2025/7/23
 * @Description : 用户控制类
 */
@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *  注册
     * @param request
     * @return
     */
    @PostMapping("/register")
    public ApiResult<?> register(@RequestBody UserRegisterRequest request) {
        String firstName = request.getFirstName();
        String lastName = request.getLastName();
        String userName = UserUtils.combineUsername(firstName, lastName);
        User user = User.builder().userId(request.getUserId()).userName(userName).tgName(request.getTgName()).build();
        return userService.register(user) ? ApiResult.success() : ApiResult.fail();
    }
}
