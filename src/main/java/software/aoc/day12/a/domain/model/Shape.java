package software.aoc.day12.a.domain.model;

import java.util.List;

public record Shape(int id, List<String> rows) {
    public Shape { rows = List.copyOf(rows); }

    public int area() {
        return (int) rows.stream()
                .flatMapToInt(String::chars)
                .filter(c -> c == '#')
                .count();
    }
}
