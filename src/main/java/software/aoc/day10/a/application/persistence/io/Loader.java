package software.aoc.day10.a.application.persistence.io;

import software.aoc.day10.a.domain.model.Machine;
import software.aoc.day10.a.domain.persistence.deserialization.Parser;
import software.aoc.day10.a.domain.persistence.io.Reader;

import java.util.List;

public class Loader {

    private final Reader reader;
    private final Parser<Machine> machineParser;

    public Loader(Reader reader, Parser<Machine> machineParser) {
        this.reader = reader;
        this.machineParser = machineParser;
    }

    public List<Machine> obtainMachines() {
        return machineParser.parse(reader.read().toList());
    }
}
