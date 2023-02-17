package com.skombie.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Reader {

    private Reader(){}

    public static List<String> readFileToArrayList(String fileName){
        List<String> lines = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Reader.class.getClassLoader().getResourceAsStream(fileName))))) {
            reader.lines().forEach(lines::add);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}