package software.aoc.day11.a.application;

import software.aoc.day11.a.application.controller.SolutionCalculator;
import software.aoc.day11.a.application.persistence.deserialization.GraphParser;
import software.aoc.day11.a.application.persistence.io.Loader;
import software.aoc.day11.a.application.persistence.io.PlainTextReader;
import software.aoc.day11.a.application.service.MemoizedDfsPathCounter;
import software.aoc.day11.a.domain.model.ReactorGraph;
import software.aoc.day11.a.domain.persistence.deserialization.Parser;
import software.aoc.day11.a.domain.persistence.io.Reader;
import software.aoc.day11.a.domain.service.PathCounter;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        String filePath = args.length >= 1 ? args[0] : "C:\\Users\\asmae\\Documents\\INGENIERIA INFORMATICA\\3 TERCERO\\IS2\\2025-26\\AdventOfCode2025\\AdventOfCode2025\\src\\main\\resources\\day11\\inputs.txt";

        Reader reader = new PlainTextReader(filePath);
        Parser<ReactorGraph> parser = new GraphParser();
        Loader<ReactorGraph> loader = new Loader<>(reader, parser);

        ReactorGraph graph = loader.load().getFirst();

        PathCounter counter = new MemoizedDfsPathCounter();
        SolutionCalculator calculator = new SolutionCalculator(counter);

        BigInteger answer = calculator.countPathsYouToOut(graph);
        System.out.println(answer);
    }
}
