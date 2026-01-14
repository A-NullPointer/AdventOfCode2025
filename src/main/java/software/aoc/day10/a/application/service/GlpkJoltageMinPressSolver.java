package software.aoc.day10.a.application.service;

import software.aoc.day10.b.domain.model.Button;
import software.aoc.day10.b.domain.model.Machine;
import software.aoc.day10.b.domain.service.MachineSolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GlpkJoltageMinPressSolver implements MachineSolver {

    private static final String GLPSOL = "glpsol";

    private static final Pattern OBJECTIVE_LINE =
            Pattern.compile("Objective:\\s+\\w+\\s*=\\s*([-0-9.eE+]+)");

    private static final Pattern MIP_LINE =
            Pattern.compile("mip\\s*=\\s*([-0-9.eE+]+)");

    private static final Pattern OBJECTIVE_VALUE_LINE =
            Pattern.compile("Objective value\\s*=\\s*([-0-9.eE+]+)");

    @Override
    public int minPresses(Machine machine) {
        List<Button> buttons = castButtons(machine.buttons());
        List<Integer> jolts = castInts(machine.shakeRequirements());

        List<List<Integer>> buttonIndices = buttons.stream()
                .map(b -> castInts(b.lightIndices()))
                .toList();

        String lp = buildLp(buttonIndices, jolts);

        Path lpPath = null;
        try {
            lpPath = Files.createTempFile("day10_", ".lp");
            Files.writeString(lpPath, lp, StandardCharsets.UTF_8);

            Process process = new ProcessBuilder(GLPSOL, "--lp", lpPath.toString())
                    .redirectErrorStream(true)
                    .start();

            String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            int exit = process.waitFor();

            if (exit != 0) {
                throw new IllegalStateException("glpsol failed (exit " + exit + "):\n" + output);
            }

            Integer obj = parseObjective(output);
            if (obj == null) {
                throw new IllegalStateException("Could not parse objective value from glpsol output:\n" + output);
            }

            return obj;
        } catch (IOException e) {
            throw new IllegalStateException("IO running glpsol: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while running glpsol", e);
        } finally {
            if (lpPath != null) {
                try { Files.deleteIfExists(lpPath); } catch (IOException ignored) {}
            }
        }
    }

    private String buildLp(List<List<Integer>> buttons, List<Integer> jolts) {
        int k = buttons.size();
        int m = jolts.size();

        List<String> varNames = new ArrayList<>();
        for (int j = 0; j < k; j++) varNames.add("x" + j);

        StringBuilder sb = new StringBuilder();

        sb.append("Minimize\n");
        sb.append("  z: ").append(String.join(" + ", varNames)).append("\n\n");

        sb.append("Subject To\n");
        for (int i = 0; i < m; i++) {
            List<String> terms = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                if (buttons.get(j).contains(i)) terms.add("x" + j);
            }
            String lhs = terms.isEmpty() ? "0" : String.join(" + ", terms);
            sb.append("  c").append(i).append(": ").append(lhs).append(" = ").append(jolts.get(i)).append("\n");
        }

        sb.append("\nBounds\n");
        for (String v : varNames) sb.append("  ").append(v).append(" >= 0\n");

        sb.append("\nGenerals\n");
        sb.append("  ").append(String.join(" ", varNames)).append("\n\n");

        sb.append("End\n");
        return sb.toString();
    }

    private Integer parseObjective(String output) {
        Integer v;

        v = matchFirstNumber(OBJECTIVE_LINE, output);
        if (v != null) return v;

        v = matchFirstNumber(MIP_LINE, output);
        if (v != null) return v;

        v = matchFirstNumber(OBJECTIVE_VALUE_LINE, output);
        return v;
    }

    private Integer matchFirstNumber(Pattern p, String output) {
        Matcher m = p.matcher(output);
        if (!m.find()) return null;
        return parseNumber(m.group(1));
    }

    private Integer parseNumber(String s) {
        try {
            // entero directo
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException ignored) {
            try {
                // float en notación científica -> redondear
                double d = Double.parseDouble(s.trim());
                return (int) Math.round(d);
            } catch (NumberFormatException ignored2) {
                return null;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private List<Button> castButtons(List raw) {
        return (List<Button>) raw;
    }

    @SuppressWarnings("unchecked")
    private List<Integer> castInts(List raw) {
        return (List<Integer>) raw;
    }
}
