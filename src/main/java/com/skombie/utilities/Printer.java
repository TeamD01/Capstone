package com.skombie.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Printer {


    public static void printFile(String fileName){
        printFile(fileName, 0);
    }

    /*
    * Print file
    * @param fileName - the file that needs to be printed
    * @param pauseAtLine - how long to pause after each line print (long milliseconds)
    * */
    public static void printFile(String fileName, long pauseAtLine) {
        try (InputStream stream = Printer.class.getClassLoader().getResourceAsStream(fileName)) {
            assert stream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
                String line;
                while((line = reader.readLine()) != null){
                    System.out.println(line);
                    Console.pause(pauseAtLine);
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
