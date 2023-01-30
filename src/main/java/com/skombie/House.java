package com.skombie;

import com.skombie.utilities.JSONMapper;

import java.util.List;

public class House {
    JSONMapper map = new JSONMapper();
    List<Location> rooms;
    List<Item> items;
    List<Furniture> furnitures;
    List<Character> characters;

    public House() {
        rooms = map.getCreateLocationsList();
        items = map.getCreateItemsList();
        furnitures = map.getCreateFurnitureList();
        characters = map.getCreateCharactersList();
    }

    public Location grabJSONLocation(String location) {
        return rooms.stream().filter(x -> x.getName().equalsIgnoreCase(location)).findFirst().orElse(null);
    }
    public Item grabJSONItemDesc(String item) {
        return items.stream().filter(x -> x.getDescription().equalsIgnoreCase(item)).findFirst().orElse(null);
    }
}