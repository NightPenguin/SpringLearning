package com.spring.start_spring_boot.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class GameValue {

    @Value("${dream.threeKingdoms}")
    private String game;
    @Value("${dream.name}")
    private String name;
    @Value("${dream.whole-name}")
    private String wholename;

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
