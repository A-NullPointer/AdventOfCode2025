package software.aoc.day07.b.domain.persistence.deserialization;

import java.util.List;

public interface Parser<T> {
    T parse(List<String> lines);
}
