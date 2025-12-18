package software.aoc.day01.a;

import software.aoc.day01.a.application.Operation;
import software.aoc.day01.a.deserialization.PlainTextRotationParser;
import software.aoc.day01.a.io.PlainTextRotationReader;
import software.aoc.day01.a.model.Dial;
import software.aoc.day01.a.model.Rotation;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        /**
         * CÓDIGO DE PUEBA SOLO
         */
//        InputStream resource = Main.class.getResourceAsStream("/day01/a/inputs.txt");
//        InputStreamReader inputStreamReader = new InputStreamReader(resource);
//        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//        System.out.println(bufferedReader.readAllLines());
//
//        String path = "C:/Users/asmae/Documents/INGENIERIA INFORMATICA/3 TERCERO/IS2/2025-26/AdventOfCode2025/AdventOfCode2025/src/main/resources/day01/a/inputs.txt";
//
//        PlainTextRotationReader txtRotationReader = new PlainTextRotationReader(path);
//        List<Rotation> rotations = txtRotationReader.read();
//        System.out.println(rotations);

//        Dial dial = new Dial(50, 0, 99);
//        int currentPosition = dial.getPosition();
//        int offLimit = dial.getMax() + 1;
//        int sequence = 0;
//
//        for (Rotation rotation : rotations) {
//            //System.out.println(rotation.direction().getDelta());
//            currentPosition = (currentPosition + (rotation.direction().getDelta() * rotation.times()));
//            if (currentPosition < dial.getMin()) {
//                currentPosition = (offLimit) + currentPosition;
//                sequence++;
//            } else if (currentPosition > dial.getMax()) {
//                currentPosition =  currentPosition - (offLimit);
//                sequence++;
//            }
//        }
//
//        System.out.println(currentPosition);
//        System.out.println(sequence);


        String path = "C:/Users/asmae/Documents/INGENIERIA INFORMATICA/3 TERCERO/IS2/2025-26/AdventOfCode2025/AdventOfCode2025/src/main/resources/day01/a/inputs.txt";
        PlainTextRotationReader txtRotationReader = new PlainTextRotationReader(path);
        List<Rotation> rotations = txtRotationReader.read();
        Dial dial = new Dial(50, 0, 99);
        Operation operation = new Operation(rotations, dial);
        int password = operation.obtainSequence();
        System.out.println("Password: " + password);

        /**
         * --------------------------------------------------------------------
         */
    }
}
