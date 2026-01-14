package software.aoc.day07.a.domain.persistence.deserialization;

import software.aoc.day07.a.domain.model.Manifold;

import java.util.List;

public interface Parser<T> {
    T parse(List<String> lines);
}
