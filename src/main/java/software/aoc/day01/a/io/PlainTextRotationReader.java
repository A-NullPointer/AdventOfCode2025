package software.aoc.day01.a.io;

import software.aoc.day01.a.deserialization.PlainTextRotationParser;
import software.aoc.day01.a.deserialization.RotationParser;
import software.aoc.day01.a.model.Rotation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlainTextRotationReader implements RotationReader {

    public final String filePath;
    public RotationParser parser;
    public List<Rotation> Rotations;

    public PlainTextRotationReader(String filePath) {
        this.filePath = filePath;
        this.parser = new PlainTextRotationParser();
        this.Rotations = new ArrayList<>();
    }

    @Override
    public List<Rotation> read() {
        try (FileReader fileReader = new FileReader(this.filePath)) {
            readFile(fileReader);
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.Rotations;
    }

    private void readFile(FileReader fileReader) {
        new BufferedReader(fileReader)
                .lines()
                .forEach(this::addParsedRotation);
    }

    private void addParsedRotation(String s) {
        this.Rotations.add(this.parser.parse(s));
    }
}
