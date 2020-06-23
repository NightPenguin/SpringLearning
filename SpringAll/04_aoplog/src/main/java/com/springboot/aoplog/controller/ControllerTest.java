package com.springboot.aoplog.controller;

import com.springboot.aoplog.annotation.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ControllerTest {

    @Log("执行方法一")
    @GetMapping("/one")
    public void methodone(String args){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Log("执行方法二")
    @GetMapping("/two")
    public void methodTwo(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Log("执行方法三")
    @GetMapping("/three")
    public void methodThree(String username, String args){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Optional.of("已经通知被调用的方法").ifPresent(System.out::println);
    }
}
