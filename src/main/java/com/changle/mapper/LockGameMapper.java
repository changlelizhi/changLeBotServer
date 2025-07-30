package com.changle.mapper;

import com.changle.entity.LockGame;
import org.apache.ibatis.annotations.Param;

/**
 * @author : 长乐
 * @package : com.changle.mapper
 * @project : changLeBotServer
 * @name : LockGameMapper
 * @Date : 2025/7/30
 * @Description :
 */
public interface LockGameMapper {

    LockGame selectByUserId(@Param("userId") String userId);

    Integer insertLockGame(LockGame lockGame);

}
