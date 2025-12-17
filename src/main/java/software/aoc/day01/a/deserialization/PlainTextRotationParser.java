package software.aoc.day01.a.deserialization;

import software.aoc.day01.a.io.RotationReader;
import software.aoc.day01.a.io.PlainTextRotationReader;
import software.aoc.day01.a.model.Direction;
import software.aoc.day01.a.model.Rotation;

import java.util.ArrayList;
import java.util.List;

public class PlainTextRotationParser implements RotationParser {

    public RotationReader reader;
    public List<Rotation> rotations;

    public PlainTextRotationParser(PlainTextRotationReader reader) {
        this.reader = reader;
        this.rotations = new ArrayList<>();
    }

    @Override
    public List<Rotation> parse(List<String> lines) {
        lines.stream()
                .forEach(line -> {rotations.add(rotationFromLine(line));});
        return rotations;
    }

    private Rotation rotationFromLine(String line) {
        return new Rotation(Direction.valueOf(String.valueOf(line.charAt(0))), Integer.parseInt(line.substring(1)));
    }
}
