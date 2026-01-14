package software.aoc.day02.b.domain.model;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public record IdRange(long firstId, long lastId) {

    // TODO: Y si Id ya no es de tipo Long??
    public Stream<Long> stream() {
        return LongStream.rangeClosed(firstId, lastId).boxed();
    }
}
