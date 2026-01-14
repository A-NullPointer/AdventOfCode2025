package software.aoc.day02.b.domain.persistence.io;

import software.aoc.day02.b.domain.model.IdRange;

import java.util.List;

public interface IdRangeReader {
    List<IdRange> read();
}
