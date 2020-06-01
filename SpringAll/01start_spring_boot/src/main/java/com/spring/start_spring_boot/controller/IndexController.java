package com.spring.start_spring_boot.controller;

import com.spring.start_spring_boot.entity.GameValue;
import com.spring.start_spring_boot.entity.GameConfigurationProperties;
import com.spring.start_spring_boot.entity.GameProperSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class IndexController {

    @Resource
    private GameValue game;

    @Resource
    private GameConfigurationProperties game2;

    @Resource
    private GameProperSource game3;

    @RequestMapping("/")
    public String index() {
        return game.getGame() + "--" + game.getName();
    }

    @RequestMapping("/2")
    public String index2() {
        return game2.getThreeKingdoms() + "--" + game2.getName()+ game2.getWholename();
    }

    @RequestMapping("/3")
    public String index3() {
        return game3.getThreeKingdoms() + "--" + game3.getName();
    }
}
