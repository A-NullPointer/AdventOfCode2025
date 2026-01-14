package software.aoc.day10.b.application.service;

import software.aoc.day10.b.domain.model.Button;
import software.aoc.day10.b.domain.model.Machine;
import software.aoc.day10.b.domain.service.MachineSolver;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;

public final class JoltageAStarMinPressSolver implements MachineSolver {

    @Override
    public int minPresses(Machine machine) {
        int[] target = requirements(machine);
        if (isZero(target)) return 0;

        List<Button> buttons = castButtons(machine.buttons());
        int[][] buttonIdxs = buttons.stream().map(this::toIndexArray).toArray(int[][]::new);

        ensureSolvable(target, buttonIdxs);

        int maxCover = Arrays.stream(buttonIdxs).mapToInt(a -> a.length).max().orElse(1);
        BigInteger base = BigInteger.valueOf(maxBase(target));

        State start = new State(target, encode(target, base));
        return aStar(start, buttonIdxs, base, maxCover);
    }

    private int aStar(State start, int[][] buttons, BigInteger base, int maxCover) {
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator
                .comparingInt(Node::f)
                .thenComparingInt(Node::g));

        Map<BigInteger, Integer> bestG = new HashMap<>();
        open.add(new Node(start, 0, heuristic(start.remaining(), maxCover)));
        bestG.put(start.key(), 0);

        while (!open.isEmpty()) {
            Node cur = open.poll();
            if (cur.state().isGoal()) return cur.g();

            if (cur.g() != bestG.getOrDefault(cur.state().key(), Integer.MAX_VALUE)) continue;

            for (int b = 0; b < buttons.length; b++) {
                State next = tryApply(cur.state(), buttons[b], base);
                if (next == null) continue;

                int g2 = cur.g() + 1;
                if (g2 >= bestG.getOrDefault(next.key(), Integer.MAX_VALUE)) continue;

                bestG.put(next.key(), g2);
                open.add(new Node(next, g2, g2 + heuristic(next.remaining(), maxCover)));
            }
        }

        return 0; // si no hay solución (no debería ocurrir si el input es válido)
    }

    private State tryApply(State s, int[] button, BigInteger base) {
        if (!canDecrementAll(s.remaining(), button)) return null;
        int[] next = s.remaining().clone();
        for (int idx : button) next[idx]--;
        return new State(next, encode(next, base));
    }

    private boolean canDecrementAll(int[] remaining, int[] idxs) {
        for (int idx : idxs) {
            if (remaining[idx] <= 0) return false;
        }
        return true;
    }

    private int heuristic(int[] remaining, int maxCover) {
        int sum = Arrays.stream(remaining).sum();
        int h1 = (sum + maxCover - 1) / maxCover; // cota por “máximo decremento por pulsación”
        int h2 = Arrays.stream(remaining).max().orElse(0); // cada contador baja <=1 por pulsación
        return Math.max(h1, h2);
    }

    private void ensureSolvable(int[] target, int[][] buttons) {
        for (int i = 0; i < target.length; i++) {
            if (target[i] == 0) continue;
            boolean covered = false;
            for (int[] b : buttons) {
                if (contains(b, i)) { covered = true; break; }
            }
            if (!covered) {
                throw new IllegalArgumentException("No button affects counter index " + i);
            }
        }
    }

    private boolean contains(int[] arr, int v) {
        for (int x : arr) if (x == v) return true;
        return false;
    }

    private BigInteger encode(int[] remaining, BigInteger base) {
        BigInteger key = BigInteger.ZERO;
        for (int v : remaining) key = key.multiply(base).add(BigInteger.valueOf(v));
        return key;
    }

    private int maxBase(int[] target) {
        int max = Arrays.stream(target).max().orElse(0);
        return Math.max(2, max + 1);
    }

    private boolean isZero(int[] a) {
        for (int v : a) if (v != 0) return false;
        return true;
    }

    private int[] requirements(Machine machine) {
        List<Integer> req = castInts(machine.shakeRequirements());
        return req.stream().mapToInt(Integer::intValue).toArray();
    }

    private int[] toIndexArray(Button b) {
        List<Integer> idxs = castInts(b.lightIndices());
        return idxs.stream().mapToInt(Integer::intValue).toArray();
    }

    @SuppressWarnings("unchecked")
    private List<Button> castButtons(List raw) {
        return (List<Button>) raw;
    }

    @SuppressWarnings("unchecked")
    private List<Integer> castInts(List raw) {
        return (List<Integer>) raw;
    }

    private record Node(State state, int g, int f) {}
    private record State(int[] remaining, BigInteger key) {
        boolean isGoal() { return IntStream.of(remaining).allMatch(v -> v == 0); }
    }
}
