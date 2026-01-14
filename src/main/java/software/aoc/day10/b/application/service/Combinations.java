package software.aoc.day10.b.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class Combinations {
    private Combinations() {}

    public static <T> Stream<List<T>> of(List<T> items, int k) {
        return of(items, k, 0, new ArrayList<>());
    }

    private static <T> Stream<List<T>> of(List<T> items, int k, int start, List<T> acc) {
        if (acc.size() == k) return Stream.of(List.copyOf(acc));
        if (start >= items.size()) return Stream.empty();

        return Stream.concat(
                take(items, k, start, acc),
                skip(items, k, start, acc)
        );
    }

    private static <T> Stream<List<T>> take(List<T> items, int k, int start, List<T> acc) {
        acc.add(items.get(start));
        var s = of(items, k, start + 1, acc);
        acc.remove(acc.size() - 1);
        return s;
    }

    private static <T> Stream<List<T>> skip(List<T> items, int k, int start, List<T> acc) {
        return of(items, k, start + 1, acc);
    }
}
