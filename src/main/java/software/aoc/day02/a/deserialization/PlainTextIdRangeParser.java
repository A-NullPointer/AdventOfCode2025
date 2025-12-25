package software.aoc.day02.a.deserialization;

import software.aoc.day02.a.model.Id;
import software.aoc.day02.a.model.IdRange;

import java.util.ArrayList;
import java.util.List;

public class PlainTextIdRangeParser implements IdRangeParser {

    public List<Id> RangeOfIds;

    public PlainTextIdRangeParser() {
        RangeOfIds = new ArrayList<>();
    }

    @Override
    public IdRange parse(String ids) {
        return getIdsIn(split(ids));
    }

    private List<String> split(String range){
        return List.of(range.split("-"));
    }

    private IdRange getIdsIn(List<String> list){
        list.stream()
                .map(Long::parseLong)
                .forEach(element -> RangeOfIds.add(new Id(element)));
        return new IdRange(RangeOfIds.getFirst(), RangeOfIds.getLast());
    }
}
