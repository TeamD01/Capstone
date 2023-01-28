package main.java.com.skombie.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Printer {


    public static void printFile(String fileName) {
        try {
            System.out.println(Files.readString(Path.of(fileName)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
