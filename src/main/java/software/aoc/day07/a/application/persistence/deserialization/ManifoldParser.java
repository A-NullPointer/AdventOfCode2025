package software.aoc.day07.a.application.persistence.deserialization;

import software.aoc.day07.a.domain.model.Manifold;
import software.aoc.day07.a.domain.persistence.deserialization.Parser;

import java.util.List;

/**
 * Parser para archivos de manifold
 */
public class ManifoldParser implements Parser<Manifold> {

    @Override
    public Manifold parse(List<String> lines) {
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty");
        }

        // Validar que todas las líneas tengan el mismo ancho
        int expectedWidth = lines.get(0).length();
        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i).length() != expectedWidth) {
                throw new IllegalArgumentException(
                        "All lines must have the same width. Line " + i +
                                " has " + lines.get(i).length() + " chars, expected " + expectedWidth
                );
            }
        }

        return new Manifold.Builder().fromLines(lines);
    }
}
