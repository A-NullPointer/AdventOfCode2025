package software.aoc.day05.a.domain.persistence.deserialization;

import java.util.List;
import java.util.stream.Stream;

public interface Parser<T> {
    List<T> parse(List<String> lines);
}
