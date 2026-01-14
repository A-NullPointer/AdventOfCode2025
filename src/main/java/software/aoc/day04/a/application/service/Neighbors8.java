package software.aoc.day04.a.application.service;

import software.aoc.day04.a.domain.model.Pos;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public final class Neighbors8 {
    private Neighbors8() {}

    public static Stream<Pos> of(Pos p) {
        int r = p.row();
        int c = p.col();
        return List.of(
                new Pos(r - 1, c - 1), new Pos(r - 1, c), new Pos(r - 1, c + 1),
                new Pos(r,     c - 1),                 new Pos(r,     c + 1),
                new Pos(r + 1, c - 1), new Pos(r + 1, c), new Pos(r + 1, c + 1)
        ).stream();
    }

    public static long adjacentRolls(Pos p, Set<Pos> rolls) {
        return of(p).filter(rolls::contains).count();
    }
}
