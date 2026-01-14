package software.aoc.day02.a.domain.model;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public record IdRange(long firstId, long lastId) {

    public Stream<Long> stream() {
        return LongStream.rangeClosed(firstId, lastId).boxed();
    }
}
