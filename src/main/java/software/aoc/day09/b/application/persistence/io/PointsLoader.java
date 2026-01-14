package software.aoc.day09.b.application.persistence.io;

import software.aoc.day09.b.application.persistence.io.PlainTextReader;
import software.aoc.day09.b.domain.model.Point;
import software.aoc.day09.b.domain.persistence.deserialization.Parser;

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
