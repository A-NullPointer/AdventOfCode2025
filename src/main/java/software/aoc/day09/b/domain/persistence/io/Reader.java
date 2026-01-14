package software.aoc.day09.b.domain.persistence.io;


import java.util.stream.Stream;

public interface Reader {
    Stream<String> read();
}
