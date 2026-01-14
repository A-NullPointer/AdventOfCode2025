package software.aoc.day06.b.application.persistence.deserialization;

import software.aoc.day06.b.application.control.Worksheet;
import software.aoc.day06.b.domain.model.NumberSequence;
import software.aoc.day06.b.domain.model.Operator;
import software.aoc.day06.b.domain.persistence.deserialization.Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser que lee números verticalmente (dígito por dígito) y problemas de derecha a izquierda
 */
public class VerticalWorksheetParser implements Parser<Worksheet> {

    @Override
    public Worksheet parse(List<String> lines) {
        if (lines.isEmpty()) {
            return new Worksheet(List.of(), List.of());
        }

        // 1. Separar última línea (operadores) del resto (números)
        String operatorsLine = lines.getLast();
        List<String> numberLines = lines.subList(0, lines.size() - 1);

        // 2. Obtener el ancho máximo para procesar carácter por carácter
        int maxWidth = numberLines.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);

        // 3. Normalizar líneas al mismo ancho (rellenar con espacios)
        List<String> normalizedLines = numberLines.stream()
                .map(line -> padRight(line, maxWidth))
                .toList();

        // 4. Extraer columnas de dígitos (de derecha a izquierda)
        List<DigitColumn> digitColumns = extractDigitColumns(normalizedLines, operatorsLine, maxWidth);

        // 5. Convertir a NumberSequences y Operators
        List<NumberSequence> sequences = new ArrayList<>();
        List<Operator> operators = new ArrayList<>();

        for (DigitColumn column : digitColumns) {
            sequences.add(new NumberSequence(column.numbers()));
            operators.add(column.operator());
        }

        return new Worksheet(sequences, operators);
    }

    /**
     * Extrae columnas de dígitos, leyendo de DERECHA a IZQUIERDA
     */
    private List<DigitColumn> extractDigitColumns(List<String> lines, String operatorsLine, int maxWidth) {
        List<DigitColumn> columns = new ArrayList<>();
        List<StringBuilder> currentNumberBuilders = null;
        Operator currentOperator = null;

        // Leer de derecha a izquierda
        for (int col = maxWidth - 1; col >= 0; col--) {
            boolean isEmptyColumn = true;

            // Verificar si esta columna tiene algún dígito
            for (String line : lines) {
                char ch = col < line.length() ? line.charAt(col) : ' ';
                if (ch != ' ') {
                    isEmptyColumn = false;
                    break;
                }
            }

            // Si es columna vacía, finalizar problema actual
            if (isEmptyColumn) {
                if (currentNumberBuilders != null && currentOperator != null) {
                    columns.add(buildDigitColumn(currentNumberBuilders, currentOperator));
                    currentNumberBuilders = null;
                    currentOperator = null;
                }
                continue;
            }

            // Extraer operador para esta columna
            char operatorChar = col < operatorsLine.length() ? operatorsLine.charAt(col) : ' ';
            if (operatorChar == '+' || operatorChar == '*') {
                currentOperator = Operator.fromSymbol(operatorChar);
            }

            // Inicializar builders si es necesario
            if (currentNumberBuilders == null) {
                currentNumberBuilders = new ArrayList<>();
                for (int i = 0; i < lines.size(); i++) {
                    currentNumberBuilders.add(new StringBuilder());
                }
            }

            // Agregar dígitos de esta columna (en orden inverso porque vamos de derecha a izquierda)
            for (int row = 0; row < lines.size(); row++) {
                char ch = col < lines.get(row).length() ? lines.get(row).charAt(col) : ' ';
                if (Character.isDigit(ch)) {
                    // Insertar al principio porque vamos de derecha a izquierda
                    currentNumberBuilders.get(row).insert(0, ch);
                }
            }
        }

        // Agregar el último problema si existe
        if (currentNumberBuilders != null && currentOperator != null) {
            columns.add(buildDigitColumn(currentNumberBuilders, currentOperator));
        }

        return columns;
    }

    /**
     * Construye una DigitColumn a partir de los builders
     */
    private DigitColumn buildDigitColumn(List<StringBuilder> builders, Operator operator) {
        List<Long> numbers = builders.stream()
                .map(StringBuilder::toString)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .toList();

        return new DigitColumn(numbers, operator);
    }

    /**
     * Rellena una cadena con espacios a la derecha hasta alcanzar el ancho deseado
     */
    private String padRight(String str, int width) {
        if (str.length() >= width) {
            return str;
        }
        return str + " ".repeat(width - str.length());
    }

    /**
     * Record interno para representar una columna de números con su operador
     */
    private record DigitColumn(List<Long> numbers, Operator operator) {}
}