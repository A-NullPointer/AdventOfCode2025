package software.aoc.day05.a.domain.model;


import software.aoc.day05.a.domain.model.IngredientId;

public record IdRange(IngredientId firstId, IngredientId lastId) {

    public boolean contains(IngredientId id) {
        return id.value() >= firstId.value() && id.value() <= lastId.value();
    }
}
