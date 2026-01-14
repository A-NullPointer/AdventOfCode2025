package software.aoc.day02.a.domain.persistence.deserialization;

import software.aoc.day02.a.domain.model.IdRange;

public interface IdRangeParser {
    IdRange parse(String ids);
}
