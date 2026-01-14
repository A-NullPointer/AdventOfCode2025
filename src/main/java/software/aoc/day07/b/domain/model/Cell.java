package software.aoc.day07.b.domain.model;

/**
 * Interfaz simplificada para celdas
 * Solo necesitamos saber qué tipo de celda es
 */
public interface Cell {
    char getSymbol();

    /**
     * Factory Method para crear celdas según el símbolo
     */
    static Cell fromSymbol(char symbol) {
        return switch (symbol) {
            case '.' -> new EmptyCell();
            case '^' -> new SplitterCell();
            case 'S' -> new StartCell();
            default -> throw new IllegalArgumentException("Unknown cell symbol: " + symbol);
        };
    }
}

/**
 * Celda vacía - el haz pasa libremente
 */
class EmptyCell implements Cell {
    @Override
    public char getSymbol() {
        return '.';
    }
}

/**
 * Celda divisora - divide haces DOWN en LEFT y RIGHT
 */
class SplitterCell implements Cell {
    @Override
    public char getSymbol() {
        return '^';
    }
}

/**
 * Celda de inicio
 */
class StartCell implements Cell {
    @Override
    public char getSymbol() {
        return 'S';
    }
}
