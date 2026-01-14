package software.aoc.day05.a.application.persistence.deserialization;

import software.aoc.day05.a.domain.model.IngredientId;
import software.aoc.day05.a.domain.persistence.deserialization.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class IngredientIdParser implements Parser<IngredientId> {

    private final List<IngredientId> IngredientIds;

    public IngredientIdParser() {
        this.IngredientIds = new ArrayList<>();
    }

    @Override
    public List<IngredientId> parse(List<String> lines) {
        lines.forEach(line -> getIngredientId(line));

        return Collections.unmodifiableList(IngredientIds);
    }

    private void getIngredientId(String line) {
        IngredientIds.add(new IngredientId(Long.parseLong(line)));
    }
}
