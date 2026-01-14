package software.aoc.day04.a.application.persistence.io;

import software.aoc.day04.a.domain.persistence.deserialization.Parser;
import software.aoc.day04.a.domain.persistence.io.Reader;

import java.util.List;

public class Loader<T> {

    private final Reader reader;
    private final Parser<T> parser;

    public Loader(Reader reader, Parser<T> parser) {
        this.reader = reader;
        this.parser = parser;
    }

    public T loadFirst() {
        return parser.parse(reader.read().toList()).getFirst();
    }
}
