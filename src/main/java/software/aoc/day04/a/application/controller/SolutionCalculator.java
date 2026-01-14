package software.aoc.day04.a.application.controller;

import software.aoc.day04.a.domain.model.Pos;
import software.aoc.day04.a.domain.service.RollSolver;

import java.util.Set;

public class SolutionCalculator {

    private final RollSolver solver;

    public SolutionCalculator(RollSolver solver) {
        this.solver = solver;
    }

    public int solve(Set<Pos> rolls) {
        return solver.solve(rolls);
    }
}
