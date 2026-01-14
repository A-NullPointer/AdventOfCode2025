package software.aoc.day09.a.domain.model;

import software.aoc.day09.a.domain.model.Point;

public class RectangleAreaCalculator implements AreaCalculator {

    @Override
    public long calculateArea(Point p1, Point p2) {
        return new Square(p1, p2).calculateArea(p1, p2);
    }
}
