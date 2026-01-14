package software.aoc.day02.a.domain.persistence.io;

import software.aoc.day02.a.domain.model.IdRange;

import java.util.List;

public interface IdRangeReader {
    List<IdRange> read();
}
