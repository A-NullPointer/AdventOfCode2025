package software.aoc.day05.b.application.persistence.deserialization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class SpliterByBlankLine {

    private List<String> IdRanges;
    private List<String> IngredientIds;
    private boolean blankLineFound;

    public SpliterByBlankLine() {
        IdRanges = new ArrayList<>();
        IngredientIds = new ArrayList<>();
        blankLineFound = false;
    }

    public List<String> IdRanges() {
        return Collections.unmodifiableList(IdRanges);
    }

    public List<String> IngredientIds() {
        return Collections.unmodifiableList(IngredientIds);
    }

//    public void split(Stream<String> lines) {
//        lines.takeWhile(line -> !blankLineFound)
//                .forEach(line -> IdRanges.add(line));
//
//        lines.dropWhile(line -> !blankLineFound)
//                .forEach(line -> IngredientIds.add(line));
//    }

    public void split(Stream<String> lines) {
        lines.forEach(line -> classifyLine(line));
    }

    private void classifyLine(String line) {
        if (line.trim().isEmpty()) {
            blankLineFound = true;
            return;
        }
        addLineToCorrectObjectList(line);
    }

    private void addLineToCorrectObjectList(String line) {
        if (!blankLineFound) {
            IdRanges.add(line);
        } else {
            IngredientIds.add(line);
        }
    }
}
