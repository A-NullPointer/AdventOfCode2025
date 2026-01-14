package software.aoc.day11.b.application.persistence.deserialization;

import software.aoc.day11.b.domain.model.ReactorGraph;
import software.aoc.day11.b.domain.persistence.deserialization.Parser;

import java.util.*;
import java.util.stream.Stream;

public class GraphParser implements Parser<ReactorGraph> {

    @Override
    public List<ReactorGraph> parse(List<String> lines) {
        return List.of(new ReactorGraph(buildAdjacency(lines)));
    }

    private Map<String, List<String>> buildAdjacency(List<String> lines) {
        Map<String, List<String>> adj = new HashMap<>();

        lines.stream()
                .filter(l -> !l.isBlank())
                .map(this::parseLine)
                .forEach(e -> adj.put(e.from(), List.copyOf(e.to())));

        ensureAllNodesExist(adj);
        return adj;
    }

    private LineEdge parseLine(String line) {
        String[] parts = line.split(":");
        String from = parts[0].trim();
        List<String> to = parseTargets(parts.length > 1 ? parts[1] : "");
        return new LineEdge(from, to);
    }

    private List<String> parseTargets(String rhs) {
        String trimmed = rhs.trim();
        if (trimmed.isEmpty()) return List.of();

        return Stream.of(trimmed.split("\\s+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    private void ensureAllNodesExist(Map<String, List<String>> adj) {
        Set<String> referenced = adj.values().stream()
                .flatMap(List::stream)
                .collect(HashSet::new, Set::add, Set::addAll);

        referenced.stream()
                .filter(n -> !adj.containsKey(n))
                .forEach(n -> adj.put(n, List.of()));
    }

    private record LineEdge(String from, List<String> to) {}
}
