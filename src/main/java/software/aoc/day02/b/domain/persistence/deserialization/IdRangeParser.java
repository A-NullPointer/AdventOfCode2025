package software.aoc.day02.b.domain.persistence.deserialization;

import software.aoc.day02.b.domain.model.IdRange;

public interface IdRangeParser {
    IdRange parse(String ids);
}
