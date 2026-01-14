package software.aoc.day02.b.application.persistence.deserialization;

import software.aoc.day02.b.domain.model.IdRange;
import software.aoc.day02.b.domain.persistence.deserialization.IdRangeParser;

public class PlainTextIdRangeParser implements IdRangeParser {

    public PlainTextIdRangeParser() {}

    @Override
    public IdRange parse(String ids) {
        return split(ids);
    }

    private IdRange split(String ranges){
        String[] split = ranges.split("-");
        return new IdRange(Long.parseLong(split[0]), Long.parseLong(split[1]));


    }
}
