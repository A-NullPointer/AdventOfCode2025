package software.aoc.day05.b.application.persistence.deserialization;

import software.aoc.day05.b.domain.model.IdRange;
import software.aoc.day05.b.domain.model.IngredientId;
import software.aoc.day05.b.domain.persistence.deserialization.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IdRangeParser implements Parser<IdRange> {

    private final List<IdRange> IdRanges;

    public IdRangeParser() {
        IdRanges = new ArrayList<>();
    }

    @Override
    public List<IdRange> parse(List<String> lines) {

        lines.forEach(line -> IdRanges.add(split(line)));
        return Collections.unmodifiableList(IdRanges);
    }

    private IdRange split(String ranges){
        //refactorizar
        String[] split = ranges.split("-");
        // mejorar porque la linea es super larga
        return new IdRange(new IngredientId(Long.parseLong(split[0])), new IngredientId(Long.parseLong(split[1])));
    }


}
