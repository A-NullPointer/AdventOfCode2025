package software.aoc.day10.a.application;

import software.aoc.day10.a.application.controller.SolutionCalculator;
import software.aoc.day10.a.application.factory.MachineSolverFactory;
import software.aoc.day10.a.application.persistence.deserialization.MachineLineTokenizer;
import software.aoc.day10.a.application.persistence.deserialization.MachineParser;
import software.aoc.day10.a.application.persistence.io.Loader;
import software.aoc.day10.a.application.persistence.io.PlainTextReader;
import software.aoc.day10.a.application.service.MeetInMiddleMinPressSolver;
import software.aoc.day10.a.domain.model.Machine;
import software.aoc.day10.a.domain.persistence.deserialization.Parser;
import software.aoc.day10.a.domain.persistence.io.Reader;
import software.aoc.day10.a.domain.service.MachineSolver;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        int part = args.length >= 1 ? Integer.parseInt(args[0]) : 1;
        String filePath = args.length >= 2 ? args[1] : "src/main/resources/day10/inputs.txt";

        Reader reader = new PlainTextReader(filePath);
        Parser parser = new MachineParser(new MachineLineTokenizer());

        Loader loader = new Loader(reader, parser);

        List<Machine> machines = loader.obtainMachines();

        MachineSolver part1Solver = new MeetInMiddleMinPressSolver();
        MachineSolver part2Solver = part1Solver; // mientras shakes se ignoran, part2 == part1

        MachineSolverFactory factory = new MachineSolverFactory(part1Solver, part2Solver);
        SolutionCalculator calculator = new SolutionCalculator(factory.forPart(part));

        int answer = calculator.calculateTotalMinimumPresses(machines);
        System.out.println(answer);
    }
}
