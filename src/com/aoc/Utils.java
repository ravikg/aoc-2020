package com.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Utils {

    public static Stream<String> readFileByLine(String filePath) {
        try {
            return Files.lines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Stream.empty();
    }

}
