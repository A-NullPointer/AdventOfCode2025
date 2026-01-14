package software.aoc.day10.b.application.persistence.io;

import software.aoc.day10.b.domain.persistence.io.Reader;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

public class PlainTextReader implements Reader {
    private final String filePath;

    public PlainTextReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Stream<String> read() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> linesList = reader.lines().toList();
            return linesList.stream();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + filePath, e);
        } catch (IOException e) {
            throw new UncheckedIOException("Error reading file: " + filePath, e);
        }
    }
}
