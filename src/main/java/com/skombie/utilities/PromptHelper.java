package com.skombie.utilities;

import com.skombie.utilities.Printer;

import java.util.Scanner;

public class PromptHelper {
// MUST USE "Printer.printFile(<INSERT FILENAME>) instead of System.println"
    private static final String QUIT = "images/quit.txt";
    private static final String HELP = "data/intro";

    private final Scanner scanner;

    public PromptHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String prompt(String message){
        System.out.println(message);
        String userInput = this.scanner.nextLine();
        checkQuit(userInput);
        return userInput;
    }

    public String prompt(String dialogue, String regex, String error){
        String userInput = null;
        boolean isValid = false;

        while(!isValid){
            System.out.println(dialogue);
            userInput = this.scanner.nextLine();

            checkQuit(userInput);

            isValid = userInput.matches(regex);

            if(isValid){
                break;
            }
            System.out.println(error);
        }
        return userInput;
    }
    public void checkQuit(String input){
      if("QUIT".equalsIgnoreCase(input)){
          Printer.printFile(QUIT);
          System.exit(0);
      }
    };

    public void  checkHelp(String input){
        if("Help".equalsIgnoreCase(input)){
            Printer.printFile(HELP);
        }
    };
}