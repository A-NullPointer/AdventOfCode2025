package software.aoc.day11.b.application.service;

import software.aoc.day11.b.domain.model.ReactorGraph;
import software.aoc.day11.b.domain.service.PathCounter;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MemoizedDfsPathCounter implements PathCounter {

    @Override
    public BigInteger countPaths(ReactorGraph graph, String start, String end) {
        Map<String, BigInteger> memo = new HashMap<>();
        Map<String, State> state = new HashMap<>();
        return countFrom(graph, start, end, memo, state);
    }

    private BigInteger countFrom(
            ReactorGraph graph,
            String node,
            String end,
            Map<String, BigInteger> memo,
            Map<String, State> state
    ) {
        if (node.equals(end)) return BigInteger.ONE;
        if (memo.containsKey(node)) return memo.get(node);

        enter(state, node);
        BigInteger total = graph.nextOf(node).stream()
                .map(next -> countFrom(graph, next, end, memo, state))
                .reduce(BigInteger.ZERO, BigInteger::add);
        exit(state, node);

        memo.put(node, total);
        return total;
    }

    private void enter(Map<String, State> state, String node) {
        State current = state.getOrDefault(node, State.NEW);
        if (current == State.VISITING) {
            throw new IllegalStateException("Cycle detected involving node: " + node);
        }
        state.put(node, State.VISITING);
    }

    private void exit(Map<String, State> state, String node) {
        state.put(node, State.DONE);
    }

    private enum State { NEW, VISITING, DONE }
}
