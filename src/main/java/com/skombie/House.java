package com.skombie;

import com.skombie.utilities.Console;
import com.skombie.utilities.InteractionParser;
import com.skombie.utilities.JSONMapper;
import com.skombie.utilities.Printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/*
* HOUSE CLASS WILL BE USED TO MANAGE ROOMS (POSSIBLE OPTIONS FOR PLAYERS)
* */

public class House {
    //TODO : we need to add "secure" as a command?
    private static final Set<String> VERBS = Set.of("use", "look", "go", "get", "drop", "talk");
    private static final String QUIT = "images/quit.txt";
    private static final String HELP = "data/intro";
    private final Player player;
    private final List<Location> rooms;
    private final InteractionParser parser;
    private Location currLocation;

    public House() {
        JSONMapper map = new JSONMapper();
        rooms = map.mapGameJSONtoObjects();
        player = new Player();
        currLocation = findLocationByName("Living Room");
        parser = new InteractionParser();
    }

    public void manageCommand(String userInput){
        if(checkForSpecialCommad(userInput)) return;

        String[] userCommands = parser.verifyInput(userInput);

        String command = userCommands[0];
        String object = userCommands[1];

        if(VERBS.contains(command)) {
            switch(command){
                case "look":
                    Inspectable item = findInspectableByName(object);
                    if(item != null){
                        String itemDesc = player.look(item);
                        System.out.println(itemDesc);
                    }
                    else{
                        System.out.println("Not a valid item");
                    }
                    break;

                case "go":
                    Location location = findLocationByName(object);
                    if (location != null){
                       goLocation(location);
                    }
                    else{
                        System.out.println("Not a valid location.");
                    }
                    break;

                case "get":
                    Item found = findItemByName(object);
                    if(found != null){
                        player.get(found);
                    }
                    else{
                        System.out.println("Not a valid item in this location");
                    }
                    break;

                case "drop":
                    Item inInventory = findUserItemInventory(object);
                    if(inInventory != null){
                        player.drop(inInventory);
                    }
                    else{
                        System.out.printf("You don't have %s in your inventory", inInventory);
                    }
                    break;

                case "talk":
                    Character friend = findNPCByName(object);
                    if(friend != null){
                        String[] dialogue = player.talk(friend);
                        Arrays.stream(dialogue).forEach(System.out::println);
                    }
                    else {
                        System.out.printf("%s is not in this room.", friend);
                    }
                    break;
            }
        }
        else{
            System.out.printf("%s not a valid command", command);
        }
    }

    public Location findLocationByName(String location) {
        return rooms.stream().filter(x -> x.getName().equalsIgnoreCase(location)).findFirst().orElse(null);
    }

    public void printCurrLocationData() {
        Console.pause(150);
        Console.clear();

        System.out.println("=======================");
        System.out.printf("Location: %s\n", currLocation.getName());
        System.out.printf("%s\n", currLocation.getDescription());

        if (!(currLocation.getFurniture() == null)) {
            System.out.println("\nFurniture:");
            currLocation.getFurniture().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }
        if (!(currLocation.getCharacters() == null)) {
            System.out.println("\nPeople:");
            currLocation.getCharacters().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }
        if (!(currLocation.getItems() == null)) {
            System.out.println("\nItems:");
            currLocation.getItems().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }
        if (!(currLocation.getWeapons() == null)) {
            System.out.println("\nWeapons:");
            currLocation.getWeapons().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }

        System.out.println("\nAvailable Locations:");
        currLocation.getAvailableRooms().forEach(x -> System.out.printf("> %s\n", x));

        System.out.println("=======================");
    }

    private void goLocation(Location location){
        currLocation = location;
        checkForSkombie();
    }

    private Character findNPCByName(String character){
        return currLocation.getCharacters().stream().filter(x -> x.getName().equalsIgnoreCase(character)).findFirst().orElse(null);
    }

    private Item findUserItemInventory(String item){
        return player.getInventory().stream().filter(x-> x.getName().equalsIgnoreCase(item)).findFirst().orElse(null);
    }

    private Item findItemByName(String item){
        return currLocation.getItems().stream().filter(x -> x.getName().equalsIgnoreCase(item)).findFirst().orElse(null);
    }

    private Inspectable findInspectableByName(String item){
        return getInspectableItems().stream().filter(x -> x.getName().equalsIgnoreCase(item)).findFirst().orElse(null);
    }

    private List<Inspectable> getInspectableItems(){
        List<Inspectable> inspectables = new ArrayList<>();
        if(currLocation.getItems() != null) {
            inspectables.addAll(currLocation.getItems());
        }

        if(currLocation.getFurniture() != null){
        inspectables.addAll(currLocation.getFurniture());
        }

        if(currLocation.getWeapons() != null) {
            inspectables.addAll(currLocation.getWeapons());
        }

        if(currLocation.getCharacters() != null) {
            inspectables.addAll(currLocation.getCharacters());
        }

        return inspectables;
    }

    private void checkForSkombie () {
        if (findLocationByName(currLocation.getName()).isHasSkunk()) {
            System.out.println("There is a skunk, get it, get it!!!");
        }
    }

    private boolean checkForSpecialCommad(String input){
        boolean isSpecial = false;

        if("QUIT".equalsIgnoreCase(input)){
            Console.clear();
            isSpecial = true;
            Printer.printFile(QUIT);
            System.exit(0);
        }
        else if("HELP".equalsIgnoreCase(input)){
            Console.clear();
            isSpecial = true;
            Printer.printFile(HELP);
            Console.pause(3000);
        }
        else if("INVENTORY".equalsIgnoreCase(input)){
            Console.clear();
            isSpecial = true;
            printInventory();
            Console.pause(3000);
        }
        return isSpecial;
    };

    private void printInventory(){
        if(player.getInventory() == null){
            System.out.println("Your inventory is empty.");
        }
        else {
            player.getInventory().forEach(x -> {
                System.out.printf("%s - %s\n", x.getName().toUpperCase(), x.getDescription().toUpperCase());
            });
        }
    }

}