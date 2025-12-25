package software.aoc.day02.a;

import software.aoc.day02.a.application.InvalidIDIdentefier;
import software.aoc.day02.a.deserialization.PlainTextIdRangeParser;
import software.aoc.day02.a.io.PlainTextIdRangeReader;
import software.aoc.day02.a.model.IdRange;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

       String path = "C:/Users/asmae/Documents/INGENIERIA INFORMATICA/3 TERCERO/IS2/2025-26/AdventOfCode2025/AdventOfCode2025/src/main/resources/day02/a/inputs.txt";
        List<IdRange> idRanges = new PlainTextIdRangeReader(path).read();

        InvalidIDIdentefier invalidIDIdentefier = new InvalidIDIdentefier(idRanges);
        Optional<Long> resullt = invalidIDIdentefier.identifyInvalidIds();
        System.out.println("resultado de IDs inválido es: " + resullt);
    }
}
