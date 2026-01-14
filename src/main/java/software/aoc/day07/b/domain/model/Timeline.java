package software.aoc.day07.b.domain.model;

import java.util.Objects;

/**
 * Representa solo la posición actual de una timeline
 * No guardamos historial para evitar explosión de memoria
 */
public record Timeline(Position currentPosition) {

    public Timeline moveTo(Position newPosition) {
        return new Timeline(newPosition);
    }

    public record SplitResult(Timeline left, Timeline right) {}

    public SplitResult split() {
        Position leftPos = currentPosition.moveLeft();
        Position rightPos = currentPosition.moveRight();

        return new SplitResult(
                new Timeline(leftPos),
                new Timeline(rightPos)
        );
    }
}
