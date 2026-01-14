package software.aoc.day07.b.application;

import software.aoc.day07.b.application.persistence.deserialization.ManifoldParser;
import software.aoc.day07.b.domain.control.QuantumBeamSimulator;
import software.aoc.day07.b.domain.model.Manifold;
import software.aoc.day07.b.domain.persistence.deserialization.Parser;
import software.aoc.day07.b.domain.persistence.io.Reader;

public class Main {

    public static void main(String[] args) {
        String filePath = args.length >= 1
                ? args[0]
                : "src/main/resources/day07/inputs.txt";

        Reader reader = new software.aoc.day07.b.application.persistence.io.PlainTextReader(filePath);
        Parser<Manifold> parser = new ManifoldParser();

        Manifold manifold = parser.parse(reader.read().toList());

        long timelines = new QuantumBeamSimulator(manifold).simulate();
        System.out.println(timelines);
    }
}
