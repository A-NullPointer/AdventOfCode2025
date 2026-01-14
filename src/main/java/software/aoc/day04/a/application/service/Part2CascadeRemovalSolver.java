package software.aoc.day04.a.application.service;

import software.aoc.day04.a.domain.model.Pos;
import software.aoc.day04.a.domain.service.RollSolver;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Part2CascadeRemovalSolver implements RollSolver {

    @Override
    public int solve(Set<Pos> initial) {
        Set<Pos> rolls = new HashSet<>(initial);
        Set<Pos> toCheck = new HashSet<>(initial);
        int removed = 0;

        while (true) {
            Set<Pos> removable = removableIn(rolls, toCheck);
            if (removable.isEmpty()) return removed;

            removed += removable.size();
            rolls.removeAll(removable);
            toCheck = nextToCheck(rolls, removable);
        }
    }

    private Set<Pos> removableIn(Set<Pos> rolls, Set<Pos> toCheck) {
        return toCheck.stream()
                .filter(rolls::contains)
                .filter(p -> Neighbors8.adjacentRolls(p, rolls) < 4)
                .collect(Collectors.toSet());
    }

    private Set<Pos> nextToCheck(Set<Pos> rolls, Set<Pos> removedNow) {
        return removedNow.stream()
                .flatMap(Neighbors8::of)
                .filter(rolls::contains)
                .collect(Collectors.toSet());
    }
}
