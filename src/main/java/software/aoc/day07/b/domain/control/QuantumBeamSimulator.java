package software.aoc.day07.b.domain.control;

import software.aoc.day07.b.domain.model.Cell;
import software.aoc.day07.b.domain.model.Manifold;
import software.aoc.day07.b.domain.model.Position;

import java.util.HashMap;
import java.util.Map;

public final class QuantumBeamSimulator {

    private final Manifold manifold;
    private final Map<Position, Long> memoCache;

    public QuantumBeamSimulator(Manifold manifold) {
        this.manifold = manifold;
        this.memoCache = new HashMap<>();
    }

    /** Parte 2: número total de timelines desde 'S'. */
    public long simulate() {
        return countTimelinesFrom(manifold.getStartPosition());
    }

    private long countTimelinesFrom(Position pos) {
        Long cached = memoCache.get(pos);
        if (cached != null) return cached;

        Position nextPos = pos.moveDown();

        // Si salió del manifold, esta rama termina => 1 timeline
        if (!nextPos.isValid(manifold.getRows(), manifold.getCols())) {
            memoCache.put(pos, 1L);
            return 1L;
        }

        Cell cell = manifold.getCell(nextPos).orElse(null);
        if (cell == null) {
            memoCache.put(pos, 1L);
            return 1L;
        }

        long count;
        char symbol = cell.getSymbol();

        if (symbol == '^') {
            long left = countTimelinesFrom(nextPos.moveLeft());
            long right = countTimelinesFrom(nextPos.moveRight());
            count = left + right;
        } else if (symbol == '.' || symbol == 'S') {
            count = countTimelinesFrom(nextPos);
        } else {
            // Si aparecen símbolos extra, por defecto “se corta”
            count = 1L;
        }

        memoCache.put(pos, count);
        return count;
    }
}
