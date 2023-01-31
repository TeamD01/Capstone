package com.skombie.utilities;

import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skombie.Furniture;
import com.skombie.Item;
import com.skombie.Location;

public class InteractionParser {
    Scanner sc = new Scanner(System.in);
    PromptHelper prompt = new PromptHelper(sc);
    private static final String[] VERBS = new String[]{"use", "look", "go", "get", "drop"};
    //    private static final String[] NOUNS = new String[] {"kitchen","bedroom", "basement", "attic", "backyard", "hallway", "office", "living room", "hammer", "nails", "desk"};
    private static final int VERBELEMENT = 0;
    private static final int NOUNELEMENT = 1;
    JsonObject obj = new JsonObject();


    public InteractionParser() {
    }

    // This validates the pattern of the user input and checks to see if they type help or quit and returns array
    // of commands. If help is requested then a help message is displayed. Quit allows the user to exit the game.
    public String[] verifyInput() {
        String[] strSplit;

        System.out.println("Please enter a verb noun command or type \"Help\" to see a list of commands");
        while (true) {
            // Use strip for input
            String temp = sc.nextLine().toLowerCase(Locale.ROOT).trim();
            if (temp.matches("([a-zA-Z  ]*)") && temp.split(" ", 2).length == 2 || temp.split(" ", 2).length == 3) {
                // used \\s+ instead of " "
                strSplit = temp.split("\\s+", 2);
                break;
            } else if (temp.equals("help")) {
                prompt.checkHelp(temp);
                System.out.println("Please enter a verb noun command or type \"Help\" to see a list of commands");
            } else if (temp.equals("quit")) {
                prompt.checkQuit(temp);
            } else {
                System.out.println("Please enter a verb noun command or type \"Help\" to see a list of commands");
            }
        }
        return strSplit;
    }

    // This calls the verifyInput method to get and array of commands and checks to see if the commands passed
    // are part of the official commands for the game. It returns an array of the offical commands.
//    public String[] verifyCommand() {
//        while (true) {
//            String[] command = verifyInput();
//            boolean containsVerb = Arrays.stream(VERBS).anyMatch(command[VERBELEMENT]::equals);
////            boolean containsNoun = Arrays.stream(NOUNS).anyMatch(command[NOUNELEMENT]::equals);
//            if (containsVerb) {
//                System.out.println("You have decided to " + command[VERBELEMENT] + " " + command[NOUNELEMENT]);
//                return command;
//            }
////            else if (!containsVerb && containsNoun) {
////                System.out.println("Your command of \"" + command[VERBELEMENT] + "\" is not recognized.");
////            }
////            else if(containsVerb && !containsNoun) {
////                System.out.println("Your command of \"" + command[NOUNELEMENT] + "\" is not recognized.");
////            }
//            else {
//                System.out.println("Your command of \"" + command[VERBELEMENT] + "\" and \"" + command[NOUNELEMENT] + "\" is not recognized.");
//            }
//        }
//    }

    // This looks at the current location, sees list of available locations and checks
    // if user is able to proceed to next location.
    public String goRoom(Location curLoc, String[] commands) {
        String location = curLoc.getName();
        List<String> availRooms = curLoc.getAvailableRooms();
        for (int i = 0; i < availRooms.size(); i++) {
            String newValue = availRooms.get(i).toLowerCase(Locale.ROOT);
            availRooms.set(i, newValue);
        }
        while (true) {
            if (commands[VERBELEMENT].equals("go") && availRooms.contains(commands[NOUNELEMENT])) {
                System.out.println("You are now heading to " + commands[NOUNELEMENT]);
                location = commands[NOUNELEMENT];
                return location;
            } else {
                System.out.println(commands[NOUNELEMENT] + " is not available to you. Your available rooms are: " + availRooms);
                break;
            }
        }
        return location;
    }


    public String look(Location name, String[] commands) {
        String description = null;
        List<String> itemsString = new ArrayList<>();
        List<Item> items = name.getItems();
        if (items != null) {
            for (Item item : items) {
                itemsString.add(item.getName().toLowerCase(Locale.ROOT));
                if (item.getName().toLowerCase(Locale.ROOT).equals(commands[NOUNELEMENT])) {
                    description = item.getDescription();
                }
            }
        }
        List<String> furnituresString = new ArrayList<>();
        List<Furniture> furnitures = name.getFurniture();
        if (furnitures != null) {
            for (Furniture furniture : furnitures) {
                furnituresString.add(furniture.getName().toLowerCase(Locale.ROOT));
                if (furniture.getName().toLowerCase(Locale.ROOT).equals(commands[NOUNELEMENT])) {
                    description = furniture.getDescription();
                }
            }
        }
        if (description != null) {
            return description;
        }
        else {
            return null;

        }
    }
}
