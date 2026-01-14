package software.aoc.day10.b.application.factory;

import software.aoc.day10.b.domain.service.MachineSolver;

public class MachineSolverFactory {
    private final MachineSolver part1;
    private final MachineSolver part2;

    public MachineSolverFactory(MachineSolver part1, MachineSolver part2) {
        this.part1 = part1;
        this.part2 = part2;
    }

    public MachineSolver forPart(int part) {
        return part == 1 ? part1 : part2;
    }
}
