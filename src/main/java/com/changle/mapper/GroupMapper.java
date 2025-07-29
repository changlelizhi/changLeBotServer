package com.changle.mapper;

import com.changle.entity.Group;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author : 长乐
 * @package : com.changle.mapper
 * @project : changLeBotServer
 * @name : GroupMapper
 * @Date : 2025/7/25
 * @Description : 群组Mapper
 */
@Repository
public interface GroupMapper {

    int insertGroup(Group group);

    void updateGroupStatus(@Param("chatId") String chatId, @Param("status") Integer status);


}
