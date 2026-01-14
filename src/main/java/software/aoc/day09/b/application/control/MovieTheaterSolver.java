package software.aoc.day09.b.application.control;

import software.aoc.day09.b.application.persistence.io.PointsLoader;
import software.aoc.day09.b.domain.model.AreaCalculator;
import software.aoc.day09.b.domain.model.Point;

import java.util.List;
import java.util.stream.IntStream;

public class MovieTheaterSolver {
    private final AreaCalculator calculator;
    private final PointsLoader pointsLoader;
    private final List<Point> points;

    public MovieTheaterSolver(AreaCalculator calculator,  PointsLoader pointsLoader) {
        this.calculator = calculator;
        this.pointsLoader = pointsLoader;
        this.points = pointsLoader.loadPoints();
    }

    public long findLargestRectangle() {
        return IntStream.range(0, points.size())
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, points.size())
                        .mapToObj(j -> calculator.calculateArea(
                                points.get(i),
                                points.get(j)
                        )))
                .max(Long::compareTo)
                .orElse(0L);
    }
}
