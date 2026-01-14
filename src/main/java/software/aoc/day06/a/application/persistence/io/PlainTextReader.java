package software.aoc.day06.a.application.persistence.io;

import software.aoc.day06.a.domain.persistence.io.Reader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlainTextReader implements Reader {
    private final String filePath;

    public PlainTextReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Stream<String> read() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Consumir el stream DENTRO del try para que el reader esté abierto
            return reader.lines().collect(Collectors.toList()).stream();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + filePath, e);
        } catch (IOException e) {
            throw new UncheckedIOException("Error reading file: " + filePath, e);
        }
    }
}
