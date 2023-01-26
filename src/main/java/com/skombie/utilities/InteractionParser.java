package com.skombie.utilities;
import java.util.Locale;
import java.util.Scanner;

public class InteractionParser {
    Scanner sc = new Scanner(System.in);

    public InteractionParser() {
    }

    public String[] verifyInput() {
        String[] strSplit;

        System.out.println("Please enter a verb noun command or type *Get Help* to see a list of commands");
        while (true) {
            String temp = sc.nextLine().toLowerCase(Locale.ROOT);
            if (temp.matches("([a-zA-Z ]*)") && temp.split(" ").length == 2) {
                strSplit = temp.split(" ");
                break;
            }
            else {
                System.out.println("Please enter a verb noun command or type *Get Help* to see a list of commands");
            }
        }
        return  strSplit;
    }

}