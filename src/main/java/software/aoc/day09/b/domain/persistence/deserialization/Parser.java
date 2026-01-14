package software.aoc.day09.b.domain.persistence.deserialization;


import java.util.List;
import java.util.stream.Stream;

public interface Parser<T> {
    List<T> parse(Stream<String> lines);
}
