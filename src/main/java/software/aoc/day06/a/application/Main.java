package software.aoc.day06.a.application;

import software.aoc.day06.a.application.control.Worksheet;
import software.aoc.day06.a.application.persistence.deserialization.WorksheetParser;
import software.aoc.day06.a.application.persistence.io.PlainTextReader;

public class Main {

    public static void main(String[] args) {
        try {
            String filePath = "C:\\Users\\asmae\\Documents\\INGENIERIA INFORMATICA\\3 TERCERO\\IS2\\2025-26\\AdventOfCode2025\\AdventOfCode2025\\src\\main\\resources\\day06\\inputs.txt";

            PlainTextReader reader = new PlainTextReader(filePath);
            WorksheetParser parser = new WorksheetParser();

            Worksheet worksheet = parser.parse(reader.read().toList());

            long total = worksheet.calculateTotal();
            System.out.println("Total: " + total);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
