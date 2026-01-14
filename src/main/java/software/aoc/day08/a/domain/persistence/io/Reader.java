package software.aoc.day08.a.domain.persistence.io;


import java.util.stream.Stream;

public interface Reader {
    Stream<String> read();
}
