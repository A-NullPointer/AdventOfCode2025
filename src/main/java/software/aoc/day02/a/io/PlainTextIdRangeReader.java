package software.aoc.day02.a.io;

import software.aoc.day02.a.deserialization.IdRangeParser;
import software.aoc.day02.a.deserialization.PlainTextIdRangeParser;
import software.aoc.day02.a.model.IdRange;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        this.idRanges.add(new PlainTextIdRangeParser().parse(line));
    }
}
