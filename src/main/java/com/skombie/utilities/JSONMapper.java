package com.skombie.utilities;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.skombie.Location;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSONMapper {
    private static final Gson gson = new Gson();
    private static final List<Location> locationList = new ArrayList<>();

    public static Location grabJSONLocation(String location) {

        if(locationList.isEmpty()) {
            try {
                String locationString = new String(Files.readAllBytes(Paths.get("src/main/resources/data/locations.json")));
                JsonObject jObj = gson.fromJson(locationString, JsonObject.class);

                JsonArray locationsArray = jObj.getAsJsonArray("locations");

                locationsArray.forEach(item -> locationList.add(gson.fromJson(item, Location.class)));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return locationList.stream().filter(x -> x.getName().equalsIgnoreCase(location)).findFirst().orElse(null);
    }

}