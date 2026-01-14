package software.aoc.day12.a.domain.model;

import java.util.List;

public record PuzzleInput(List<Shape> shapes, List<RegionSpec> regions) {
    public PuzzleInput {
        shapes = List.copyOf(shapes);
        regions = List.copyOf(regions);
    }
}
