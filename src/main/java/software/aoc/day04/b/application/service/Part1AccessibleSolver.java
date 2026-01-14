package software.aoc.day04.b.application.service;

import software.aoc.day04.b.domain.model.Pos;
import software.aoc.day04.b.domain.service.RollSolver;

import java.util.Set;

public class Part1AccessibleSolver implements RollSolver {

    @Override
    public int solve(Set<Pos> rolls) {
        return (int) rolls.stream()
                .filter(p -> Neighbors8.adjacentRolls(p, rolls) < 4)
                .count();
    }
}
