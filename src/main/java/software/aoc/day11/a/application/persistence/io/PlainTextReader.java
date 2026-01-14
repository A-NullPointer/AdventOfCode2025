package software.aoc.day11.a.application.persistence.io;

import software.aoc.day11.a.domain.persistence.io.Reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
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
            List<String> lines = reader.lines().toList();
            return lines.stream();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + filePath, e);
        } catch (IOException e) {
            throw new UncheckedIOException("Error reading file: " + filePath, e);
        }
    }
}
