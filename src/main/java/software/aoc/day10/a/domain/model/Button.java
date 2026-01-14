package software.aoc.day10.a.domain.model;

import java.util.List;

public record Button(int id, List<Integer> lightIndices) {
    public Button {
        lightIndices = List.copyOf(lightIndices);
    }

    public boolean affectsLight(int lightIndex) {
        return lightIndices.contains(lightIndex);
    }
}
