package software.aoc.day01.a;

import software.aoc.day01.a.application.Operation;
import software.aoc.day01.a.io.PlainTextRotationReader;
import software.aoc.day01.a.model.Dial;
import software.aoc.day01.a.model.Rotation;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String path = "C:/Users/asmae/Documents/INGENIERIA INFORMATICA/3 TERCERO/IS2/2025-26/AdventOfCode2025/AdventOfCode2025/src/main/resources/day01/a/inputs.txt";

        PlainTextRotationReader txtRotationReader = new PlainTextRotationReader(path);
        List<Rotation> rotations = txtRotationReader.read();

        Dial dial = new Dial(50, 0, 99);

        Operation operation = new Operation(rotations, dial);
        int password = operation.obtainSequence();
        System.out.println("Password: " + password);
    }
}
