package software.aoc.day07.b.application.persistence.io;

import software.aoc.day07.b.domain.persistence.io.Reader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PlainTextReader implements Reader {
    private final String filePath;

    public PlainTextReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Stream<String> read() {
        try {
            return Files.readAllLines(Paths.get(filePath)).stream();
        } catch (IOException e) {
            throw new UncheckedIOException("Error reading file: " + filePath, e);
        }
    }
}
