package software.aoc.day01.b.deserialization;

import software.aoc.day01.b.model.Rotation;

public interface RotationParser {
    Rotation parse(String lines);
}
