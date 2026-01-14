package software.aoc.day08.b.domain.persistence.deserialization;


import java.util.List;

public interface Parser<T> {
    List<T> parse(List<String> lines);
}
