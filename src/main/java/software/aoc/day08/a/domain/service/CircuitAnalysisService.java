// Domain Service Interface
package software.aoc.day08.a.domain.service;

import software.aoc.day08.a.domain.model.JunctionBox;

import java.util.List;

public interface CircuitAnalysisService {
    long analyzeCircuits(List<JunctionBox> boxes, int connectionsToMake);
}