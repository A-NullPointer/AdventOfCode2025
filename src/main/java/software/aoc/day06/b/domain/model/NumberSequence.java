package software.aoc.day06.b.domain.model;


import java.util.List;

public record NumberSequence(List<Long> sequence){

    public long applyOperator(Operator operator) {  // Retorna long, no int
        return sequence.stream()
                .reduce(operator::apply)
                .orElseThrow(() -> new IllegalStateException("Empty sequence"));
    }
}
