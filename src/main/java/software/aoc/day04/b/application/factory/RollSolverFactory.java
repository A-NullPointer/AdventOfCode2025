package software.aoc.day04.b.application.factory;

import software.aoc.day04.b.domain.service.RollSolver;

public class RollSolverFactory {

    private final RollSolver part1;
    private final RollSolver part2;

    public RollSolverFactory(RollSolver part1, RollSolver part2) {
        this.part1 = part1;
        this.part2 = part2;
    }

    public RollSolver forPart(int part) {
        return part == 1 ? part1 : part2;
    }
}
