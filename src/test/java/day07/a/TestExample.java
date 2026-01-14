package day07.a;

import software.aoc.day07.a.application.persistence.deserialization.ManifoldParser;
import software.aoc.day07.a.domain.control.BeamSimulator;
import software.aoc.day07.a.domain.model.Manifold;

import java.util.List;

public class TestExample {

    public static void main(String[] args) {
        List<String> exampleLines = List.of(
                ".......S.......",
                "...............",
                ".......^.......",
                "...............",
                "......^.^......",
                "...............",
                ".....^.^.^.....",
                "...............",
                "....^.^...^....",
                "...............",
                "...^.^...^.^...",
                "...............",
                "..^...^.....^..",
                "...............",
                ".^.^.^.^.^...^.",
                "..............."
        );

        System.out.println("===== MANIFOLD =====");
        for (int i = 0; i < exampleLines.size(); i++) {
            System.out.printf("%2d: %s%n", i, exampleLines.get(i));
        }
        System.out.println();

        ManifoldParser parser = new ManifoldParser();
        Manifold manifold = parser.parse(exampleLines);

        System.out.println("Dimensiones: " + manifold.getRows() + "x" + manifold.getCols());
        System.out.println("Start: " + manifold.getStartPosition());
        System.out.println();

        // ACTIVAR DEBUG
        BeamSimulator simulator = new BeamSimulator(manifold, true);
        int splits = simulator.simulate();

        System.out.println("\n===== RESULTADO =====");
        System.out.println("Splits detectados: " + splits);
        System.out.println("Esperado: 21");
    }
}
