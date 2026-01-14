package software.aoc.day10.b.application;

import software.aoc.day10.b.application.service.JoltageAStarMinPressSolver;
import software.aoc.day10.b.domain.model.Machine;
import software.aoc.day10.b.domain.persistence.deserialization.Parser;
import software.aoc.day10.b.domain.persistence.io.Reader;
import software.aoc.day10.b.domain.service.MachineSolver;

import java.util.List;

import software.aoc.day09.b.application.control.ConstrainedRectangleSolver;
import software.aoc.day09.b.application.persistence.deserialization.PointParser;
import software.aoc.day09.b.application.persistence.io.PlainTextReader;
import software.aoc.day09.b.application.persistence.io.PointsLoader;

public class Main {

    public static void main(String[] args) {

        String path = args.length >= 1
                ? args[0]
                : "src/main/resources/day09/inputs.txt";

        PlainTextReader reader = new PlainTextReader(path);
        PointParser parser = new PointParser();
        PointsLoader loader = new PointsLoader(reader, parser);

        ConstrainedRectangleSolver solver = new ConstrainedRectangleSolver(loader.loadPoints());

        long answer = solver.findLargestConstrainedRectangle();
        System.out.println(answer);
    }
}
