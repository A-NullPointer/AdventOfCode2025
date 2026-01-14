package software.aoc.day04.a.domain.service;

import software.aoc.day04.a.domain.model.Pos;

import java.util.Set;

public interface RollSolver {
    int solve(Set<Pos> rolls);
}
