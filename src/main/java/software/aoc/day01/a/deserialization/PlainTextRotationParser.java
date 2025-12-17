package software.aoc.day01.a.deserialization;

import software.aoc.day01.a.io.RotationReader;
import software.aoc.day01.a.io.PlainTextRotationReader;
import software.aoc.day01.a.model.Direction;
import software.aoc.day01.a.model.Rotation;

import java.util.ArrayList;
import java.util.List;

public class PlainTextRotationParser implements RotationParser {

    public PlainTextRotationParser() {
    }

    @Override
    public Rotation parse(String line) {
        return new Rotation(getDirection(line), getTimes(line));
    }

    private static Direction getDirection(String line) {
        return Direction.valueOf(String.valueOf(line.charAt(0)));
    }

    private static int getTimes(String line) {
        return Integer.parseInt(line.substring(1));
    }
}
