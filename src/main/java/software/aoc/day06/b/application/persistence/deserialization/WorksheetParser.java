package software.aoc.day06.b.application.persistence.deserialization;

import software.aoc.day06.b.application.control.Worksheet;
import software.aoc.day06.b.domain.model.NumberSequence;
import software.aoc.day06.b.domain.model.Operator;
import software.aoc.day06.b.domain.persistence.deserialization.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class WorksheetParser implements Parser<Worksheet> {

    @Override
    public Worksheet parse(List<String> lines) {
        if (lines.isEmpty()) {
            return new Worksheet(List.of(), List.of());
        }

        // 1. Separar la última línea (operadores) del resto (números)
        String operatorsLine = lines.getLast();  // Java 21+
        List<String> numberLines = lines.subList(0, lines.size() - 1);

        // 2. Procesar la línea de operadores
        List<Operator> operators = parseOperatorsLine(operatorsLine);

        if (operators.isEmpty()) {
            return new Worksheet(List.of(), List.of());
        }

        // 3. Procesar las líneas de números (transposición vertical)
        List<List<Long>> columns = transposeNumbers(numberLines, operators.size());

        // 4. Crear NumberSequences
        List<NumberSequence> sequences = columns.stream()
                .map(NumberSequence::new)
                .toList();

        return new Worksheet(sequences, operators);
    }

    /**
     * Extrae solo los operadores + y * de la línea, ignorando espacios
     */
    private List<Operator> parseOperatorsLine(String line) {
        List<Operator> operators = new ArrayList<>();

        // Recorrer cada carácter y filtrar solo operadores válidos
        for (char ch : line.toCharArray()) {
            if (ch == '+' || ch == '*') {
                operators.add(Operator.fromSymbol(ch));
            }
        }

        return operators;
    }

    /**
     * Transpone las líneas de números en columnas
     * Cada columna representa un problema vertical
     */
    private List<List<Long>> transposeNumbers(List<String> numberLines, int numColumns) {
        // Inicializar columnas vacías
        List<List<Long>> columns = new ArrayList<>();
        for (int i = 0; i < numColumns; i++) {
            columns.add(new ArrayList<>());
        }

        // Convertir cada línea en array de números
        List<Long[]> numberArrays = numberLines.stream()
                .map(this::parseNumberLine)
                .toList();

        // Validar que todas las líneas tengan el mismo número de columnas
        for (Long[] numbers : numberArrays) {
            if (numbers.length != numColumns) {
                throw new IllegalArgumentException(
                        "Inconsistencia: se esperaban " + numColumns +
                                " números pero se encontraron " + numbers.length
                );
            }
        }

        // Transponer: para cada columna, tomar el número de cada fila
        for (int col = 0; col < numColumns; col++) {
            for (Long[] numberArray : numberArrays) {
                columns.get(col).add(numberArray[col]);
            }
        }

        return columns;
    }

    /**
     * Parsea una línea de números separados por espacios (múltiples espacios permitidos)
     */
    private Long[] parseNumberLine(String line) {
        String[] tokens = line.trim().split("\\s+");

        return IntStream.range(0, tokens.length)
                .mapToObj(i -> {
                    try {
                        return Long.parseLong(tokens[i]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(
                                "No se pudo parsear el número: '" + tokens[i] + "' en posición " + i,
                                e
                        );
                    }
                })
                .toArray(Long[]::new);
    }
}
