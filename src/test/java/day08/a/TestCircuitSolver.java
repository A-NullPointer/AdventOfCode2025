package day08.a;

import software.aoc.day08.a.application.control.GraphCircuitSolver;
import software.aoc.day08.a.application.control.StreamCircuitSolver;
import software.aoc.day08.a.domain.model.JunctionBox;
import software.aoc.day08.a.domain.service.CircuitAnalysisService;

import java.util.Arrays;
import java.util.List;

public class TestCircuitSolver {

    public static void main(String[] args) {
        System.out.println("===== TEST CON EJEMPLO DEL ENUNCIADO =====\n");

        // Datos del ejemplo
        List<JunctionBox> exampleBoxes = Arrays.asList(
                new JunctionBox(162, 817, 812),
                new JunctionBox(57, 618, 57),
                new JunctionBox(906, 360, 560),
                new JunctionBox(592, 479, 940),
                new JunctionBox(352, 342, 300),
                new JunctionBox(466, 668, 158),
                new JunctionBox(542, 29, 236),
                new JunctionBox(431, 825, 988),
                new JunctionBox(739, 650, 466),
                new JunctionBox(52, 470, 668),
                new JunctionBox(216, 146, 977),
                new JunctionBox(819, 987, 18),
                new JunctionBox(117, 168, 530),
                new JunctionBox(805, 96, 715),
                new JunctionBox(346, 949, 466),
                new JunctionBox(970, 615, 88),
                new JunctionBox(941, 993, 340),
                new JunctionBox(862, 61, 35),
                new JunctionBox(984, 92, 344),
                new JunctionBox(425, 690, 689)
        );

        int connections = 10;
        long expectedResult = 40;

        System.out.println("Junction Boxes: " + exampleBoxes.size());
        System.out.println("Conexiones a realizar: " + connections);
        System.out.println("Resultado esperado: " + expectedResult);
        System.out.println();

        // Test con StreamCircuitSolver
        testSolver("StreamCircuitSolver", new StreamCircuitSolver(), exampleBoxes, connections, expectedResult);

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Test con GraphCircuitSolver
        testSolver("GraphCircuitSolver", new GraphCircuitSolver(), exampleBoxes, connections, expectedResult);

        System.out.println("\n" + "=".repeat(60) + "\n");

        // Verificar distancias del ejemplo
        System.out.println("===== VERIFICACIÓN DE DISTANCIAS =====\n");
        verifyExampleDistances(exampleBoxes);
    }

    private static void testSolver(String name, CircuitAnalysisService solver,
                                   List<JunctionBox> boxes, int connections,
                                   long expectedResult) {
        System.out.println("--- Test con " + name + " ---");

        try {
            long startTime = System.currentTimeMillis();
            long result = solver.analyzeCircuits(boxes, connections);
            long endTime = System.currentTimeMillis();

            System.out.println("\nResultado obtenido: " + result);
            System.out.println("Resultado esperado: " + expectedResult);
            System.out.println("Tiempo: " + (endTime - startTime) + "ms");

            if (result == expectedResult) {
                System.out.println("✓ TEST PASADO!");
            } else {
                System.out.println("✗ TEST FALLIDO!");
                System.out.println("Diferencia: " + (result - expectedResult));
            }

        } catch (Exception e) {
            System.out.println("✗ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void verifyExampleDistances(List<JunctionBox> boxes) {
        // Verificar las distancias mencionadas en el enunciado

        // "the two junction boxes which are closest together are 162,817,812 and 425,690,689"
        JunctionBox box1 = boxes.get(0);  // 162,817,812
        JunctionBox box20 = boxes.get(19); // 425,690,689

        double dist1 = Math.sqrt(box1.distanceSquared(box20));
        System.out.println("Distancia entre 162,817,812 y 425,690,689: " + dist1);

        // "the two junction boxes which are closest together but aren't already directly connected
        // are 162,817,812 and 431,825,988"
        JunctionBox box8 = boxes.get(7);  // 431,825,988
        double dist2 = Math.sqrt(box1.distanceSquared(box8));
        System.out.println("Distancia entre 162,817,812 y 431,825,988: " + dist2);

        // "The next two junction boxes to connect are 906,360,560 and 805,96,715"
        JunctionBox box3 = boxes.get(2);  // 906,360,560
        JunctionBox box14 = boxes.get(13); // 805,96,715
        double dist3 = Math.sqrt(box3.distanceSquared(box14));
        System.out.println("Distancia entre 906,360,560 y 805,96,715: " + dist3);

        System.out.println();

        // Encontrar el par más cercano
        double minDist = Double.MAX_VALUE;
        int minI = -1, minJ = -1;

        for (int i = 0; i < boxes.size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                double dist = boxes.get(i).distanceSquared(boxes.get(j));
                if (dist < minDist) {
                    minDist = dist;
                    minI = i;
                    minJ = j;
                }
            }
        }

        System.out.println("Par más cercano encontrado:");
        System.out.println("  Box " + (minI + 1) + ": " + boxes.get(minI));
        System.out.println("  Box " + (minJ + 1) + ": " + boxes.get(minJ));
        System.out.println("  Distancia: " + Math.sqrt(minDist));
    }
}