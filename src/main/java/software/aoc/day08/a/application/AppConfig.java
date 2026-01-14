package software.aoc.day08.a.application;


import software.aoc.day08.a.application.control.GraphCircuitSolver;
import software.aoc.day08.a.application.persistence.io.JunctionBoxLoader;
import software.aoc.day08.a.application.persistence.repository.FileJunctionBoxRepository;
import software.aoc.day08.a.domain.repository.JunctionBoxRepository;
import software.aoc.day08.a.domain.service.CircuitAnalysisService;

public class AppConfig {

    public static JunctionBoxLoader junctionBoxLoader(String filePath) {
        JunctionBoxRepository repository = new FileJunctionBoxRepository(filePath);
        return new JunctionBoxLoader(repository);
    }

    public static CircuitAnalysisService circuitAnalysisService() {
        return new GraphCircuitSolver();
    }
}
