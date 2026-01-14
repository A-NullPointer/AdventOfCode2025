package software.aoc.day12.a.domain.model;

import java.util.List;

public record RegionSpec(int width, int height, List<Integer> quantities) {
    public RegionSpec { quantities = List.copyOf(quantities); }

    public int area() { return width * height; }
}
