package software.aoc.day06.a.application.control;

import software.aoc.day06.a.domain.model.NumberSequence;
import software.aoc.day06.a.domain.model.Operator;
import software.aoc.day06.a.domain.persistence.deserialization.Parser;

import java.util.List;
import java.util.stream.IntStream;

public record Worksheet(List<NumberSequence> sequences, List<Operator> operators) {

    public long calculateTotal() {
        return IntStream.range(0, Math.min(sequences.size(), operators.size()))
                .mapToLong(i -> sequences.get(i).applyOperator(operators.get(i)))
                .sum();
    }
}