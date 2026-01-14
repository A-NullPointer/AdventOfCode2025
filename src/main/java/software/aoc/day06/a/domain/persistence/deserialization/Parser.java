package software.aoc.day06.a.domain.persistence.deserialization;

import software.aoc.day06.a.application.control.Worksheet;

import java.util.List;

public interface Parser<T> {
    Worksheet parse(List<String> lines);
}
