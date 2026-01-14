package software.aoc.day11.a.application.controller;

import software.aoc.day11.a.domain.model.ReactorGraph;
import software.aoc.day11.a.domain.service.PathCounter;

import java.math.BigInteger;

public class SolutionCalculator {

    private final PathCounter counter;

    public SolutionCalculator(PathCounter counter) {
        this.counter = counter;
    }

    public BigInteger countPathsYouToOut(ReactorGraph graph) {
        return counter.countPaths(graph, "you", "out");
    }
}
