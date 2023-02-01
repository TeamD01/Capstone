package com.skombie.utilities;

import com.google.gson.JsonObject;

public class InteractionParser {

    public String[] verifyInput(String userInput) {
        String temp = userInput.toLowerCase().trim();
        String[] strSplit = null;

        if (temp.matches("([a-zA-Z  ]*)") && temp.split(" ", 2).length == 2 || temp.split(" ", 2).length == 3) {
            // used \\s+ instead of " "
            strSplit = temp.split("\\s+", 2);
        }
        return strSplit;
    }
}