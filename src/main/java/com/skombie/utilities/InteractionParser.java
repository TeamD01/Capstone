package com.skombie.utilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class InteractionParser {
    Scanner sc = new Scanner(System.in);
    PromptHelper prompt = new PromptHelper(sc);
    String[] verbs = new String[] {"use", "look", "go", "get", "drop"};
    String[] nouns = new String[] {"kitchen","bedroom", "basement", "attic", "backyard"};

    public InteractionParser() {
    }

    private String[] verifyInput() {
        String[] strSplit;

        System.out.println("Please enter a verb noun command or type *Help* to see a list of commands");
        while (true) {
            String temp = sc.nextLine().toLowerCase(Locale.ROOT);
            if (temp.matches("([a-zA-Z ]*)") && temp.split(" ").length == 2) {
                strSplit = temp.split(" ");
                break;
            }
            else if (temp.equals("help")) {
                prompt.checkHelp(temp);
                System.out.println("Please enter a verb noun command or type *Help* to see a list of commands");
            }
            else if (temp.equals("quit")) {
                prompt.checkQuit(temp);
            }
            else {
                System.out.println("Please enter a verb noun command or type *Help* to see a list of commands");
            }
        }
        return  strSplit;
    }

    public String[] verifyCommand() {
        while (true) {
            String[] command = verifyInput();
            boolean containsVerb = Arrays.stream(verbs).anyMatch(command[0]::equals);
            boolean containsNoun = Arrays.stream(nouns).anyMatch(command[1]::equals);
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
}