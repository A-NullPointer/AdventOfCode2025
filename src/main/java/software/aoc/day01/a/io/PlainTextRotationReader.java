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
            readFile(fileReader);
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileRotations;
    }

    private void readFile(FileReader fileReader) {
        new BufferedReader(fileReader)
                .lines()
                .forEach(fileRotations::add);
    }
}
