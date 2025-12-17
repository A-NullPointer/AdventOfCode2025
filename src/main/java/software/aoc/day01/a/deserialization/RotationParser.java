package software.aoc.day01.a.deserialization;

import software.aoc.day01.a.model.Rotation;

import java.util.List;

public interface RotationParser {
    Rotation parse(String lines);
}
