package com.skombie.utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skombie.Character;
import com.skombie.Furniture;
import com.skombie.Item;
import com.skombie.Location;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONMapper {

    //Dont want static fields, only static instance itself.
    private static final Gson gson = new Gson();


    private static final String LOCATIONSPATH = "data/locations.json";

    public JSONMapper() {
    }

    public List<Location> getCreateLocationsList() {
            //GSON just needs reader, Key ingredient for reading from resources!
            List<Location> list = new ArrayList<>();
            try(Reader reader = new InputStreamReader(JSONMapper.class.getClassLoader().getResourceAsStream(LOCATIONSPATH))) {
//                Location[] locations = gson.fromJson(reader,Location[].class); Alternate way of doing next line
                Type locationListType = new TypeToken<ArrayList<Location>>(){}.getType();
                list = gson.fromJson(reader,locationListType);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
    }

    public List<Item> getCreateItemsList() {
        //GSON just needs reader, Key ingredient for reading from resources!
        List<Item> list = new ArrayList<>();
        try(Reader reader = new InputStreamReader(JSONMapper.class.getClassLoader().getResourceAsStream(LOCATIONSPATH))) {
//                Location[] locations = gson.fromJson(reader,Location[].class); Alternate way of doing next line
            Type locationListType = new TypeToken<ArrayList<Location>>(){}.getType();
            list = gson.fromJson(reader,locationListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Character> getCreateCharactersList() {
        //GSON just needs reader, Key ingredient for reading from resources!
        List<Character> list = new ArrayList<>();
        try(Reader reader = new InputStreamReader(JSONMapper.class.getClassLoader().getResourceAsStream(LOCATIONSPATH))) {
//                Location[] locations = gson.fromJson(reader,Location[].class); Alternate way of doing next line
            Type locationListType = new TypeToken<ArrayList<Location>>(){}.getType();
            list = gson.fromJson(reader,locationListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Furniture> getCreateFurnitureList() {
        //GSON just needs reader, Key ingredient for reading from resources!
        List<Furniture> list = new ArrayList<>();
        try(Reader reader = new InputStreamReader(JSONMapper.class.getClassLoader().getResourceAsStream(LOCATIONSPATH))) {
//                Location[] locations = gson.fromJson(reader,Location[].class); Alternate way of doing next line
            Type locationListType = new TypeToken<ArrayList<Location>>(){}.getType();
            list = gson.fromJson(reader,locationListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
//    // We can make several other methods here like this one for grabbing data from the various other Classes (Item, Character, etc)
//    public Location grabJSONLocation(String location) {
//        return locationList.stream().filter(x -> x.getName().equalsIgnoreCase(location)).findFirst().orElse(null);
//    }


}