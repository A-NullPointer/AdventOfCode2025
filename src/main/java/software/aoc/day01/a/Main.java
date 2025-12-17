package software.aoc.day01.a;

import software.aoc.day01.a.deserialization.PlainTextRotationParser;
import software.aoc.day01.a.io.PlainTextRotationReader;
import software.aoc.day01.a.model.Rotation;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        InputStream resource = Main.class.getResourceAsStream("/day01/a/inputs.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(resource);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        System.out.println(bufferedReader.readAllLines());

        String path = "C:/Users/asmae/Documents/INGENIERIA INFORMATICA/3 TERCERO/IS2/2025-26/AdventOfCode2025/AdventOfCode2025/src/main/resources/day01/a/inputs.txt";
        FileReader fileReader = new FileReader(path);
        bufferedReader = new BufferedReader(fileReader);
        System.out.println(bufferedReader.readAllLines());

        PlainTextRotationReader txtRotationReader = new PlainTextRotationReader(path);
        List<String> fileLines = txtRotationReader.read();
        System.out.println(fileLines);
    }
}
