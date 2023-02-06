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

import static com.skombie.utilities.Printer.printFile;
/*
 * HOUSE CLASS WILL BE USED TO MANAGE ROOMS (POSSIBLE OPTIONS FOR PLAYERS)
 * */

public class House {
    private static final String QUIT = "images/quit.txt";
    private static final String HELP = "data/intro";
    private static final String SAVE = "data/savefile.txt";
    private Player player;
    private List<Location> rooms;
    private final InteractionParser parser;
    private Location currLocation;
    private String currentMap = "images/map/livingroom.txt";
    private final PromptHelper prompter;
    private boolean firstInteraction = true;
    private List<Character> characterStatusList;
    // Sets minimum number turns AFTER combat starts for character when turn into zombie. Must be higher than or equal to number turns after combat player dies.
    private static final int TURNALIVE = 10;
    // Sets number of combat turns for character until they die.
    private static final int CHARACTERDIES = 5;
    private int skombieCounter = 0;
    // sets attack value of skombies and zombies
    private static final double ATTACK_HEALTH = 10.0;
    public boolean progressedPastHelp = false;
    private boolean isSecure = false;
    private boolean isEvacuating = false;

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
    public List<String> changeCharacterRoom() {
        List<String> messages = new ArrayList<>();
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
            if (tempRoomPutCharacterOld != null && rooms.contains(tempRoomPutCharacterOld) && characterToMove.isAvailableMove()) {
                rooms.remove(tempRoomPutCharacterOld);
                rooms.add(tempRoomPutCharacterNew);
                // movement logic for if room has a skunk or if the character is a zombie
                if (tempRoomPutCharacterNew.isHasSkunk() && !characterToMove.isDead()) {
                    messages.add("You hear what sounds like fighting. You should see if anyone needs help!");
                    characterToMove.setAvailableMove(false);
                }

                //combat here
                if (characterToMove.isDead() && tempRoomPutCharacterNew.equals(currLocation)) {
                    messages.add("Zombie " + characterToMove.getName() + " has burst into the room... Fight or Run for your life\n");
                    characterToMove.setAvailableMove(false);
                    if (randGen()) {
                        double health = player.getHealth();
                        player.setHealth(health - ATTACK_HEALTH);
                        messages.add("Zombie " + characterToMove.getName() + " has attacked you.\n");
                    }
                }
                if (characterToMove.isDead()) {
                    messages.add("You hear strange groaning that kinda sounds like " + characterToMove.getName() + "\n");
                }
            }
        }
        return messages;
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
            File myObj = new File(Objects.requireNonNull(House.class.getClassLoader().getResource("data/save.txt")).getFile());
            if (myObj.createNewFile()) {
                System.out.println("File Created!");
            } else {
                System.out.println("File Created!");
            }
            FileOutputStream fileOut = new FileOutputStream(Objects.requireNonNull(House.class.getClassLoader().getResource("data/save.txt")).getFile());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(saveGameData);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nYour progress is being saved.........");
        System.out.println("Gamed Saved");
    }

    public void loadGame() {
        try {
            FileInputStream fileIn = new FileInputStream(Objects.requireNonNull(House.class.getClassLoader().getResource("data/save.txt")).getFile());
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
            System.out.println("\n.......Loading.......");
            System.out.println("Game Loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void manageCommand(String userInput) {
        List<String> messages = new ArrayList<>();
        if (checkForSpecialCommand(userInput)) {
            gatherLocationData();
            return;
        }

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
                    messages.add(itemDesc);
                } else if (item != null) {
                    Character deadChar = (Character) item;
                    if (deadChar.isDead()) {
                        messages.add(deadChar.getName() + " is dead...");
                    } else {
                        String itemDesc = player.look(item);
                        messages.add(itemDesc);
                    }
                }
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
                    messages.add(currLocation.getDescription());
                } else {
                    messages.add(String.format("%s Not a valid location.", object.toUpperCase()));
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
                    if(found.getName().equalsIgnoreCase("gas mask")){
                        player.setHasGasMask(true);
                    }
                    if (found instanceof Weapon) {
                        Weapon previousWeapon = player.getCurrentWeapon();
                        if (previousWeapon != null) currLocation.addWeaponToRoom(previousWeapon);
                        player.setCurrentWeapon((Weapon) found);
                        messages.add(String.format("%s set to current weapon", found.getName().toUpperCase()));
                        currLocation.removeWeaponFromRoom((Weapon) found);
                    } else if (found instanceof Item) {
                        if(found.getName().equalsIgnoreCase("radio")){
                            messages.add("We don't want to lug this radio around with us. USE <Radio>");
                        }
                        else {
                            player.get(found);
                            messages.add(String.format("%s added to inventory", found.getName().toUpperCase()));
                            currLocation.removeItemFromRoom((Item) found);
                        }
                    }
                } else {
                    messages.add(String.format("%s Not a valid item in this location", object.toUpperCase()));
                }
                break;

            case "drop":
            case "leave":
            case "down":
            case "release":
                InventoryItem inInventory = findItemInUserInventory(object);
                if (inInventory != null) {
                    if (inInventory instanceof Item) {
                        if(inInventory.getName().equalsIgnoreCase("gas mask")){
                            messages.add(String.format("Bold move dropping your %s", inInventory.getName()));
                            player.setHasGasMask(false);
                        }
                        player.drop(inInventory);
                        currLocation.addItemToRoom((Item) inInventory);
                        messages.add(String.format("Dropped %s from inventory.", inInventory.getName()));
                    }
                } else {
                    messages.add(String.format("You don't have %s in your inventory", object));
                }
                break;

            case "talk":
            case "chat":
            case "speak":
                Character friend = findNPCByName(object);
                if(friend != null && friend.getName().equalsIgnoreCase("sugey") && firstInteraction){
                    firstInteraction = false;
                    messages.add("YOU: Hey Honey, Do you know the code to Grandpa Nick's old military trunk?");
                    messages.add("Sugey: Yea, it's one of the kids birthdays");
                    messages.add("** I can't seem to remember their birthdays, we've got to have their birth certificates somewhere.**");
                }
                else if (friend != null && !friend.isDead()) {
                    String[] dialogue = player.talk(friend);
                    messages.addAll(Arrays.asList(dialogue));
                }
                else if (friend != null && friend.isDead()) {
                    messages.add("Argghhhaaaarrrgggghhhhh");
                }
                else {
                    messages.add(String.format("%s is not in this room.", object.toUpperCase()));
                }
                break;

            case "open":
                Furniture furniture = findFurnitureInLocation(object);
                if (furniture != null) {
                    if (furniture.isLocked()) {
                        messages.add(String.format("The %s seems to be locked. We need to find a way to open it.", furniture.getName().toUpperCase()));
                        String userAttempt = prompter.prompt("Combination Code: ", "\\d{1,4}", "\nNot valid input code MUST be 4 digits\n");
                        boolean isCorrect = promptUserForCode(furniture, userAttempt);

                        if(!isCorrect){
                            messages.add("That's not it. Keep looking around. It has to be in here");
                        }
                    } else {
                        inspectFurniture(furniture, "");
                    }
                } else {
                    messages.add(String.format("%s is not in this room.", object.toUpperCase()));
                }

                break;
            case "secure":
                if(inInventory("wood") && inInventory("hammer") && inInventory("nails") && currLocation.getUnsecurePoints() > 0){
                    messages.add(String.format("%s all secure!", currLocation.getName()));
                    currLocation.setUnsecurePoints(0);
                    currLocation.setSecure(true);
                    messages.addAll(checkIfHouseSecure());
                }
                else if(currLocation.getUnsecurePoints() == 0){
                    messages.add(String.format("%s is already secure", currLocation.getName()));
                }
                else{
                    messages.add("You will need the following equipment to secure this room : WOOD, HAMMER, NAILS");
                }
                break;

            case "attack":
            case "hit":
                Character familyMember = findNPCByName(object);
                if(familyMember != null && !familyMember.isDead()){
                    messages.add(String.format("I would never hurt %s",familyMember.getName()));
                }
                else if(familyMember != null && familyMember.isDead()){
                    messages.add(String.format("You have attacked %s", familyMember.getName()));
                    double attackValue = (familyMember.getHitsCanTake() - player.getAttackValue());
                    familyMember.setHitsCanTake(attackValue);

                    if (attackValue <= 0) {
                        messages.add(String.format("** You have killed %s **", familyMember.getName()));
                        currLocation.removeNPCFromRoom(familyMember);
                    } else {
                        messages.add(String.format("** You attacked the %s. Keep attacking it! **",familyMember.getName()));
                    }
                }

                if (object != null && object.equalsIgnoreCase("skombie") && currLocation.isHasSkunk()) {
                    double attackValue = (currLocation.getTimesLeftToKillSkombie() - player.getAttackValue());
                    currLocation.setTimesLeftToKillSkombie(attackValue);
                    if (attackValue <= 0) {
                        currLocation.setHasSkunk(false);
                        messages.add("** You have killed the skombie **");
                    } else {
                        messages.add("** You attacked the skombie. Keep attacking it! **");
                    }
                }
                else if (object.equalsIgnoreCase("skombie") && !currLocation.isHasSkunk()) {
                    messages.add("Relax there is no skombie here.. YET..");
                }
                break;

            case "use":
                InventoryItem foundItem = findInventoryItemByName(object);
                if(foundItem != null && object.equalsIgnoreCase("radio")){
                    if(!isSecure){
                        messages.add("YOU: MAYDAY, MAYDAY We need to request evacuation at 2 Jefferson Ave.");
                        messages.add("GENERAL JOE LEE: We hear you loud and clear, we won't be able to get to you for another 30 minutes or so.");
                        messages.add("You need to secure your house, keep your family safe!");
                    }
                    else{
                        messages.add("YOU: Our house is secure, what is your status?");
                        messages.add("GENERAL JOE LEE: We have a helicopter heading your way now. Get to the highest point in your house");
                        isEvacuating = true;
                    }
                }
                else{
                    messages.add(String.format("%s not in room.", object.toUpperCase()));
                }
                break;
            default:
                messages.add(String.format("%s Not a valid command", command.toUpperCase()));
        }
        messages.addAll(checkForSkombie());
        messages.addAll(checkForZombie());
        gatherLocationData(messages);
    }

    public List<String> checkIfHouseSecure(){
        List<String> message = new ArrayList<>();
        boolean allSecure = rooms.stream().allMatch(Location::isSecure);

        if(allSecure){
            isSecure = true;
            message.add("The house is now secure");
            message.add("Call for evacuation. Get out now!!");
        }
        else{
            message.add("You still have a few rooms to go");
        }
        return message;
    }

    public boolean inInventory(String name){
        return player.getInventory().stream().anyMatch(i -> name.equalsIgnoreCase(i.getName()));
    }

    public boolean promptUserForCode(Furniture furniture, String userInput){
        boolean isCodeCorrect = false;
        List<Integer> unlockCode = furniture.getUnlockCode();
        List<Integer> userAttempt = new ArrayList<>();
        char[] input = userInput.toCharArray();

        for (char codeDigit : input) {
            userAttempt.add(java.lang.Character.getNumericValue(codeDigit));
        }

        Collections.sort(unlockCode);
        Collections.sort(userAttempt);

        if(userAttempt.equals(unlockCode)){
            inspectFurniture(furniture, "");
            isCodeCorrect = true;
        }
       return isCodeCorrect;
    }

    /**
     * Used to find location in the entire house based on the name passed in.
     * If exists will return location object else null
     */
    public Location findLocationByName(String location) {
        return rooms.stream().filter(x -> x.getName().equalsIgnoreCase(location)).findFirst().orElse(null);
    }

    public void gatherLocationData() {
        gatherLocationData(new ArrayList<>());
    }

    /**
     * Helper method to print information for the current location the user is in.
     */
    public void gatherLocationData(List<String> previousInfo) {
        Console.pause(150);
        Console.clear();

        List<String> locationData = new ArrayList<>();

        locationData.add("=======================");
        locationData.add(String.format("Unsecure Points: %s", currLocation.getUnsecurePoints()));

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
        if (!previousInfo.isEmpty()) {
            locationData.add("\n========OUTPUT=========");
            locationData.addAll(previousInfo);
            locationData.add("=======================");
        } else {
            locationData.add("=======================");
        }
        addMapAndPrint(locationData);
    }

    private void goLocation(Location location) {
        if(location.getName().equalsIgnoreCase("backyard") && !player.hasGasMask()){
            player.setDead(true);
            player.setReasonForDeath("Died due to the skombie spray.");
            return;
        }
        currentMap = String.format("images/map/%s.txt", location.getName().replaceAll("\\s+", "").toLowerCase());
        if (currLocation.getCharacters() != null) {
            for (Character deadCharNotMove : currLocation.getCharacters()) {
                if (deadCharNotMove.isDead() & deadCharNotMove.getTurnsInCombat() >= TURNALIVE) {
                    deadCharNotMove.setAvailableMove(true);

                }
            }
        }
        if(location.getName().equalsIgnoreCase("attic") && isEvacuating){
            Music.playSound(this.getClass().getClassLoader().getResourceAsStream("music/helicopter.wav"));
            Console.pause(1500);
            gameComplete();
        }
        currLocation = location;
    }

    private void addMapAndPrint(List<String> locationData) {
        List<String> mapStrings = Reader.readFileToArrayList(currentMap);
        int size = Math.max(locationData.size(), mapStrings.size());
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

    private Furniture findFurnitureInLocation(String furniture) {
        return currLocation.getFurniture().stream().filter(x -> x.getName().equalsIgnoreCase(furniture)).findFirst().orElse(null);
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
    private List<String> checkForSkombie() {
        List<String> userMessages = new ArrayList<>();
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
                Music.playSound(this.getClass().getClassLoader().getResourceAsStream("music/zombie.wav"));
                userMessages.add("There is a skombie, get it!");
            } else if (currLocation.getCharacters() != null) {
                if (charInCombatAlive.size() > 0) {
                    for (Character x : charInCombatAlive) {
                        Music.playSound(this.getClass().getClassLoader().getResourceAsStream("music/bloodSplash.wav"));
                        userMessages.add(x.getName() + " needs your help fighting the skombie! They are doing what they can but it is not enough!");
                    }
                }
                Music.playSound(this.getClass().getClassLoader().getResourceAsStream("music/zombie.wav"));
                userMessages.add("There is a skombie, get it!");
            }
            if (randGen()) {
                double health = player.getHealth();
                Music.playSound(this.getClass().getClassLoader().getResourceAsStream("music/bloodSplash.wav"));
                userMessages.add("Skombie attacks you!! Your health has dropped to " + (health - ATTACK_HEALTH));
                player.setHealth(health - ATTACK_HEALTH);
                checkIfPlayerDead();
            }
        }
        return userMessages;
    }

    //Combat here
    private List<String> checkForZombie() {
        List<String> messages = new ArrayList<>();
        if (currLocation.getCharacters() != null) {
            for (Character character : currLocation.getCharacters()) {
                if (character.isDead() && character.getTurnsInCombat() >= TURNALIVE) {
                    messages.add(character.getName() + " is a zombie. You must attack");
                    character.setAvailableMove(false);
                    messages.add(combatCycle(character));
                    checkIfPlayerDead();
                }
            }
        }
        return messages;
    }

    private void checkIfPlayerDead(){
        if(player.getHealth() <= 0){
            player.setDead(true);
            player.setReasonForDeath("The infected have killed you.");
        }
    }

    private void inspectFurniture(Furniture furniture, String message) {
        boolean inspectingFurniture = true;

        printFurnitureDetails(furniture, new ArrayList<>());
        while (inspectingFurniture) {
            String userInput = prompter.prompt("Please enter a command to proceed or [Back] to go back.");

            if(!userInput.equalsIgnoreCase("back")){
                List<String> statement = promptInFurniture(userInput, furniture);
                Console.clear();
                printFurnitureDetails(furniture, statement);
            }
            else{
               inspectingFurniture = false;
            }
        }
    }

    public void printFurnitureDetails(Furniture furniture, List<String> messages) {
        System.out.println("====================================================");
        System.out.println("Available Commands: Look<Item|Weapon>, Get<Item|Weapon>\n");
        printFile(String.format("images/%s.txt", furniture.getName().trim().toLowerCase()));

        if (furniture.getItems() != null && furniture.getItems().size() != 0) {
            System.out.println("Items:");
            furniture.getItems().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }

        if (furniture.getWeapons() != null && furniture.getWeapons().size() != 0) {
            System.out.println("Weapons:");
            furniture.getWeapons().forEach(x -> System.out.printf("> %s\n", x.getName()));
        }

        if (messages.size() != 0) {
            System.out.println("=======================OUTPUT======================");
            messages.forEach(System.out::println);
            System.out.println("====================================================");
        } else {
            System.out.println("====================================================");
        }
    }

    public List<String> promptInFurniture(String userInput, Furniture furniture) {
        List<String> messages = new ArrayList<>();
        String[] userCommands = parser.verifyInput(userInput);

        if (userCommands == null) {
            messages.add("Not Valid");
            return messages;
        }


        String command = userCommands[0];
        String object = userCommands[1];

        List<Inspectable> inFurniture = new ArrayList<>();
        List<InventoryItem> inventoryItemsInFurniture = new ArrayList<>();

        if (furniture.getItems() != null) {
            inFurniture.addAll(furniture.getItems());
            inventoryItemsInFurniture.addAll(furniture.getItems());
        }
        if (furniture.getWeapons() != null) {
            inFurniture.addAll(furniture.getWeapons());
            inventoryItemsInFurniture.addAll(furniture.getWeapons());
        }

        switch (command) {
            case "look":
            case "examine":
            case "see":
            case "scan":
            case "view":
            case "inspect":
                Inspectable item = inFurniture.stream().filter(x -> x.getName().equalsIgnoreCase(object)).findFirst().orElse(null);
                if (item != null) {
                    if(item.getName().equalsIgnoreCase("files")){
                        messages.addAll(Reader.readFileToArrayList("images/files.txt"));
                    }
                    messages.add(player.look(item));
                } else {
                  messages.add("Item not in room");
                }
                break;

        case "get":
        case "take":
        case "pick":
        case "grab":
        case "acquire":
        case "hold":

            InventoryItem found = inventoryItemsInFurniture.stream().filter(x -> x.getName().equalsIgnoreCase(object)).findFirst().orElse(null);

            if (found != null) {
                if(found.getName().equalsIgnoreCase("gas mask")){
                    player.setHasGasMask(true);
                }
                if (found instanceof Weapon) {
                    Weapon previousWeapon = player.getCurrentWeapon();
                    if (previousWeapon != null) furniture.addWeaponToFurniture(previousWeapon);
                    player.setCurrentWeapon((Weapon) found);
                    messages.add(String.format("%s set to current weapon", found.getName().toUpperCase()));
                    furniture.removeWeaponFromFurniture((Weapon) found);
                }
                else if (found instanceof Item) {
                    if(found.getName().equalsIgnoreCase("files")){
                        messages.add("We probably don't want to carry files around with us.");
                        messages.addAll(Reader.readFileToArrayList("images/files.txt"));
                    }
                    else {
                        player.get(found);
                        messages.add(String.format("%s added to inventory", found.getName().toUpperCase()));
                        furniture.removeItemFromFurniture((Item) found);
                    }
                    }
            } else {
                messages.add(String.format("%s Not a valid item in this location", object.toUpperCase()));
            }
            break;
            default:
                messages.add(String.format("%s not a valid command.", command.toUpperCase()));
        }
        return messages;
    }

    // checks all rooms to see if person is in combat and for how long. If too long, they die. If dead, after a while to turn into zombie and can move.
    public List<String> updateCharacterStatusList() {
        List<String> messages = new ArrayList<>();
        for (Location room : rooms) {
            if (room.getCharacters() != null) {
                for (Character character : room.getCharacters()) {
                    //If character cant move and is alive, it means they are in combat
                    if (!character.isAvailableMove()) {
                        character.setTurnsInCombat(1);
                    }
                    if (character.getTurnsInCombat() == CHARACTERDIES) {
                        messages.add(character.getName() + " is now dead.");
                        character.setDead(true);
                        character.setAvailableMove(false);
                        character.setTurnsInCombat(1);
                    }
                    //turn into zombie
                    if (character.getTurnsInCombat() == TURNALIVE) {
                        character.setAvailableMove(true);
                        messages.add(character.getName() + " is now among the walking dead.");
                        character.setTurnsInCombat(1);
                    }
                }
            }
        }
        return messages;
    }

    private String combatCycle(Character character) {
        String message = "";
        if (randGen()) {
            double health = player.getHealth();
            message = ("Zombie " + character.getName() + " attacks you!!!");
            player.setHealth(health - ATTACK_HEALTH);
        }
        return message;
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

    private boolean randGen() {
        return Math.random() < 0.5;
    }

    public boolean isProgressedPastHelp() {
        return progressedPastHelp;
    }

    public void setProgressedPastHelp(boolean progressedPastHelp) {
        this.progressedPastHelp = progressedPastHelp;
    }

    private boolean checkForSpecialCommand(String input) {
        boolean isSpecial = false;

        if ("QUIT".equalsIgnoreCase(input)) {
            Console.clear();
            isSpecial = true;
            Music.playSound(this.getClass().getClassLoader().getResourceAsStream("music/gamefinish.wav"));
            printFile(QUIT);
            Console.pause(22000);
            System.exit(0);
        } else if ("HELP".equalsIgnoreCase(input)) {
            Console.clear();
            isSpecial = true;
            printFile(HELP);
            Scanner myObj = new Scanner(System.in);
            prompter.prompt("\n[P]roceed ?", "[pP]", "\nNot Valid");

        } else if ("INVENTORY".equalsIgnoreCase(input)) {
            Console.clear();
            isSpecial = true;
            printInventory();
            prompter.prompt("\n[P]roceed ?", "[pP]", "\nNot Valid");
        } else if ("SAVE".equalsIgnoreCase(input)) {
            Console.clear();
            isSpecial = true;
            saveGame();
            Console.pause(3000);
        }
        return isSpecial;
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

    private Location findAvailableLocationInCurrLocation(String location) {
        String valid = currLocation.getAvailableRooms().stream().filter(x -> x.equalsIgnoreCase(location)).findFirst().orElse(null);
        return findLocationByName(valid);
    }

    private InventoryItem findInventoryItemByName(String item) {
        return getInventoryItems().stream().filter(x -> x.getName().equalsIgnoreCase(item)).findFirst().orElse(null);
    }

    public Player getPlayer() {
        return player;
    }

    public void gameComplete(){
        Console.clear();
        Music.playSound(this.getClass().getClassLoader().getResourceAsStream("music/gamefinish.wav"));
        printFile("images/quit.txt");
        Console.pause(22000);
        System.exit(0);
    }
}
