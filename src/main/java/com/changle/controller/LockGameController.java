package com.changle.controller;

import com.changle.dto.request.LockGameRequest;
import com.changle.dto.response.LockGameResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.print.DocFlavor;

/**
 * @author : 长乐
 * @package : com.changle.controller
 * @project : changLeBotServer
 * @name : LockGameController
 * @Date : 2025/7/31
 * @Description :
 */
@Controller
@RequestMapping("/api/lock")
public class LockGameController {

    @GetMapping("/query")
    public LockGameResponse queryLockGame(String userId){
        return new LockGameResponse();
    }

    @PostMapping("/save")
    public LockGameResponse saveLockGame(@RequestBody LockGameRequest lockGameRequest){
        
        return new LockGameResponse();
    }
}
