package software.aoc.day09.b.application.persistence.deserialization;
import software.aoc.day09.b.domain.model.Point;
import software.aoc.day09.b.domain.persistence.deserialization.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class PointParser implements Parser<Point> {

    private final List<Point> points;

    public PointParser() {
        this.points = new ArrayList<>();
    }

    private Point obtainPointFrom(String line){
        //refactorizar
        String[] split = line.split(",");
        // mejorar porque la linea es super larga
        return new Point((Integer.parseInt(split[0])), (Integer.parseInt(split[1])));
    }

    @Override
    public List<Point> parse(Stream<String> lines) {
        lines.forEach(l -> points.add(obtainPointFrom(l)));
        return Collections.unmodifiableList(points);
    }
}
