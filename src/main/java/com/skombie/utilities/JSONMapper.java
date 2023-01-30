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
    private static JSONMapper roomsStatus;
    private static final Gson gson = new Gson();
    private static final List<Location> locationList = new ArrayList<>();

    private JSONMapper() {

    }

    public static JSONMapper getInstance() {
        if (roomsStatus == null) {
            try {
                roomsStatus = new JSONMapper();
                // Next 4 lines could be private helper method (takes path and new array list)
                // Call several times here to make multiple array lists of classes Location, Weapon, Item, Character etc etc.
                String locationString = new String(Files.readAllBytes(Paths.get("src/main/resources/data/locations.json")));
                JsonObject jObj = gson.fromJson(locationString, JsonObject.class);
                JsonArray locationsArray = jObj.getAsJsonArray("locations");
                locationsArray.forEach(item -> locationList.add(gson.fromJson(item, Location.class)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return roomsStatus;
    }

    // We can make several other methods here like this one for grabbing data from the various other Classes (Item, Character, etc)
    public Location grabJSONLocation(String location) {
        return locationList.stream().filter(x -> x.getName().equalsIgnoreCase(location)).findFirst().orElse(null);
    }

    public static List<Location> getRoomsStatus() {
        return locationList;
    }



}