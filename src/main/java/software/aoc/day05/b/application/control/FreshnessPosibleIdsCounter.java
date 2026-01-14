package software.aoc.day05.b.application.control;

import software.aoc.day05.b.domain.model.IdRange;
import software.aoc.day05.b.domain.model.IngredientId;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FreshnessPosibleIdsCounter {

    private final List<IdRange> freshRanges;

    public FreshnessPosibleIdsCounter(List<IdRange> freshRanges) {
        this.freshRanges = freshRanges;
    }

    public long howManyFreshIds() {
        List<IdRange> fusionados = new ArrayList<>();

        freshRanges.stream()
                .sorted(Comparator.comparingLong(r -> r.firstId().value()))
                .forEach(r -> {
                    if (fusionados.isEmpty() || r.firstId().value() > fusionados.getLast().lastId().value()) {
                        fusionados.add(r);
                    } else {
                        IdRange ultimo = fusionados.removeLast();
                        long newEnd = Math.max(ultimo.lastId().value(), r.lastId().value());
                        fusionados.add(new IdRange(ultimo.firstId(), new IngredientId(newEnd)));
                    }
                });

        return fusionados.stream()
                .mapToLong(r -> r.lastId().value() - r.firstId().value() + 1)
                .sum();
    }
}
