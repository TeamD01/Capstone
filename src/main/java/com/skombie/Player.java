package com.skombie;

import com.google.gson.JsonArray;

import java.util.List;

public class Player {

    House house;

    public Player(House house) {
        this.house = house;
    }

    public void look(Object obj) {
        System.out.println(obj.getClass());
    }


}