package software.aoc.day07.b.domain.model;

import software.aoc.day07.b.domain.model.Direction;

public record Position(int row, int col) {

    public Position move(Direction direction) {
        return switch (direction) {
            case DOWN -> new Position(row + 1, col);
        };
    }

    // Métodos auxiliares para mover en cualquier dirección sin enum
    public Position moveDown() {
        return new Position(row + 1, col);
    }

    public Position moveLeft() {
        return new Position(row, col - 1);
    }

    public Position moveRight() {
        return new Position(row, col + 1);
    }

    public boolean isValid(int rows, int cols) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}
