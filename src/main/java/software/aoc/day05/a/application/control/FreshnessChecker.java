package software.aoc.day05.a.application.control;

import software.aoc.day05.a.domain.model.IdRange;
import software.aoc.day05.a.domain.model.IngredientId;

import java.util.List;

public class FreshnessChecker {

    private final List<IdRange> freshRanges;

    public FreshnessChecker(List<IdRange> freshRanges) {
        this.freshRanges = freshRanges;
    }

    public boolean isFresh(IngredientId ingredientId) {
        return freshRanges.stream()
                .anyMatch(range -> range.contains(ingredientId));
    }


    public long countFresh(List<IngredientId> ingredientIds) {
        return ingredientIds.stream()
                .filter(this::isFresh)
                .count();
    }

}
