package software.aoc.day06.b.domain.persistence.deserialization;

import software.aoc.day06.b.application.control.Worksheet;

import java.util.List;

public interface Parser<T> {
    Worksheet parse(List<String> lines);
}
