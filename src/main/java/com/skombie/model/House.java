package com.skombie.model;

import com.skombie.utilities.*;
import com.skombie.utilities.Console;
import com.skombie.utilities.Reader;


import java.io.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import java.util.*;

/*
 * HOUSE CLASS WILL BE USED TO MANAGE ROOMS (POSSIBLE OPTIONS FOR PLAYERS)
 * */

public class House {
    //TODO : we need to add "secure" as a command?
    private static final String QUIT = "images/quit.txt";
    private static final String HELP = "data/intro";
    private static final String SAVE = "data/savefile.txt";
    private Player player;
    private List<Location> rooms;
    private final InteractionParser parser;
    private Location currLocation;
    private static final String LOAD = "data/";
    private String currentMap = "images/map/livingroom.txt";
    private final PromptHelper prompter;
    private List<Character> characterStatusList;
    // Sets minimum number turns AFTER combat starts for character when turn into zombie. Must be higher than or equal to number turns after combat player dies.
    private static final int TURNALIVE = 10;
    // Sets number of combat turns for character until they die.
    private static final int CHARACTERDIES = 5;
    private int skombieCounter = 0;
    // sets attack value of skombies and zombies
    private static final double ATTACK_HEALTH = 10.0;
    public boolean progressedPastHelp = false;

    public House() {
        JSONMapper map = new JSONMapper();
        rooms = map.mapGameJSONtoObjects();
        player = new Player();
        currLocation = findLocationByName("Living Room");
        parser = new InteractionParser();
        Scanner scanner = new Scanner(System.in);
        prompter = new PromptHelper(scanner);
    }

    // Randomly called in the SkombieApp for making characters change rooms.
    public void changeCharacterRoom() {
        HashMap<Character, String> characters = new HashMap<>();
        ArrayList<Location> roomList = new ArrayList<>();
        ArrayList<Character> characterList = new ArrayList<>();
        for (Location roomOfCharacter : rooms) {
            // add all game Locations to roomList
            if (!roomOfCharacter.getName().toLowerCase(Locale.ROOT).equals("backyard")) {
                roomList.add(roomOfCharacter);
                if (roomOfCharacter.getCharacters() != null) {
                    //loop through all characters in each room
                    for (Character characterInRoom : roomOfCharacter.getCharacters()) {
                        // add to hashmap the characters in the room and all rooms
                        characters.put(characterInRoom, roomOfCharacter.getName());
                        // add character list all characters
                        characterList.add(characterInRoom);
                    }
                }
            }
        }
        //randomly select character
        Collections.shuffle(characterList);
        Character characterToMove = characterList.get(0);
        //If the character is not in combat, or they are dead, they can move
        if (characterToMove.isAvailableMove()) {
            // get string name of location of character to move
            String characterToMoveLocation = characters.get(characterToMove);
            Location tempCurrentCharLoc = null;

            for (Location currentCharLoc : rooms) {
                if (Objects.equals(characterToMoveLocation, currentCharLoc.getName())) {
                    tempCurrentCharLoc = currentCharLoc;
                }
            }
            //remove room from list, remove char from room, add back room without character
            if (tempCurrentCharLoc != null) {
                if (rooms.contains(tempCurrentCharLoc)) {
                    rooms.remove(tempCurrentCharLoc);
                    tempCurrentCharLoc.removeCharacter(characterToMove);
                    rooms.add(tempCurrentCharLoc);
                }
            }
            //put character in random room
            Location tempRoomPutCharacterOld = null;
            Location tempRoomPutCharacterNew = null;
            List<Character> charsList = new ArrayList<>();
            Collections.shuffle(roomList);
            // find random match for room to put character in
            for (Location roomToPutCharacter : rooms) {
                if (roomToPutCharacter.getName().toLowerCase(Locale.ROOT).equals(roomList.get(0).getName().toLowerCase(Locale.ROOT))) {
                    tempRoomPutCharacterOld = roomToPutCharacter;

                    // if existing room to put character has other characters
                    if (tempRoomPutCharacterOld.getCharacters() != null) {
                        tempRoomPutCharacterNew = tempRoomPutCharacterOld;
                        tempRoomPutCharacterNew.setCharacter(characterToMove);
                    }
                    // if existing room to put character in is empty of other characters
                    if (tempRoomPutCharacterOld.getCharacters() == null) {
                        charsList.add(characterToMove);
                        tempRoomPutCharacterNew = tempRoomPutCharacterOld;
                        tempRoomPutCharacterNew.setCharacters(charsList);
                    }
                }
            }
            // remove the old version of the room to put character in and add the new version
            if (tempRoomPutCharacterOld != null && characterToMove != null && rooms.contains(tempRoomPutCharacterOld) && characterToMove.isAvailableMove()) {
                rooms.remove(tempRoomPutCharacterOld);
                rooms.add(tempRoomPutCharacterNew);
                // movement logic for if room has a skunk or if the character is a zombie
                if (tempRoomPutCharacterNew.isHasSkunk() && !characterToMove.isDead()) {
                    System.out.println("You hear what sounds like fighting. You should see if anyone needs help!");
                    characterToMove.setAvailableMove(false);
                }

                //combat here
                if (characterToMove.isDead() && tempRoomPutCharacterNew.equals(currLocation)) {
                    System.out.println("Zombie " + characterToMove.getName() + " has burst into the room... Fight or Run for your life");
                    characterToMove.setAvailableMove(false);
                    if (randGen() == 1) {
                        double health = player.getHealth();
                        player.setHealth(health - ATTACK_HEALTH);
                        System.out.println("Zombie " + characterToMove.getName() + " has attacked you.");
                    }
                }
                if (characterToMove.isDead()) {
                    System.out.println("You hear strange groaning that kinda sounds like " + characterToMove.getName());
                }
            }
            //shows character movement
//            if (characterToMove != null) {
//                System.out.println(characterToMove.getName() + " moving to " + roomList.get(0).getName());
//            }
        }
    }

    private void saveGame() {
        ArrayList<Object> saveGameData = new ArrayList<>();
        if (currLocation != null) {
            saveGameData.add(currLocation);
        }
        if (player != null) {
            saveGameData.add(player);
        }
        saveGameData.add(skombieCounter);
        for (Location room : rooms) {
            if (room != null) {
                saveGameData.add(room);
            }
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
            FileInputStream fileIn = new FileInputStream("src/main/resources/data/save.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            List<Object> saveGameData = (List<Object>) in.readObject();
            this.currLocation = (Location) saveGameData.get(0);
            this.currentMap = String.format("images/map/%s.txt", currLocation.getName().replaceAll("\\s+", "").toLowerCase());
            this.player = (Player) saveGameData.get(1);
            this.rooms = new ArrayList<>();
            this.skombieCounter = (int) saveGameData.get(2);
            for (int i = 3; i < saveGameData.size(); i++) {
                Location loc = (Location) saveGameData.get(i);
                if (loc != null) {
                    rooms.add(loc);
                }
            }

            in.close();
            fileIn.close();
            System.out.println(".......loading....");
            System.out.println("Game Loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void manageCommand(String userInput) {
        if (checkForSpecialCommand(userInput)) return;

        String[] userCommands = parser.verifyInput(userInput);
        if (userCommands == null) {
            checkForZombie();
            checkForSkombie();
            return;
        }
        String command = userCommands[0];
        String object = userCommands[1];


        switch (command) {
            case "look":
            case "examine":
            case "see":
            case "scan":
            case "view":
            case "inspect":
                Inspectable item = findInspectableByName(object);
                if (item != null && item.getClass() != Character.class) {
                    String itemDesc = player.look(item);
                    System.out.println(itemDesc);
                } else if (item != null) {
                    Character deadChar = (Character) item;
                    if (deadChar.isDead()) {
                        System.out.println(deadChar.getName() + " is dead...");
                    } else {
                        String itemDesc = player.look(item);
                        System.out.println(itemDesc);
                    }
                } else {
                    System.out.println("Not a valid item");
                }
                checkForZombie();
                checkForSkombie();
                Console.pause(20);
                break;

            case "go":
            case "move":
            case "proceed":
            case "advance":
            case "walk":
            case "run":
            case "travel":
                Location location = findAvailableLocationInCurrLocation(object);
                if (location != null) {
                    goLocation(location);
                } else {
                    System.out.println("Not a valid location.");
                    Console.pause(20);
                }
                break;

            case "get":
            case "take":
            case "pick":
            case "grab":
            case "acquire":
            case "hold":
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
                checkForZombie();
                checkForSkombie();
                Console.pause(20);
                break;

            case "drop":
            case "leave":
            case "down":
            case "release":
                InventoryItem inInventory = findItemInUserInventory(object);
                if (inInventory != null) {
                    if (inInventory instanceof Item) {
                        player.drop(inInventory);
                        currLocation.addItemToRoom((Item) inInventory);
                    }
                } else {
                    System.out.printf("You don't have %s in your inventory", object);
                    Console.pause(20);
                }
                break;

            case "talk":
            case "chat":
            case "speak":
                Character friend = findNPCByName(object);
                if (friend != null && !friend.isDead()) {
                    String[] dialogue = player.talk(friend);
                    Arrays.stream(dialogue).forEach(System.out::println);
                } else if (friend != null && friend.isDead()) {
                    System.out.println("Argghhhaaaarrrgggghhhhh");
                } else {
                    System.out.printf("%s is not in this room.", object);
                }
                checkForZombie();
                checkForSkombie();
                Console.pause(25);
                break;
//            case "attack":
//            case "hit":
//                if ( object != null && object.equals("skombie")) {
//                    int skombieHealth = (currLocation.getTimesCanAttackZombie() - player.getAttackValue());
//                    if (skombieHealth <= 0 && currLocation != null) {
//                        currLocation.setHasSkunk(false);
//                        System.out.println("You have vanquished the skombie!!!");
//                    }
//                    else {
//                        System.out.println("You attack the skombie");
//                    }
//                }
//                checkForZombie();
//                checkForSkombie();
//                break;
            default:
                checkForZombie();
                checkForSkombie();
                System.out.printf("%s Not a valid command", command);
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
    public void gatherLocationData() {
        Console.pause(150);
        Console.clear();

        List<String> locationData = new ArrayList<>();

        locationData.add("=======================");
        locationData.add(String.format("Location: %s", currLocation.getName()));
        locationData.add(String.format("%s", currLocation.getDescription()));
        if (currLocation.isHasSkunk()){
            locationData.add("\nSkombie in room:");
            System.out.println(locationData.add(String.format("> %s", "Yes!!")));
        }
        if (currLocation.getFurniture() != null && currLocation.getFurniture().size() != 0) {
            locationData.add("\nFurniture:");
            currLocation.getFurniture().forEach(x -> locationData.add(String.format("> %s", x.getName())));
        }
        if (currLocation.getCharacters() != null && currLocation.getCharacters().size() != 0) {
            locationData.add("\nPeople:");
            currLocation.getCharacters().forEach(x -> locationData.add(String.format("> %s", x.getName())));
        }
        if (currLocation.getItems() != null && currLocation.getItems().size() != 0) {
            locationData.add("\nItems:");
            currLocation.getItems().forEach(x -> locationData.add(String.format("> %s", x.getName())));
        }
        if (currLocation.getWeapons() != null && currLocation.getWeapons().size() != 0) {
            locationData.add("\nWeapons:");
            currLocation.getWeapons().forEach(x -> locationData.add(String.format("> %s", x.getName())));
        }
        locationData.add("\nAvailable Locations:");
        currLocation.getAvailableRooms().forEach(x -> locationData.add(String.format("> %s", x)));

        locationData.add("==========YOU==========");
        locationData.add(String.format("Health: %s", player.getHealth()));
        if (player.getCurrentWeapon() != null) {
            locationData.add("Current Weapon:");
            locationData.add(String.format("> %s", player.getCurrentWeapon().getName()));
        }
        if (player.getInventory().size() > 0) {
            locationData.add("Inventory:");
            player.getInventory().forEach(x -> locationData.add(String.format("> %s", x.getName())));
        }
        locationData.add("=======================");

        addMapAndPrint(locationData);
    }

    private void goLocation(Location location) {
        currentMap = String.format("images/map/%s.txt", location.getName().replaceAll("\\s+", "").toLowerCase());
        if (currLocation.getCharacters() != null) {
            for (Character deadCharNotMove : currLocation.getCharacters()) {
                if (deadCharNotMove.isDead() & deadCharNotMove.getTurnsInCombat() >= TURNALIVE) {
                    deadCharNotMove.setAvailableMove(true);

                }
            }
        }
        currLocation = location;
        checkForSkombie();
        checkForZombie();

    }

    private void addMapAndPrint(List<String> locationData) {
        List<String> mapStrings = Reader.readFileToArrayList(currentMap);
        int size = Math.max(locationData.size(), mapStrings.size());
        Set<String> locationInfo = new HashSet<>();
        int i = 0;
        int j = 0;

        while (i <= size || j <= size) {
            String item1 = i < locationData.size() ? locationData.get(i) : "";

            if (item1.contains("\n")) {
                System.out.printf("%-65s %s%n", "", j < mapStrings.size() ? mapStrings.get(j) : "");
                j++;
                System.out.printf("%-65s %s%n", locationData.get(i).replace("\n", ""), j < mapStrings.size() ? mapStrings.get(j) : "");
                i++;
                j++;
            } else {
                System.out.printf("%-65s %s%n", i < locationData.size() ? locationData.get(i) : "", j < mapStrings.size() ? mapStrings.get(j) : "");
                i++;
                j++;
            }
        }
    }

    private Character findNPCByName(String character) {
        return currLocation.getCharacters().stream().filter(x -> x.getName().equalsIgnoreCase(character)).findFirst().orElse(null);
    }

    private InventoryItem findItemInUserInventory(String item) {
        return getPlayerInventoryItems().stream().filter(x -> x.getName().equalsIgnoreCase(item)).findFirst().orElse(null);
    }

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

    //Combat here
    //sets dialog for player when going to room that has skombie depending if other alive characters or not
    private void checkForSkombie() {
        List<Character> charInCombatAlive = new ArrayList<>();
        List<Character> charInSkombieRoomDead = new ArrayList<>();
        if (currLocation.getCharacters() != null) {
            for (Character charInRoom : currLocation.getCharacters()) {
                if (!charInRoom.isDead()) {
                    charInCombatAlive.add(charInRoom);
                }
                if (charInRoom.isDead()) {
                    charInSkombieRoomDead.add(charInRoom);
                }
            }
        }
        if (findLocationByName(currLocation.getName()).isHasSkunk()) {
            if (currLocation.getCharacters() == null) {
                System.out.println("There is a skombie, get it, get it!!!");
            }
            else if (currLocation.getCharacters() != null) {
                if (charInCombatAlive.size() > 0) {
                    for (Character x : charInCombatAlive) {
                        System.out.println(x.getName() + " needs your help fighting the skombie! They are doing what they can but it is not enough!");
                    }
                }
                System.out.println("There is a skombie, get it get it!!!");

            }
            if (randGen() == 1) {
                System.out.println("Skombie attacks you!!");
                double health = player.getHealth();
                player.setHealth( health - ATTACK_HEALTH);
            }
        }

    }

    //Combat here
    private void checkForZombie() {
        if (currLocation.getCharacters() != null) {
            for (Character character : currLocation.getCharacters()) {
                if (character.isDead() && character.getTurnsInCombat() >= TURNALIVE) {
                    System.out.println(character.getName() + " is a zombie. You must attack");
                    character.setAvailableMove(false);
                    combatCycle(character);
                }
            }
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
            Scanner myObj = new Scanner(System.in);
            prompter.prompt("\n[P]roceed ?", "[pP]", "\nNot Valid");

        } else if ("INVENTORY".equalsIgnoreCase(input)) {
            Console.clear();
            isSpecial = true;
            printInventory();
            Console.pause(3000);
        } else if ("SAVE".equalsIgnoreCase(input)) {
            Console.clear();
            isSpecial = true;
            saveGame();
            Console.pause(3000);
        } else if ("SAVE".equalsIgnoreCase(input)) {
            Console.clear();
            isSpecial = true;
            saveGame();
            Console.pause(3000);
        }

        return isSpecial;
    }

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
        if (player.getInventory().isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            player.getInventory().forEach(x -> {
                System.out.printf("%s - %s\n", x.getName().toUpperCase(), x.getDescription().toUpperCase());
            });
        }
    }

    // checks all rooms to see if person is in combat and for how long. If too long, they die. If dead, after a while to turn into zombie and can move.
    public void updateCharacterStatusList() {
        for (Location room : rooms) {
            if (room.getCharacters() == null) {

            } else {
                for (Character character : room.getCharacters()) {
                    //If character cant move and is alive, it means they are in combat
                    if (!character.isAvailableMove()) {
                        character.setTurnsInCombat(1);
                    }
                    if (character.getTurnsInCombat() == CHARACTERDIES) {
                        System.out.println(character.getName() + " is now dead.");
                        character.setDead(true);
                        character.setAvailableMove(false);
                        character.setTurnsInCombat(1);
                    }
                    //turn into zombie
                    if (character.getTurnsInCombat() == TURNALIVE) {
                        character.setAvailableMove(true);
                        System.out.println(character.getName() + " is now among the walking dead.");
                        character.setTurnsInCombat(1);
                    }

                }
            }

        }
    }
    private void combatCycle(Character character) {
        if (randGen() == 1) {
            double health = player.getHealth();
            System.out.println("Zombie " + character.getName() + " attacks you!!!");
            player.setHealth(health - ATTACK_HEALTH);
        }
    }

    public Location getCurrLocation() {
        return currLocation;
    }

    public List<Location> getRooms() {
        return rooms;
    }

    public int getSkombieCounter() {
        return skombieCounter;
    }

    public void setSkombieCounter(int skombieInc) {
        this.skombieCounter += skombieInc;
    }

    private int randGen() {
        return new Random().nextInt(3);
    }

    public boolean isProgressedPastHelp() {
        return progressedPastHelp;
    }

    public void setProgressedPastHelp(boolean progressedPastHelp) {
        this.progressedPastHelp = progressedPastHelp;
    }
}