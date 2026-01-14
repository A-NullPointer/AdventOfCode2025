package software.aoc.day06.b.application.persistence.deserialization;

import software.aoc.day06.b.application.control.Worksheet;
import software.aoc.day06.b.domain.model.NumberSequence;
import software.aoc.day06.b.domain.model.Operator;
import software.aoc.day06.b.domain.persistence.deserialization.Parser;

import java.util.ArrayList;
import java.util.List;

public class RotatedLeftWorksheetParser implements Parser {

    @Override
    public Worksheet parse(List lines) {
        if (lines == null || lines.isEmpty()) return new Worksheet(List.of(), List.of());

        List<String> raw = castToStringList(lines);
        List<String> rotated = rotateLeft(raw);

        List<NumberSequence> sequences = new ArrayList<>();
        List<Operator> operators = new ArrayList<>();
        List<Long> accNumbers = new ArrayList<>();

        for (String s : rotated) {
            Token token = parseToken(s);
            if (token == null) continue;

            // ----------- EXPANSIÓN COMPLETA AQUÍ -----------
            // 1) Si trae número (normal o "número+op pegado"), acumularlo
            if (token.kind == Kind.NUMBER || token.kind == Kind.NUMBER_WITH_TRAILING_OP) {
                accNumbers.add(token.number);
            }

            // 2) Si trae operador (normal o "número+op pegado"), cerrar el bloque
            if (token.kind == Kind.OPERATOR || token.kind == Kind.NUMBER_WITH_TRAILING_OP) {
                Operator op = token.operator;

                if (accNumbers.isEmpty()) {
                    throw new IllegalArgumentException("Operator without numbers near token: " + s);
                }

                sequences.add(new NumberSequence(List.copyOf(accNumbers)));
                operators.add(op);
                accNumbers.clear();
            }
            // ----------- FIN EXPANSIÓN -----------
        }

        if (!accNumbers.isEmpty()) {
            throw new IllegalArgumentException("Dangling numbers without operator at end: " + accNumbers);
        }

        return new Worksheet(sequences, operators);
    }

    private List<String> rotateLeft(List<String> lines) {
        int rows = lines.size();
        int cols = lines.stream().mapToInt(String::length).max().orElse(0);

        List<String> padded = lines.stream().map(l -> padRight(l, cols)).toList();

        // transpose: cols -> rows
        List<String> transposed = new ArrayList<>(cols);
        for (int c = 0; c < cols; c++) {
            StringBuilder sb = new StringBuilder(rows);
            for (int r = 0; r < rows; r++) sb.append(padded.get(r).charAt(c));
            transposed.add(sb.toString());
        }

        // rotate-left = transpose + reverse
        List<String> rotated = new ArrayList<>(transposed.size());
        for (int i = transposed.size() - 1; i >= 0; i--) {
            String t = transposed.get(i).trim();
            if (!t.isEmpty()) rotated.add(t);
        }
        return rotated;
    }

    private Token parseToken(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;

        // Parse: número al inicio + (op opcional después)
        int i = 0;
        while (i < s.length() && Character.isDigit(s.charAt(i))) i++;
        if (i == 0) return null; // no empieza por número

        long num = Long.parseLong(s.substring(0, i));
        String rest = s.substring(i).trim();

        if (rest.isEmpty()) return Token.number(num);

        char opChar = rest.charAt(0);
        if (opChar != '+' && opChar != '*') {
            throw new IllegalArgumentException("Unknown operator tail: '" + rest + "' from token: " + s);
        }

        // número con operador pegado: produce “num” y luego “op”
        return Token.numberWithTrailingOp(num, Operator.fromSymbol(opChar));
    }

    private String padRight(String str, int width) {
        if (str.length() >= width) return str;
        return str + " ".repeat(width - str.length());
    }

    @SuppressWarnings("unchecked")
    private List<String> castToStringList(List raw) {
        return (List<String>) raw;
    }

    private enum Kind { NUMBER, OPERATOR, NUMBER_WITH_TRAILING_OP }

    private static final class Token {
        final Kind kind;
        final long number;      // válido si kind es NUMBER o NUMBER_WITH_TRAILING_OP
        final Operator operator; // válido si kind es OPERATOR o NUMBER_WITH_TRAILING_OP

        private Token(Kind kind, long number, Operator operator) {
            this.kind = kind;
            this.number = number;
            this.operator = operator;
        }

        static Token number(long n) {
            return new Token(Kind.NUMBER, n, null);
        }

        static Token op(Operator op) {
            return new Token(Kind.OPERATOR, 0, op);
        }

        static Token numberWithTrailingOp(long n, Operator op) {
            return new Token(Kind.NUMBER_WITH_TRAILING_OP, n, op);
        }
    }
}
