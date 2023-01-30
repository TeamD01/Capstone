package com.skombie.utilities;
import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skombie.Item;
import com.skombie.Location;

public class InteractionParser {
    Scanner sc = new Scanner(System.in);
    PromptHelper prompt = new PromptHelper(sc);
    private static final String[] VERBS = new String[] {"use", "look", "go", "get", "drop"};
    private static final String[] NOUNS = new String[] {"kitchen","bedroom", "basement", "attic", "backyard", "hallway", "office", "living room", "hammer", "nails", "desk"};
    JsonObject obj = new JsonObject();


    public InteractionParser() {
    }

    // This validates the pattern of the user input and checks to see if they type help or quit and returns array
    // of commands. If help is requested then a help message is displayed. Quit allows the user to exit the game.
    private String[] verifyInput() {
        String[] strSplit;

        System.out.println("Please enter a verb noun command or type \"Help\" to see a list of commands");
        while (true) {
            // Use strip for input
            String temp = sc.nextLine().toLowerCase(Locale.ROOT);
            if (temp.matches("([a-zA-Z  ]*)") && temp.split(" ", 2).length == 2 || temp.split(" ", 2).length == 3) {
                // used \\s+ instead of " "
                strSplit = temp.split("\\s+",2);
                break;
            }
            else if (temp.equals("help")) {
                prompt.checkHelp(temp);
                System.out.println("Please enter a verb noun command or type \"Help\" to see a list of commands");
            }
            else if (temp.equals("quit")) {
                prompt.checkQuit(temp);
            }
            else {
                System.out.println("Please enter a verb noun command or type \"Help\" to see a list of commands");
            }
        }
        return  strSplit;
    }

    // This calls the verifyInput method to get and array of commands and checks to see if the commands passed
    // are part of the official commands for the game. It returns an array of the offical commands.
    public String[] verifyCommand() {
        while (true) {
            String[] command = verifyInput();
            System.out.println(command[0]);
            System.out.println(command[1]);
            boolean containsVerb = Arrays.stream(VERBS).anyMatch(command[0]::equals);
            boolean containsNoun = Arrays.stream(NOUNS).anyMatch(command[1]::equals);
            if (containsVerb && containsNoun) {
                System.out.println("You have decided to " + command[0] + " " + command[1]);
                return command;
            }
            else if (!containsVerb && containsNoun) {
                System.out.println("Your command of \"" + command[0] + "\" is not recognized.");
            }
            else if(containsVerb && !containsNoun) {
                System.out.println("Your command of \"" + command[1] + "\" is not recognized.");
            }
            else {
                System.out.println("Your command of \"" + command[0] + "\" and \"" + command[1] + "\" is not recognized.");
            }
        }
    }

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
            if (commands[0].equals("go") && availRooms.contains(commands[1])) {
                System.out.println("You are now heading to " + commands[1]);
                location = commands[1];
                return location;
            }
            else {
                System.out.println(commands[1] + " is not available to you. Your available rooms are: " + availRooms);
                break;
            }
        }
        return location;
    }
    public void look(Location name, String[] commands) {
        // Better way to use current location using current instance of the map. Now can use currentLocation with the Location Class methods!
//        Location currentLocation = map.grabJSONLocation(curLoc.getName());
        System.out.println(name.getDescription());
        List<String> items = name.getItems();
        List<String> furniture = name.getFurniture();

        while (true) {
            if (items == null && furniture == null) {
                System.out.println("There is nothing there.");
                break;
            }
            else if (items == null && commands[0].equals("look") && furniture.stream().anyMatch(commands[1]::equalsIgnoreCase)) {
                System.out.println("You are looking at " + commands[1]);
                JsonArray arr = obj.getAsJsonArray("description");
                System.out.println(arr);
                break;

            }
            else if (furniture == null && commands[0].equals("look") && items.stream().anyMatch(commands[1]::equalsIgnoreCase)) {

                System.out.println("You are looking at " + commands[1]);
                System.out.println();
                break;

            }
            else if (items !=null && furniture != null && commands[0].equals("look") && items.stream().anyMatch(commands[1]::equalsIgnoreCase) && furniture.stream().anyMatch(commands[1]::equalsIgnoreCase)) {
                System.out.println("You are looking at " + commands[1]);
                JsonArray arr = obj.getAsJsonArray("description");
                System.out.println(arr);
                break;

            }
            else {
                System.out.println(commands[1] + " is not available to you. The current available items: " + items);
                break;
            }
        }
    }
}