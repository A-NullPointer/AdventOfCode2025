package software.aoc.day02.a.application;

import software.aoc.day02.a.application.validator.RepeatedPatternIdValidator;
import software.aoc.day02.a.application.control.Solver;
import software.aoc.day02.a.domain.persistence.io.IdRangeReader;
import software.aoc.day02.a.application.persitence.io.PlainTextIdRangeReader;
import software.aoc.day02.a.domain.persistence.deserialization.IdRangeParser;
import software.aoc.day02.a.application.persitence.deserialization.PlainTextIdRangeParser;
import software.aoc.day02.a.domain.validators.IdValidator;

public class Main {
    public static void main(String[] args) {

       String path = "C:/Users/asmae/Documents/INGENIERIA INFORMATICA/3 TERCERO/IS2/2025-26/AdventOfCode2025/AdventOfCode2025/src/main/resources/day02/inputs.txt";

        IdRangeParser parser = new PlainTextIdRangeParser();
        IdRangeReader reader = new PlainTextIdRangeReader(path, parser);
        IdValidator validator = new RepeatedPatternIdValidator();

        Solver solver = new Solver(reader, validator);

        System.out.println("Resultado de la suma de IDs inválidos es: " + solver.solve());
    }
}
