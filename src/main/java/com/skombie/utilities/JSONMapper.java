package com.skombie.utilities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skombie.model.Location;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JSONMapper {
    //Dont want static fields, only static instance itself.
    private static final Gson gson = new Gson();
    private static final String LOCATIONS_PATH = "data/locations.json";


    public JSONMapper() {
    }

    public List<Location> mapGameJSONtoObjects() {
            List<Location> list = new ArrayList<>();
            try(Reader reader = new InputStreamReader(Objects.requireNonNull(JSONMapper.class.getClassLoader().getResourceAsStream(LOCATIONS_PATH)))) {
                Type locationListType = new TypeToken<ArrayList<Location>>(){}.getType();
                list = gson.fromJson(reader,locationListType);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;
    }
}