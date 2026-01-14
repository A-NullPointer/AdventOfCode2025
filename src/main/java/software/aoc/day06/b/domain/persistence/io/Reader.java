package software.aoc.day06.b.domain.persistence.io;


import java.util.stream.Stream;

public interface Reader {
    Stream<String> read();
}
