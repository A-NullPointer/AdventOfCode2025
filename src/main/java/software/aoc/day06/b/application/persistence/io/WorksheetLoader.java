package software.aoc.day06.b.application.persistence.io;

import software.aoc.day06.b.application.control.Worksheet;
import software.aoc.day06.b.domain.persistence.deserialization.Parser;
import software.aoc.day06.b.domain.persistence.io.Reader;

public class WorksheetLoader {

    public static Worksheet load(Reader reader, Parser<Worksheet> parser) {
        return parser.parse(reader.read().toList());
    }
}