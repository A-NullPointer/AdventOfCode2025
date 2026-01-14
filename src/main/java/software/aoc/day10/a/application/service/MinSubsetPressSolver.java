package software.aoc.day10.a.application.service;

import software.aoc.day10.a.domain.model.Button;
import software.aoc.day10.a.domain.model.Light;
import software.aoc.day10.a.domain.model.LightConfiguration;
import software.aoc.day10.a.domain.model.Machine;
import software.aoc.day10.a.domain.service.MachineSolver;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MinSubsetPressSolver implements MachineSolver {

    @Override
    public int minPresses(Machine machine) {
        return IntStream.rangeClosed(0, machine.buttons().size())
                .filter(k -> existsCombination(machine, k))
                .findFirst()
                .orElseThrow();
    }

    private boolean existsCombination(Machine machine, int k) {
        return combinations(machine.buttons(), k)
                .map(combo -> apply(combo, machine.getLightCount()))
                .anyMatch(cfg -> cfg.matches(machine.targetConfiguration()));
    }

    private LightConfiguration apply(List<Button> combo, int lightCount) {
        var arr = IntStream.range(0, lightCount).mapToObj(i -> Light.OFF).toArray(Light[]::new);
        combo.forEach(b -> b.lightIndices().forEach(i -> arr[i] = arr[i].toggle()));
        return new LightConfiguration(List.of(arr));
    }

    private Stream<List<Button>> combinations(List<Button> items, int k) {
        return Combinations.of(items, k); // helper estático dedicado (Single Responsibility)
    }
}
