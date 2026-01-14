package software.aoc.day10.b.application.controller;

import software.aoc.day10.b.domain.model.Machine;
import software.aoc.day10.b.domain.service.MachineSolver;

import java.util.List;

public class SolutionCalculator {

    private final MachineSolver solver;

    public SolutionCalculator(MachineSolver solver) {
        this.solver = solver;
    }

    public int calculateTotalMinimumPresses(List<Machine> machines) {
        return machines.stream().mapToInt(solver::minPresses).sum();
    }
}
