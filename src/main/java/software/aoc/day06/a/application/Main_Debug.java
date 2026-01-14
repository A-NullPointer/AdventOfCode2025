package software.aoc.day06.a.application;

import software.aoc.day06.a.application.control.Worksheet;
import software.aoc.day06.a.application.persistence.deserialization.WorksheetParser;
import software.aoc.day06.a.application.persistence.io.PlainTextReader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main_Debug {

    public static void main(String[] args) {
        try {
            String filePath = "C:\\Users\\asmae\\Documents\\INGENIERIA INFORMATICA\\3 TERCERO\\IS2\\2025-26\\AdventOfCode2025\\AdventOfCode2025\\src\\main\\resources\\day06\\inputs.txt";

            // ===== PASO 1: VERIFICAR ARCHIVO =====
            System.out.println("===== PASO 1: VERIFICACIÓN DE ARCHIVO =====");
            File file = new File(filePath);
            System.out.println("Ruta absoluta: " + file.getAbsolutePath());
            System.out.println("Archivo existe: " + file.exists());
            System.out.println("Es archivo: " + file.isFile());
            System.out.println("Tamaño: " + file.length() + " bytes");
            System.out.println();

            if (!file.exists()) {
                System.err.println("ERROR: El archivo no existe!");
                System.err.println("Asegúrate de que 'inputs.txt' está en: " + file.getAbsolutePath());
                return;
            }

            // ===== PASO 2: LEER LÍNEAS DIRECTAMENTE =====
            System.out.println("===== PASO 2: LECTURA DIRECTA =====");
            List<String> directLines = Files.readAllLines(Paths.get(filePath));
            System.out.println("Líneas leídas directamente: " + directLines.size());

            if (directLines.isEmpty()) {
                System.err.println("ERROR: El archivo está vacío!");
                return;
            }

            for (int i = 0; i < Math.min(3, directLines.size()); i++) {
                String preview = directLines.get(i).substring(0, Math.min(80, directLines.get(i).length()));
                System.out.println("Línea " + i + " (primeros 80 chars): " + preview + "...");
            }
            System.out.println();

            // ===== PASO 3: LEER CON PlainTextReader =====
            System.out.println("===== PASO 3: LECTURA CON PlainTextReader =====");
            PlainTextReader reader = new PlainTextReader(filePath);
            List<String> readerLines = reader.read().toList();
            System.out.println("Líneas leídas con reader: " + readerLines.size());

            if (readerLines.isEmpty()) {
                System.err.println("ERROR: PlainTextReader no está leyendo líneas!");
                return;
            }
            System.out.println();

            // ===== PASO 4: PARSEAR =====
            System.out.println("===== PASO 4: PARSEANDO CON WorksheetParser =====");
            WorksheetParser parser = new WorksheetParser();

            // Separar operadores y números manualmente para debug
            String operatorsLine = readerLines.get(readerLines.size() - 1);
            List<String> numberLines = readerLines.subList(0, readerLines.size() - 1);

            System.out.println("Líneas de números: " + numberLines.size());
            System.out.println("Línea de operadores (primeros 100 chars): " +
                    operatorsLine.substring(0, Math.min(100, operatorsLine.length())));

            // Contar operadores manualmente
            long operatorCount = operatorsLine.chars()
                    .filter(ch -> ch == '+' || ch == '*')
                    .count();
            System.out.println("Operadores detectados: " + operatorCount);
            System.out.println();

            // ===== PASO 5: CARGAR WORKSHEET =====
            System.out.println("===== PASO 5: CARGANDO WORKSHEET =====");
            Worksheet worksheet = parser.parse(readerLines);

            System.out.println("Total de problemas: " + worksheet.sequences().size());
            System.out.println("Total de operadores: " + worksheet.operators().size());
            System.out.println();

            if (worksheet.sequences().isEmpty()) {
                System.err.println("ERROR: El worksheet está vacío después de parsear!");
                return;
            }

            // ===== PASO 6: MOSTRAR PRIMEROS PROBLEMAS =====
            System.out.println("===== PASO 6: PRIMEROS 5 PROBLEMAS =====");
            for (int i = 0; i < Math.min(5, worksheet.sequences().size()); i++) {
                var sequence = worksheet.sequences().get(i);
                var operator = worksheet.operators().get(i);
                long result = sequence.applyOperator(operator);

                System.out.println("Problema " + (i+1) + ": " +
                        sequence.sequence() + " " +
                        operator.name() + " = " + result);
            }
            System.out.println();

            // ===== PASO 7: CALCULAR TOTAL =====
            System.out.println("===== PASO 7: RESULTADO FINAL =====");
            long total = worksheet.calculateTotal();
            System.out.println("Total: " + total);

        } catch (Exception e) {
            System.err.println("ERROR FATAL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
