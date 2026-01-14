package software.aoc.day07.b.domain.model;

import software.aoc.day07.b.domain.model.Direction;
import software.aoc.day07.b.domain.model.Position;

/**
 * Value object representing a tachyon beam
 * Immutable - cada movimiento crea un nuevo Beam
 */
public record Beam(Position position, Direction direction) {

    public Beam move() {
        return new Beam(position.move(direction), direction);
    }

    public boolean isValid(int rows, int cols) {
        return position.isValid(rows, cols);
    }
}
