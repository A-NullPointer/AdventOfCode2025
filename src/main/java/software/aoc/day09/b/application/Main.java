package software.aoc.day09.b.application;

import software.aoc.day09.b.application.control.ConstrainedRectangleSolver;
import software.aoc.day09.b.application.control.MovieTheaterSolver;
import software.aoc.day09.b.application.persistence.deserialization.PointParser;
import software.aoc.day09.b.application.persistence.io.PlainTextReader;
import software.aoc.day09.b.application.persistence.io.PointsLoader;
import software.aoc.day09.b.domain.model.RectangleAreaCalculator;

public class Main {
    public static void main(String[] args) {
        String path = "C:\\Users\\asmae\\Documents\\INGENIERIA INFORMATICA\\3 TERCERO\\IS2\\2025-26\\AdventOfCode2025\\AdventOfCode2025\\src\\main\\resources\\day09\\inputs.txt";

        PlainTextReader plainTextReader = new PlainTextReader(path);
        PointParser pointParser = new PointParser();
        PointsLoader pointsLoader = new PointsLoader(plainTextReader, pointParser);

        RectangleAreaCalculator rectangleAreaCalculator = new RectangleAreaCalculator();

        ConstrainedRectangleSolver constrainedSolver = new ConstrainedRectangleSolver(pointsLoader.loadPoints());
        long largestConstrainedRectangle = constrainedSolver.findLargestConstrainedRectangle();
        System.out.println(largestConstrainedRectangle);
    }
}
