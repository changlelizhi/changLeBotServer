package com.changle.mapper;

import com.changle.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author : 长乐
 * @package : com.changle.mapper
 * @project : changLeBotServer
 * @name : UserMapper
 * @Date : 2025/7/23
 * @Description : 用户mapper
 */
@Repository
public interface UserMapper {

    int insertUser(User user);

    int updateUserInfo(@Param("userId") String userId, @Param("userInfo") String userInfo);

}
