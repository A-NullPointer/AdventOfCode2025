package software.aoc.day12.a.application.persistence.deserialization;

import software.aoc.day12.a.domain.model.PuzzleInput;
import software.aoc.day12.a.domain.model.RegionSpec;
import software.aoc.day12.a.domain.model.Shape;
import software.aoc.day12.a.domain.persistence.deserialization.Parser;

import java.util.ArrayList;
import java.util.List;

public class Day12Parser implements Parser<PuzzleInput> {

    @Override
    public List<PuzzleInput> parse(List<String> lines) {
        List<List<String>> blocks = splitByBlankLine(lines);

        int regionBlockIndex = blocks.size() - 1;
        List<String> regionLines = blocks.get(regionBlockIndex);

        List<Shape> shapes = blocks.subList(0, regionBlockIndex).stream()
                .map(this::parseShapeBlock)
                .toList();

        List<RegionSpec> regions = regionLines.stream()
                .filter(l -> !l.isBlank())
                .map(this::parseRegionLine)
                .toList();

        return List.of(new PuzzleInput(shapes, regions));
    }

    private Shape parseShapeBlock(List<String> block) {
        String header = block.getFirst().trim();     // e.g. "0:"
        int id = Integer.parseInt(header.substring(0, header.length() - 1));
        List<String> rows = block.subList(1, block.size()).stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
        return new Shape(id, rows);
    }

    private RegionSpec parseRegionLine(String line) {
        String[] parts = line.split(":");
        String[] wh = parts[0].trim().split("x");

        int w = Integer.parseInt(wh[0]);
        int h = Integer.parseInt(wh[1]);

        List<Integer> qty = parts.length < 2
                ? List.of()
                : List.of(parts[1].trim().split("\\s+")).stream().map(Integer::parseInt).toList();

        return new RegionSpec(w, h, qty);
    }

    private List<List<String>> splitByBlankLine(List<String> lines) {
        List<List<String>> blocks = new ArrayList<>();
        List<String> current = new ArrayList<>();

        lines.forEach(l -> {
            if (l.trim().isEmpty()) {
                flush(blocks, current);
            } else {
                current.add(l);
            }
        });

        flush(blocks, current);
        return List.copyOf(blocks);
    }

    private void flush(List<List<String>> blocks, List<String> current) {
        if (!current.isEmpty()) {
            blocks.add(List.copyOf(current));
            current.clear();
        }
    }
}
