package software.aoc.day04.b;

import software.aoc.day04.b.application.controller.SolutionCalculator;
import software.aoc.day04.b.application.factory.RollSolverFactory;
import software.aoc.day04.b.application.persistence.deserialization.RollPositionsParser;
import software.aoc.day04.b.application.persistence.io.Loader;
import software.aoc.day04.b.application.persistence.io.PlainTextReader;
import software.aoc.day04.b.application.service.Part1AccessibleSolver;
import software.aoc.day04.b.application.service.Part2CascadeRemovalSolver;
import software.aoc.day04.b.domain.model.Pos;
import software.aoc.day04.b.domain.persistence.deserialization.Parser;
import software.aoc.day04.b.domain.persistence.io.Reader;
import software.aoc.day04.b.domain.service.RollSolver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// ... imports
public class Main {
    public static void main(String[] args) {
        String filePath = args.length >= 1 ? args[0] : "src/main/resources/day04/inputs.txt";

        Reader reader = new PlainTextReader(filePath);
        Parser<List<Pos>> parser = new RollPositionsParser();
        Loader<List<Pos>> loader = new Loader<>(reader, parser);

        Set<Pos> rolls = new HashSet<>(loader.loadFirst());

        RollSolver solver = new Part2CascadeRemovalSolver(); // SOLO el segundo
        int answer = solver.solve(rolls);

        System.out.println(answer);
    }
}
