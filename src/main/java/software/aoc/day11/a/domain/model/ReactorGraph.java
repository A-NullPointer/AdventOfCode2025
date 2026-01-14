package software.aoc.day11.a.domain.model;

import java.util.List;
import java.util.Map;

public record ReactorGraph(Map<String, List<String>> adjacency) {

    public ReactorGraph {
        adjacency = Map.copyOf(adjacency);
    }

    public List<String> nextOf(String node) {
        return adjacency.getOrDefault(node, List.of());
    }

    public boolean contains(String node) {
        return adjacency.containsKey(node);
    }
}
