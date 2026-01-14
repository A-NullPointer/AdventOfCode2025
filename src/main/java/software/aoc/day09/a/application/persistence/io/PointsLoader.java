package software.aoc.day09.a.application.persistence.io;

import software.aoc.day09.a.domain.model.Point;
import software.aoc.day09.a.domain.persistence.deserialization.Parser;

import java.util.List;

public class PointsLoader {

    private PlainTextReader reader;
    private Parser pointParser;
    public PointsLoader(PlainTextReader reader, Parser pointParser) {
        this.reader = reader;
        this.pointParser = pointParser;
    }

    public List<Point> loadPoints() {
        return pointParser.parse(reader.read());
    }

}
