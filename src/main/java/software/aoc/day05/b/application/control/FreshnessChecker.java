package software.aoc.day05.b.application.control;

import software.aoc.day05.b.domain.model.IdRange;
import software.aoc.day05.b.domain.model.IngredientId;

import java.util.List;

public class FreshnessChecker {

    private final List<IdRange> freshRanges;

    public FreshnessChecker(List<IdRange> freshRanges) {
        this.freshRanges = freshRanges;
    }

    /**
     * Verifica si un ingrediente es fresco (fresh).
     * Un ingrediente es fresco si está en al menos uno de los rangos.
     */
    public boolean isFresh(IngredientId ingredientId) {
        return freshRanges.stream()
                .anyMatch(range -> range.contains(ingredientId));
    }

    /**
     * Cuenta cuántos ingredientes de la lista son frescos.
     */
    public long countFresh(List<IngredientId> ingredientIds) {
        return ingredientIds.stream()
                .filter(this::isFresh)
                .count();
    }

}
