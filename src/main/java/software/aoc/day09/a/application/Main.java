package software.aoc.day09.a.application;

import software.aoc.day09.a.application.control.MovieTheaterSolver;
import software.aoc.day09.a.application.persistence.deserialization.PointParser;
import software.aoc.day09.a.application.persistence.io.PlainTextReader;
import software.aoc.day09.a.application.persistence.io.PointsLoader;
import software.aoc.day09.a.domain.model.RectangleAreaCalculator;

public class Main {
    public static void main(String[] args) {
        String path = "C:\\Users\\asmae\\Documents\\INGENIERIA INFORMATICA\\3 TERCERO\\IS2\\2025-26\\AdventOfCode2025\\AdventOfCode2025\\src\\main\\resources\\day09\\inputs.txt";

        PlainTextReader plainTextReader = new PlainTextReader(path);
        PointParser pointParser = new PointParser();
        PointsLoader pointsLoader = new PointsLoader(plainTextReader, pointParser);

        RectangleAreaCalculator rectangleAreaCalculator = new RectangleAreaCalculator();

        MovieTheaterSolver movieTheaterSolver = new MovieTheaterSolver(rectangleAreaCalculator, pointsLoader);

        long largestRectangle = movieTheaterSolver.findLargestRectangle();

        System.out.println(largestRectangle);
    }
}
