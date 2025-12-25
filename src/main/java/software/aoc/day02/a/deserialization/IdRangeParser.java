package software.aoc.day02.a.deserialization;

import software.aoc.day02.a.model.IdRange;

public interface IdRangeParser {
    IdRange parse(String ids);
}
