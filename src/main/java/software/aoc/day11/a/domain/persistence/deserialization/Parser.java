package software.aoc.day11.a.domain.persistence.deserialization;

import java.util.List;

public interface Parser<T> {
    List<T> parse(List<String> lines);
}
