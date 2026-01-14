package software.aoc.day08.b.application;


import software.aoc.day08.b.application.control.GraphCircuitSolver;
import software.aoc.day08.b.application.persistence.io.JunctionBoxLoader;
import software.aoc.day08.b.application.persistence.repository.FileJunctionBoxRepository;
import software.aoc.day08.b.domain.repository.JunctionBoxRepository;
import software.aoc.day08.b.domain.service.CircuitAnalysisService;

public class AppConfig {

    public static JunctionBoxLoader junctionBoxLoader(String filePath) {
        JunctionBoxRepository repository = new FileJunctionBoxRepository(filePath);
        return new JunctionBoxLoader(repository);
    }

    public static CircuitAnalysisService circuitAnalysisService() {
        return new GraphCircuitSolver();
    }
}
