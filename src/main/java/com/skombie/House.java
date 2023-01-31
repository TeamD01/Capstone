package com.skombie;

import com.skombie.utilities.JSONMapper;

import java.util.List;

/*
* HOUSE CLASS WILL BE USED TO MANAGE ROOMS (POSSIBLE OPTIONS FOR PLAYERS)
* */

public class House {
    JSONMapper map = new JSONMapper();
    List<Location> rooms;

    public House() {
        rooms = map.mapGameJSONtoObjects();
    }

    public Location grabJSONLocation(String location) {
        return rooms.stream().filter(x -> x.getName().equalsIgnoreCase(location)).findFirst().orElse(null);
    }
}