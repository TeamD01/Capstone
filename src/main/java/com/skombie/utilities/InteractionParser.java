package com.skombie.utilities;
import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skombie.Location;

public class InteractionParser {
    Scanner sc = new Scanner(System.in);
    PromptHelper prompt = new PromptHelper(sc);
    private static final String[] VERBS = new String[] {"use", "look", "go", "get", "drop"};
    private static final String[] NOUNS = new String[] {"kitchen","bedroom", "basement", "attic", "backyard", "hallway", "office", "living room"};

    public InteractionParser() {
    }

    // This validates the pattern of the user input and checks to see if they type help or quit and returns array
    // of commands. If help is requested then a help message is displayed. Quit allows the user to exit the game.
    private String[] verifyInput() {
        String[] strSplit;

        System.out.println("Please enter a verb noun command or type \"Help\" to see a list of commands");
        while (true) {
            String temp = sc.nextLine().toLowerCase(Locale.ROOT);
            if (temp.matches("([a-zA-Z  ]*)") && temp.split(" ", 2).length == 2 || temp.split(" ", 2).length == 3) {
                strSplit = temp.split(" ",2);
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
    private String[] verifyCommand() {
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
            else if(containsVerb == true && containsNoun == false) {
                System.out.println("Your command of \"" + command[1] + "\" is not recognized.");
            }
            else {
                System.out.println("Your command of \"" + command[0] + "\" and \"" + command[1] + "\" is not recognized.");
            }
        }
    }

    // This looks at the current location, sees list of available locations and checks
    // if user is able to proceed to next location.
    public String goRoom(Location curLoc) {
        String newLocation = null;
        List<String> availRooms = curLoc.getAvailableRooms();
        for (int i = 0; i < availRooms.size(); i++) {
            String newValue = availRooms.get(i).toLowerCase(Locale.ROOT);
            availRooms.set(i, newValue);
        }
        while (true) {
            String[] commands = verifyCommand();
            if (commands[0].equals("go") && availRooms.contains(commands[1])) {
                System.out.println("You are now heading to " + commands[1]);
                newLocation = commands[1];
                return newLocation;
            }
            else {
                System.out.println(commands[1] + " is not available to you. Your available rooms are: " + availRooms);
            }
        }
    }
}