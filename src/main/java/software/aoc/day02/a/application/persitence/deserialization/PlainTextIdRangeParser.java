package software.aoc.day02.a.application.persitence.deserialization;

import software.aoc.day02.a.domain.persistence.deserialization.IdRangeParser;
import software.aoc.day02.a.domain.model.IdRange;

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
