package software.aoc.day07.a.application;

import software.aoc.day07.a.application.persistence.deserialization.ManifoldParser;
import software.aoc.day07.a.application.persistence.io.PlainTextReader;
import software.aoc.day07.a.domain.control.BeamSimulator;
import software.aoc.day07.a.domain.model.Manifold;

public class Main {

    private static final String INPUT_FILE = "src/main/resources/day07/inputs.txt";

    public static void main(String[] args) {
        try {
            // Cargar manifold
            PlainTextReader reader = new PlainTextReader(INPUT_FILE);
            ManifoldParser parser = new ManifoldParser();

            Manifold manifold = parser.parse(reader.read().toList());

            System.out.println("Manifold cargado:");
            System.out.println("  Dimensiones: " + manifold.getRows() + "x" + manifold.getCols());
            System.out.println("  Posición inicial: " + manifold.getStartPosition());
            System.out.println();

            // Simular
            BeamSimulator simulator = new BeamSimulator(manifold);
            int splits = simulator.simulate();

            System.out.println("Resultado:");
            System.out.println("  Total de divisiones: " + splits);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
