package software.aoc.day11.a.domain.service;

import software.aoc.day11.a.domain.model.ReactorGraph;

import java.math.BigInteger;

public interface PathCounter {
    BigInteger countPaths(ReactorGraph graph, String start, String end);
}
