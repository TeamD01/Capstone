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
    private final Gson gson = new Gson();
    private final String LOCATIONS_PATH = "data/locations.json";

    public JSONMapper() {
    }

    public List<Location> mapGameJSONtoObjects() {
            //GSON just needs reader, Key ingredient for reading from resources!
            List<Location> list = new ArrayList<>();
            try(Reader reader = new InputStreamReader(JSONMapper.class.getClassLoader().getResourceAsStream(LOCATIONS_PATH))) {
//                Location[] locations = gson.fromJson(reader,Location[].class); Alternate way of doing next line
                Type locationListType = new TypeToken<ArrayList<Location>>(){}.getType();
                list = gson.fromJson(reader,locationListType);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
    }
}