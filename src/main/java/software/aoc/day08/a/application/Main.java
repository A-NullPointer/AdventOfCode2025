package software.aoc.day08.a.application;

import software.aoc.day08.a.application.control.GraphCircuitSolver;
import software.aoc.day08.a.application.control.StreamCircuitSolver;
import software.aoc.day08.a.application.persistence.deserialization.JunctionBoxParser;
import software.aoc.day08.a.application.persistence.io.PlainTextReader;
import software.aoc.day08.a.domain.model.JunctionBox;
import software.aoc.day08.a.domain.service.CircuitAnalysisService;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final String INPUT_FILE = "src/main/resources/day08/inputs.txt";
    private static final int CONNECTIONS_TO_MAKE = 1000;

    public static void main(String[] args) {
        System.out.println("===== ANÁLISIS DE CIRCUITOS DE JUNCTION BOXES =====\n");

        try {
            // 1. Cargar junction boxes
            System.out.println("Cargando junction boxes desde: " + INPUT_FILE);
            List<JunctionBox> boxes = loadJunctionBoxes(INPUT_FILE);

            System.out.println("Total junction boxes: " + boxes.size());
            System.out.println("Conexiones a procesar: " + CONNECTIONS_TO_MAKE);
            System.out.println();

            // 2. Resolver con el solver seleccionado
            CircuitAnalysisService solver = selectSolver(args);

            System.out.println("===== INICIANDO ANÁLISIS =====\n");
            long startTime = System.currentTimeMillis();

            long result = solver.analyzeCircuits(boxes, CONNECTIONS_TO_MAKE);

            long endTime = System.currentTimeMillis();

            // 3. Mostrar resultado
            System.out.println("\n===== RESULTADO FINAL =====");
            System.out.println("Multiplicación de los 3 circuitos más grandes: " + result);
            System.out.println("Tiempo total: " + (endTime - startTime) + "ms");

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Carga las junction boxes desde el archivo
     */
    private static List<JunctionBox> loadJunctionBoxes(String filePath) {
        PlainTextReader reader = new PlainTextReader(filePath);
        JunctionBoxParser parser = new JunctionBoxParser();

        List<String> lines = reader.read().collect(Collectors.toList());
        return parser.parse(lines);
    }

    /**
     * Selecciona el solver según los argumentos de línea de comandos
     */
    private static CircuitAnalysisService selectSolver(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("stream")) {
            System.out.println("Usando: StreamCircuitSolver\n");
            return new StreamCircuitSolver();
        } else if (args.length > 0 && args[0].equalsIgnoreCase("graph")) {
            System.out.println("Usando: GraphCircuitSolver\n");
            return new GraphCircuitSolver();
        } else {
            // Por defecto usar GraphCircuitSolver (más eficiente)
            System.out.println("Usando: GraphCircuitSolver (por defecto)\n");
            System.out.println("Tip: Usa 'stream' o 'graph' como argumento para elegir el solver\n");
            return new GraphCircuitSolver();
        }
    }
}
