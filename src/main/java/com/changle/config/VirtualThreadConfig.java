package com.changle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * @author : 长乐
 * @package : com.changle.config
 * @project : changLeBotServer
 * @name : VirtualThreadConfig
 * @Date : 2025/7/28
 * @Description :
 */
@Configuration
public class VirtualThreadConfig {

    @Bean(name = "virtualThreadExecutor" )
    public Executor virtualThreadExecutor() {
        return Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("changLe-vir-",0).factory());
    }
}
