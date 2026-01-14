package software.aoc.day04.a.application.persistence.deserialization;

import software.aoc.day04.a.domain.model.Pos;
import software.aoc.day04.a.domain.persistence.deserialization.Parser;

import java.util.List;
import java.util.stream.IntStream;

public class RollPositionsParser implements Parser<List<Pos>> {

    @Override
    public List<List<Pos>> parse(List<String> lines) {
        return List.of(parseRolls(lines));
    }

    private List<Pos> parseRolls(List<String> lines) {
        return IntStream.range(0, lines.size())
                .boxed()
                .flatMap(r -> positionsInRow(lines.get(r), r))
                .toList();
    }

    private java.util.stream.Stream<Pos> positionsInRow(String row, int r) {
        return IntStream.range(0, row.length())
                .filter(c -> row.charAt(c) == '@')
                .mapToObj(c -> new Pos(r, c));
    }
}
