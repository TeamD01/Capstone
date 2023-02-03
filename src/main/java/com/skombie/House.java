package com.skombie;

import com.skombie.utilities.Console;
import com.skombie.utilities.InteractionParser;
import com.skombie.utilities.JSONMapper;
import com.skombie.utilities.Printer;

import java.io.*;
import java.util.*;

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
    private static final String SAVE = "data/";
    private static final String LOAD = "data/";

    public House() {
        JSONMapper map = new JSONMapper();
        rooms = map.mapGameJSONtoObjects();
        player = new Player();
        currLocation = findLocationByName("Living Room");
        parser = new InteractionParser();
    }

    public void changeCharacterRoom() {
        HashMap<Character, String> characters = new HashMap<>();
        ArrayList<Location> roomList = new ArrayList<>();
        ArrayList<Character> characterList = new ArrayList<>();
        for (Location roomOfCharacter : rooms) {
            // add all game Locations to roomList
            roomList.add(roomOfCharacter);
            if (roomOfCharacter.getCharacters() != null) {
                //loop through all characters in room
                for (Character characterInRoom : roomOfCharacter.getCharacters()) {
                    // add to hashmap the characters in the room and all rooms
                    characters.put(characterInRoom, roomOfCharacter.getName());
                    // add character list all characters
                    characterList.add(characterInRoom);
                }
            }
        }

        //randomly select character
        Collections.shuffle(characterList);
        Character characterToMove = characterList.get(0);
        // get string name of location of character to move
        String characterToMoveLocation = characters.get(characterToMove);
        Location nameOfRemovedLocation = null;
        Location tempCurrentCharLoc = null;

        for (Location currentCharLoc : rooms) {
            if (Objects.equals(characterToMoveLocation, currentCharLoc.getName())) {
                tempCurrentCharLoc = currentCharLoc;
//                currentCharLoc.removeCharacter(characterToMove);
                nameOfRemovedLocation = currentCharLoc;
            }
        }
        if (tempCurrentCharLoc != null) {
            if (rooms.contains(tempCurrentCharLoc)) {
                rooms.remove(tempCurrentCharLoc);
                tempCurrentCharLoc.removeCharacter(characterToMove);
                rooms.add(tempCurrentCharLoc);
            }
        }
        if (nameOfRemovedLocation != null) {
            System.out.println(characterToMove + " removed from " + nameOfRemovedLocation.getName());
        }

        //put character in random room
        Location tempRoomPutCharacterOld = null;
        Location tempRoomPutCharacterNew = null;
        List<Character> charsList = new ArrayList<>();
        Collections.shuffle(roomList);
        for (Location roomToPutCharacter : rooms) {
            if (roomToPutCharacter.getName().toLowerCase(Locale.ROOT).equals(roomList.get(0).getName().toLowerCase(Locale.ROOT))) {
                tempRoomPutCharacterOld = roomToPutCharacter;

                if (tempRoomPutCharacterOld.getCharacters() != null) {
                    tempRoomPutCharacterNew = tempRoomPutCharacterOld;
                    tempRoomPutCharacterNew.setCharacter(characterToMove);


                }
                if (tempRoomPutCharacterOld.getCharacters() == null) {
                    charsList.add(characterToMove);
                    tempRoomPutCharacterNew = tempRoomPutCharacterOld;
                    tempRoomPutCharacterNew.setCharacters(charsList);
                }
            }

        }
        if (tempRoomPutCharacterOld != null && tempRoomPutCharacterNew != null && characterToMove != null && rooms.contains(tempRoomPutCharacterOld)) {
            rooms.remove(tempRoomPutCharacterOld);
            rooms.add(tempRoomPutCharacterNew);
        }
        System.out.println(characterToMove + " moving to " + roomList.get(0).getName());

    }

    public void manageCommand(String userInput) {
        if (checkForSpecialCommand(userInput)) return;

        String[] userCommands = parser.verifyInput(userInput);
        if (userCommands == null) {
            return;
        }
        String command = userCommands[0];
        String object = userCommands[1];

        if (VERBS.contains(command)) {
            switch (command) {
                case "look":
                    Inspectable item = findInspectableByName(object);
                    if (item != null) {
                        String itemDesc = player.look(item);
                        System.out.println(itemDesc);
                    } else {
                        System.out.println("Not a valid item");
                    }
                    Console.pause(2000);
                    break;

                case "go":
                    Location location = findAvailableLocationInCurrLocation(object);
                    if (location != null) {
                        goLocation(location);
                    } else {
                        System.out.println("Not a valid location.");
                        Console.pause(2000);
                    }
                    break;

                case "get":
                    InventoryItem found = findInventoryItemByName(object);
                    if (found != null) {
                        if (found instanceof Weapon) {
                            Weapon previousWeapon = player.getCurrentWeapon();
                            if (previousWeapon != null) currLocation.addWeaponToRoom(previousWeapon);

                            player.setCurrentWeapon((Weapon) found);
                            System.out.printf("\n%s set to current weapon\n", found.getName().toUpperCase());
                            currLocation.removeWeaponFromRoom((Weapon) found);
                        } else if (found instanceof Item) {
                            player.get(found);
                            System.out.printf("\n%s added to inventory\n", found.getName().toUpperCase());
                            currLocation.removeItemFromRoom((Item) found);
                        }
                    } else {
                        System.out.println("Not a valid item in this location");
                    }
                    Console.pause(2000);
                    break;

                case "drop":
                    InventoryItem inInventory = findItemInUserInventory(object);
                    if (inInventory != null) {
                        if (inInventory instanceof Item) {
                            player.drop(inInventory);
                            currLocation.addItemToRoom((Item) inInventory);
                        }
                    } else {
                        System.out.printf("You don't have %s in your inventory", object);
                        Console.pause(2000);
                    }
                    break;

                case "talk":
                    Character friend = findNPCByName(object);
                    if (friend != null) {
                        String[] dialogue = player.talk(friend);
                        Arrays.stream(dialogue).forEach(System.out::println);
                    } else {
                        System.out.printf("%s is not in this room.", friend);
                    }
                    Console.pause(2500);
                    break;
            }
        } else {
            System.out.printf("%s not a valid command\n", command);
        }
    }


    /**
     * Used to find location in the entire house based on the name passed in.
     * If exists will return location object else null
     */
    public Location findLocationByName(String location) {
        return rooms.stream().filter(x -> x.getName().equalsIgnoreCase(location)).findFirst().orElse(null);
    }

    /**
     * Helper method to print information for the current location the user is in.
     */
    public void printCurrLocationData() {
        Console.pause(150);
        Console.clear();

        System.out.println("=======================");

        System.out.printf("Location: %s\n", currLocation.getName());
        System.out.printf("%s\n", currLocation.getDescription());
        if (currLocation.getFurniture() != null && currLocation.getFurniture().size() != 0) {
            System.out.println("\nFurniture:");
            currLocation.getFurniture().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }
        if (currLocation.getCharacters() != null && currLocation.getCharacters().size() != 0) {
            System.out.println("\nPeople:");
            currLocation.getCharacters().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }
        if (currLocation.getItems() != null && currLocation.getItems().size() != 0) {
            System.out.println("\nItems:");
            currLocation.getItems().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }
        if (currLocation.getWeapons() != null && currLocation.getWeapons().size() != 0) {
            System.out.println("\nWeapons:");
            currLocation.getWeapons().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }

        System.out.println("\nAvailable Locations:");
        currLocation.getAvailableRooms().forEach(x -> System.out.printf("> %s\n", x));

        System.out.printf("\nHealth: %s", player.getHealth());
        if (player.getCurrentWeapon() != null) {
            System.out.printf("\tWeapon: %s", player.getCurrentWeapon().getName());
        }
        if (player.getInventory().size() > 0) {
            System.out.print("\tInventory:");
            player.getInventory().forEach(x -> System.out.printf(" %s ", x.getName()));
        }
        System.out.println("\n=======================");
    }

    private void goLocation(Location location) {
        currLocation = location;
        checkForSkombie();
    }

    private Character findNPCByName(String character) {
        return currLocation.getCharacters().stream().filter(x -> x.getName().equalsIgnoreCase(character)).findFirst().orElse(null);
    }

    private InventoryItem findItemInUserInventory(String item) {
        return getPlayerInventoryItems().stream().filter(x -> x.getName().equalsIgnoreCase(item)).findFirst().orElse(null);
    }

    private void saveGame(){
        ArrayList<Object>saveGameData = new ArrayList<>();
        if (currLocation != null) {
            saveGameData.add(currLocation);
        }
        if (player != null) {
            saveGameData.add(player);
        }
        if (rooms != null) {
            saveGameData.add(rooms);
        }

        try {
            File myObj = new File("src/main/resources/data/save.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created!");
            } else {
                System.out.println("File exists!");
            }
            FileOutputStream fileOut = new FileOutputStream("src/main/resources/data/save.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(saveGameData);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Your progress is being saved.........");
        System.out.println("Gamed Saved");
    }

    public void loadGame() {
        try {
            FileInputStream fileIn = new FileInputStream(LOAD + "filename.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object loadLocation = in.readObject();
            currLocation = (Location) loadLocation;
            in.close();
            fileIn.close();
            System.out.println(".......loading....");
            System.out.println("Game Loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    private Item findItemByName(String item){
//        return currLocation.getItems().stream().filter(x -> x.getName().equalsIgnoreCase(item)).findFirst().orElse(null);
//    }

    /**
     * Inspectable interface is used because Item, Weapon, Character, other objects can use the "look" command
     * this allows us to build similar functionality for diff types of objs
     */
    private Inspectable findInspectableByName(String item) {
        return getInspectableItems().stream().filter(x -> x.getName().equalsIgnoreCase(item)).findFirst().orElse(null);
    }

    private List<Inspectable> getInspectableItems() {
        List<Inspectable> inspectables = new ArrayList<>();
        if (currLocation.getItems() != null) {
            inspectables.addAll(currLocation.getItems());
        }

        if (currLocation.getFurniture() != null) {
            inspectables.addAll(currLocation.getFurniture());
        }

        if (currLocation.getWeapons() != null) {
            inspectables.addAll(currLocation.getWeapons());
        }

        if (currLocation.getCharacters() != null) {
            inspectables.addAll(currLocation.getCharacters());
        }

        return inspectables;
    }

    private List<InventoryItem> getInventoryItems() {
        List<InventoryItem> validItems = new ArrayList<>();
        if (currLocation.getItems() != null) {
            validItems.addAll(currLocation.getItems());
        }
        if (currLocation.getWeapons() != null) {
            validItems.addAll(currLocation.getWeapons());
        }
        return validItems;
    }

    private List<InventoryItem> getPlayerInventoryItems() {
        List<InventoryItem> validItems = new ArrayList<>();
        if (player.getInventory() != null) {
            validItems.addAll(player.getInventory());
        }
        if (player.getCurrentWeapon() != null) {
            validItems.add(player.getCurrentWeapon());
        }
        return validItems;
    }


    private void checkForSkombie() {
        if (findLocationByName(currLocation.getName()).isHasSkunk()) {
            System.out.println("There is a skunk, get it, get it!!!");
        }
    }

    private boolean checkForSpecialCommand(String input) {
        boolean isSpecial = false;

        if ("QUIT".equalsIgnoreCase(input)) {
            Console.clear();
            isSpecial = true;
            Printer.printFile(QUIT);
            System.exit(0);
        } else if ("HELP".equalsIgnoreCase(input)) {
            Console.clear();
            isSpecial = true;
            Printer.printFile(HELP);
            Console.pause(3000);
        } else if ("INVENTORY".equalsIgnoreCase(input)) {
            Console.clear();
            isSpecial = true;
            printInventory();
            Console.pause(3000);
        }
        else if ("SAVE".equalsIgnoreCase(input)) {
            Console.clear();
            isSpecial = true;
            saveGame();
            Console.pause(3000);
        }

        return isSpecial;
    }

    ;

    /**
     * InventoryItem is an interface that collects both Item <Class/> & Weapon<Class/>
     * because functionality is very similar with these objects
     */
    private InventoryItem findInventoryItemByName(String item) {
        return getInventoryItems().stream().filter(x -> x.getName().equalsIgnoreCase(item)).findFirst().orElse(null);
    }

    /**
     * Based on user input will find if location input is an available location in the current room
     */
    private Location findAvailableLocationInCurrLocation(String location) {
        String valid = currLocation.getAvailableRooms().stream().filter(x -> x.equalsIgnoreCase(location)).findFirst().orElse(null);

        return findLocationByName(valid);
    }

    private void printInventory() {
        if (player.getInventory() == null) {
            System.out.println("Your inventory is empty.");
        } else {
            player.getInventory().forEach(x -> {
                System.out.printf("%s - %s\n", x.getName().toUpperCase(), x.getDescription().toUpperCase());
            });
        }
    }
}