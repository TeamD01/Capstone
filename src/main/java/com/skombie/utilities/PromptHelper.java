package com.skombie.utilities;

import java.util.Scanner;

public class PromptHelper {
    private final Scanner scanner;

    public PromptHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String prompt(String message){
        System.out.println(message);
        return this.scanner.nextLine();
    }

    public String prompt(String dialogue, String regex, String error){
        String userInput = null;
        boolean isValid = false;

        while(!isValid){
            System.out.println(dialogue);
            userInput = this.scanner.nextLine();
            isValid = userInput.matches(regex);

            if(isValid){
                break;
            }
            System.out.println(error);
        }
        return userInput;
    }
}