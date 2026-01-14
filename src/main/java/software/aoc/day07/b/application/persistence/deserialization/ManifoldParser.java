package software.aoc.day07.b.application.persistence.deserialization;

import software.aoc.day07.b.domain.model.Manifold;
import software.aoc.day07.b.domain.persistence.deserialization.Parser;

import java.util.List;

public final class ManifoldParser implements Parser<Manifold> {

    @Override
    public Manifold parse(List<String> lines) {
        return new Manifold.Builder().fromLines(lines);
    }
}
