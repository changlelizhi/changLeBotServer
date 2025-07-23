package com.changle.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : 长乐
 * @package : com.changle.dto.request
 * @project : changLeBotServer
 * @name : UserRegisterRequest
 * @Date : 2025/7/23
 * @Description : 用户注册请求
 */
@Getter
public class UserRegisterRequest {

    @NotBlank(message = "用户ID不能为空")
    @Size(max = 30, message = "用户ID长度不能超过30")
    private String userId;

    @Size(max = 30, message = "用户第一个名字长度不能超过30")
    private String firstName;

    @Size(max = 30, message = "用户第二个名字长度不能超过30")
    private String lastName;

    @Size(max = 30, message = "用户TG名字长度不能超过30")
    private String tgName;

    public UserRegisterRequest() {
    }
}
