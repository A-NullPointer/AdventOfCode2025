package software.aoc.day02.b.application;

import software.aoc.day02.b.application.control.Solver;
import software.aoc.day02.b.application.validators.MultipleRepeatedPatternIdValidator;
import software.aoc.day02.b.domain.persistence.deserialization.IdRangeParser;
import software.aoc.day02.b.application.persistence.deserialization.PlainTextIdRangeParser;
import software.aoc.day02.b.domain.persistence.io.IdRangeReader;
import software.aoc.day02.b.application.persistence.io.PlainTextIdRangeReader;
import software.aoc.day02.b.domain.validators.IdValidator;

public class Main {
    public static void main(String[] args) {

       String path = "C:/Users/asmae/Documents/INGENIERIA INFORMATICA/3 TERCERO/IS2/2025-26/AdventOfCode2025/AdventOfCode2025/src/main/resources/day02/inputs.txt";

        IdRangeParser parser = new PlainTextIdRangeParser();
        IdRangeReader reader = new PlainTextIdRangeReader(path, parser);
        IdValidator validator = new MultipleRepeatedPatternIdValidator();

        Solver solver = new Solver(reader, validator);

        System.out.println("Resultado de la suma de IDs inválidos es: " + solver.solve());
    }
}
