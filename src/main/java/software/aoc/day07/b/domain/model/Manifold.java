package software.aoc.day07.b.domain.model;

import software.aoc.day07.b.domain.model.Cell;
import software.aoc.day07.b.domain.model.Position;

import java.util.List;
import java.util.Optional;

/**
 * Representa la cuadrícula del manifold
 * Single Responsibility: Solo gestiona la estructura de la cuadrícula
 */
public class Manifold {
    private final software.aoc.day07.b.domain.model.Cell[][] grid;
    private final int rows;
    private final int cols;
    private final Position startPosition;

    private Manifold(software.aoc.day07.b.domain.model.Cell[][] grid, Position startPosition) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.startPosition = startPosition;
    }

    public Optional<software.aoc.day07.b.domain.model.Cell> getCell(Position position) {
        if (!position.isValid(rows, cols)) {
            return Optional.empty();
        }
        return Optional.of(grid[position.row()][position.col()]);
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * Builder Pattern para construcción de Manifold
     */
    public static class Builder {

        public Manifold fromLines(List<String> lines) {
            if (lines.isEmpty()) {
                throw new IllegalArgumentException("Lines cannot be empty");
            }

            int rows = lines.size();
            int cols = lines.get(0).length();
            software.aoc.day07.b.domain.model.Cell[][] grid = new software.aoc.day07.b.domain.model.Cell[rows][cols];
            Position startPos = null;

            for (int row = 0; row < rows; row++) {
                String line = lines.get(row);
                for (int col = 0; col < line.length(); col++) {
                    char symbol = line.charAt(col);
                    grid[row][col] = Cell.fromSymbol(symbol);

                    if (symbol == 'S') {
                        startPos = new Position(row, col);
                    }
                }
            }

            if (startPos == null) {
                throw new IllegalArgumentException("No start position 'S' found");
            }

            return new Manifold(grid, startPos);
        }
    }
}
