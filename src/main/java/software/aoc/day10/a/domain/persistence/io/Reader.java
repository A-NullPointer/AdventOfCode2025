package software.aoc.day10.a.domain.persistence.io;

import java.util.stream.Stream;

public interface Reader {
    Stream<String> read();
}
