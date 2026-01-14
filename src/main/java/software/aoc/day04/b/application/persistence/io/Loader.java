package software.aoc.day04.b.application.persistence.io;

import software.aoc.day04.b.domain.persistence.deserialization.Parser;
import software.aoc.day04.b.domain.persistence.io.Reader;

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
