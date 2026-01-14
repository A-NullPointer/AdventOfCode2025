package software.aoc.day04.b.domain.service;

import software.aoc.day04.b.domain.model.Pos;

import java.util.Set;

public interface RollSolver {
    int solve(Set<Pos> rolls);
}
