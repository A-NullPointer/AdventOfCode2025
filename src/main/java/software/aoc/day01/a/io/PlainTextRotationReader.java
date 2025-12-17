package software.aoc.day01.a.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlainTextRotationReader implements RotationReader {

    public final String filePath;
    public List<String> fileRotations;

    public PlainTextRotationReader(String filePath) {
        this.filePath = filePath;
        this.fileRotations = new ArrayList<>();
    }

    @Override
    public List<String> read() {
        try (FileReader fileReader = new FileReader(this.filePath)) {
            readRotation(fileReader);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileRotations;
    }

    private void readRotation(FileReader fileReader) {
        new BufferedReader(fileReader)
                .lines()
                .forEach(line -> fileRotations.add(line));
    }
}
