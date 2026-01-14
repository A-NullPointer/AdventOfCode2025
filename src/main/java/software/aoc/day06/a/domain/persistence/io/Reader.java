package software.aoc.day06.a.domain.persistence.io;


import java.util.stream.Stream;

public interface Reader {
    Stream<String> read();
}
