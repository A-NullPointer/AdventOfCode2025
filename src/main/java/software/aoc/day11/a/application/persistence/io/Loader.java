package software.aoc.day11.a.application.persistence.io;

import software.aoc.day11.a.domain.persistence.deserialization.Parser;
import software.aoc.day11.a.domain.persistence.io.Reader;

import java.util.List;

public class Loader<T> {

    private final Reader reader;
    private final Parser<T> parser;

    public Loader(Reader reader, Parser<T> parser) {
        this.reader = reader;
        this.parser = parser;
    }

    public List<T> load() {
        return parser.parse(reader.read().toList());
    }
}
