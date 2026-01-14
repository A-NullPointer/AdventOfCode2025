package software.aoc.day02.b.application.control;

import software.aoc.day02.b.domain.model.IdRange;
import software.aoc.day02.b.domain.persistence.io.IdRangeReader;
import software.aoc.day02.b.domain.validators.IdValidator;

import java.util.List;

// TODO: Elejir en qué capa de la arquitectura debería de estar
public class Solver {
    private final IdRangeReader reader;
    private final IdValidator validator;

    public Solver(IdRangeReader reader, IdValidator validator) {
        this.reader = reader;
        this.validator = validator;
    }

    public long solve() {
        List<IdRange> ranges = reader.read();
        return calculateSumOfInvalidIds(ranges);
    }

    private long calculateSumOfInvalidIds(List<IdRange> ranges) {
        return ranges.stream()
                .flatMap(IdRange::stream)
                .filter(validator::isInvalid)
                .mapToLong(Long::longValue)
                .sum();
    }
}
