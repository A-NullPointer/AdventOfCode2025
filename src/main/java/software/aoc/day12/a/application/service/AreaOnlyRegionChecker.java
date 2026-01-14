package software.aoc.day12.a.application.service;

import software.aoc.day12.a.domain.model.PuzzleInput;
import software.aoc.day12.a.domain.model.RegionSpec;
import software.aoc.day12.a.domain.model.Shape;

import java.util.List;
import java.util.stream.IntStream;

public class AreaOnlyRegionChecker {

    public long countFittableRegions(PuzzleInput input) {
        List<Integer> shapeAreas = input.shapes().stream().map(Shape::area).toList();

        return input.regions().stream()
                .filter(r -> regionArea(r) >= requiredArea(r, shapeAreas))
                .count();
    }

    private int regionArea(RegionSpec r) {
        return r.area();
    }

    private int requiredArea(RegionSpec r, List<Integer> shapeAreas) {
        return IntStream.range(0, Math.min(r.quantities().size(), shapeAreas.size()))
                .map(i -> r.quantities().get(i) * shapeAreas.get(i))
                .sum();
    }
}
