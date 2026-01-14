package software.aoc.day12.a.application;

import software.aoc.day12.a.application.persistence.deserialization.Day12Parser;
import software.aoc.day12.a.application.persistence.io.Loader;
import software.aoc.day12.a.application.persistence.io.PlainTextReader;
import software.aoc.day12.a.application.service.AreaOnlyRegionChecker;
import software.aoc.day12.a.domain.model.PuzzleInput;
import software.aoc.day12.a.domain.persistence.deserialization.Parser;
import software.aoc.day12.a.domain.persistence.io.Reader;

public class Main {

    public static void main(String[] args) {
        String filePath = args.length >= 1 ? args[0] : "src/main/resources/day12/inputs.txt";

        Reader reader = new PlainTextReader(filePath);
        Parser<PuzzleInput> parser = new Day12Parser();
        Loader<PuzzleInput> loader = new Loader<>(reader, parser);

        PuzzleInput input = loader.loadFirst();

        long answer = new AreaOnlyRegionChecker().countFittableRegions(input);
        System.out.println(answer);
    }
}
