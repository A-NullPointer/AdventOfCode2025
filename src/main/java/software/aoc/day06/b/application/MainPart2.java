package software.aoc.day06.b.application;

import software.aoc.day06.b.application.control.Worksheet;
import software.aoc.day06.b.application.persistence.deserialization.RotatedLeftWorksheetParser;
import software.aoc.day06.b.application.persistence.io.PlainTextReader;
import software.aoc.day06.b.application.persistence.io.WorksheetLoader;
import software.aoc.day06.b.domain.persistence.deserialization.Parser;
import software.aoc.day06.b.domain.persistence.io.Reader;

public class MainPart2 {
    public static void main(String[] args) {
        String filePath = args.length >= 1 ? args[0] : "src/main/resources/day06/inputs.txt";

        Reader reader = new PlainTextReader(filePath);
        Parser parser = new RotatedLeftWorksheetParser();

        Worksheet ws = WorksheetLoader.load(reader, parser);
        System.out.println(ws.calculateTotal());
    }
}
