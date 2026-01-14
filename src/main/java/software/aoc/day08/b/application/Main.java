package software.aoc.day08.b.application;

import software.aoc.day08.b.application.control.UnifiedCircuitSolver;
import software.aoc.day08.b.application.persistence.deserialization.JunctionBoxParser;
import software.aoc.day08.b.application.persistence.io.PlainTextReader;
import software.aoc.day08.b.domain.model.JunctionBox;
import software.aoc.day08.b.domain.service.UnifiedCircuitService;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final String INPUT_FILE = "src/main/resources/day08/inputs.txt";

    public static void main(String[] args) {
        System.out.println("===== UNIFICACIÓN DE CIRCUITO ÚNICO =====\n");

        try {
            // 1. Cargar junction boxes
            System.out.println("Cargando junction boxes...");
            List<JunctionBox> boxes = loadJunctionBoxes(INPUT_FILE);

            System.out.println("Total junction boxes: " + boxes.size());
            System.out.println("Objetivo: conectar todas en un solo circuito\n");

            // 2. Resolver
            UnifiedCircuitService solver = new UnifiedCircuitSolver();

            long startTime = System.currentTimeMillis();
            UnifiedCircuitService.Result result = solver.connectUntilUnified(boxes);
            long endTime = System.currentTimeMillis();

            // 3. Mostrar resultado
            System.out.println("\n" + "=".repeat(60));
            System.out.println("RESULTADO FINAL");
            System.out.println("=".repeat(60));
            System.out.printf("Producto de coordenadas X: %,d%n", result.xCoordinateProduct());
            System.out.println("Tiempo total: " + (endTime - startTime) + "ms");

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static List<JunctionBox> loadJunctionBoxes(String filePath) {
        PlainTextReader reader = new PlainTextReader(filePath);
        JunctionBoxParser parser = new JunctionBoxParser();

        List<String> lines = reader.read().collect(Collectors.toList());
        return parser.parse(lines);
    }
}
