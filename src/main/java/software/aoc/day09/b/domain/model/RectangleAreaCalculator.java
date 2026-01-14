package software.aoc.day09.b.domain.model;

import software.aoc.day09.b.domain.model.AreaCalculator;
import software.aoc.day09.b.domain.model.Point;
import software.aoc.day09.b.domain.model.Square;

public class RectangleAreaCalculator implements AreaCalculator {

    @Override
    public long calculateArea(Point p1, Point p2) {
        return new Square(p1, p2).calculateArea(p1, p2);
    }
}
