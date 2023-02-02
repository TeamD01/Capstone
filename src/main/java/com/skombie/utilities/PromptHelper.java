package com.skombie.utilities;

import java.util.Scanner;

public class PromptHelper {

    private final Scanner scanner;

    public PromptHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String prompt(String message){
        System.out.println(message);
        return getInput();
    }

    public String prompt(String message, String regex, String error){
        String userInput = null;
        boolean isValid = false;

        while(!isValid){
            System.out.println(message);
            userInput = getInput();
            isValid = userInput.matches(regex);

            if(isValid){
                break;
            }
            System.out.println(error);
        }
        return userInput;
    }

    private String getInput(){
        return this.scanner.nextLine().toUpperCase().trim();
    }
}