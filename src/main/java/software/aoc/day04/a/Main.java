package software.aoc.day04.a;

import software.aoc.day04.a.application.controller.SolutionCalculator;
import software.aoc.day04.a.application.factory.RollSolverFactory;
import software.aoc.day04.a.application.persistence.deserialization.RollPositionsParser;
import software.aoc.day04.a.application.persistence.io.Loader;
import software.aoc.day04.a.application.persistence.io.PlainTextReader;
import software.aoc.day04.a.application.service.Part1AccessibleSolver;
import software.aoc.day04.a.application.service.Part2CascadeRemovalSolver;
import software.aoc.day04.a.domain.model.Pos;
import software.aoc.day04.a.domain.persistence.deserialization.Parser;
import software.aoc.day04.a.domain.persistence.io.Reader;
import software.aoc.day04.a.domain.service.RollSolver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        int part = args.length >= 1 ? Integer.parseInt(args[0]) : 1;
        String filePath = args.length >= 2 ? args[1] : "src/main/resources/day04/inputs.txt";

        Reader reader = new PlainTextReader(filePath);
        Parser<List<Pos>> parser = new RollPositionsParser();
        Loader<List<Pos>> loader = new Loader<>(reader, parser);

        Set<Pos> rolls = new HashSet<>(loader.loadFirst());

        RollSolverFactory factory = new RollSolverFactory(
                new Part1AccessibleSolver(),
                new Part2CascadeRemovalSolver()
        );

        RollSolver solver = factory.forPart(part);
        SolutionCalculator calculator = new SolutionCalculator(solver);

        System.out.println(calculator.solve(rolls));
    }
}
