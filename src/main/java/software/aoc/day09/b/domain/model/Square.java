package software.aoc.day09.b.domain.model;

import software.aoc.day09.b.domain.model.AreaCalculator;
import software.aoc.day09.b.domain.model.Point;

public record Square(Point startCorner, Point endCorner) implements AreaCalculator {
    @Override
    public long calculateArea(software.aoc.day09.b.domain.model.Point p1, Point p2) {
        int width = Math.abs(endCorner.x() - startCorner.x()) + 1;
        int height = Math.abs(endCorner.y() - startCorner.y()) + 1;
        return (long) width * height;
    }
}
