package software.aoc.day02.a.application.persitence.io;

import software.aoc.day02.a.domain.persistence.deserialization.IdRangeParser;
import software.aoc.day02.a.domain.persistence.io.IdRangeReader;
import software.aoc.day02.a.domain.model.IdRange;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Ahora PlainTextIdRangeReader depende de abstracción, no implementación
public class PlainTextIdRangeReader implements IdRangeReader {
    private final String filePath;
    private final IdRangeParser parser;

    public PlainTextIdRangeReader(String filePath, IdRangeParser parser) {
        this.filePath = filePath;
        this.parser = parser; // ✓ DIP cumplido y liscov
    }

    @Override
    public List<IdRange> read() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .flatMap(line -> Arrays.stream(line.split(",")))
                    .map(parser::parse)
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + filePath, e);
        } catch (IOException e) {
            throw new UncheckedIOException("Error reading file: " + filePath, e);
        }
    }
}

/**
public class PlainTextIdRangeReader implements IdRangeReader {

    public final String filePath;
    List<IdRange> idRanges;

    public PlainTextIdRangeReader(String filePath) {
        this.filePath = filePath;
        this.idRanges = new ArrayList<>();
    }

    @Override
    public List<IdRange> read() {
        try (FileReader fileReader = new FileReader(this.filePath)) {
            bufferRead(fileReader);
        } catch (FileNotFoundException e) {
            System.out.println("Input file Not Found");;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this.idRanges;
    }

    private void bufferRead(FileReader fileReader) {
        new BufferedReader(fileReader)
                .lines()
                .map(line -> line.split(","))
                .flatMap(Arrays::stream)
                .forEach(line -> addIdRangeFrom(line));

    }

    private void addIdRangeFrom(String line) {
        this.idRanges.add(new RangeParser().parse(line));
    }
}
*/