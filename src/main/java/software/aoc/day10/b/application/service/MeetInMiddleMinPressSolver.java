package software.aoc.day10.b.application.service;

import software.aoc.day10.b.domain.model.Button;
import software.aoc.day10.b.domain.model.Machine;
import software.aoc.day10.b.domain.service.MachineSolver;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetInMiddleMinPressSolver implements MachineSolver {

    @Override
    public int minPresses(Machine machine) {
        Encoded encoded = encode(machine);
        Split split = split(encoded.buttonMasks());

        Map<BigInteger, Integer> leftBest = bestCostsByXor(split.left());
        return bestJoin(encoded.targetMask(), leftBest, split.right());
    }

    private Encoded encode(Machine machine) {
        BigInteger target = encodeTarget(machine);
        List<BigInteger> buttons = machine.buttons().stream().map(this::encodeButton).toList();
        return new Encoded(target, buttons);
    }

    private BigInteger encodeTarget(Machine machine) {
        BigInteger mask = BigInteger.ZERO;
        for (int i = 0; i < machine.getLightCount(); i++) {
            if (machine.targetConfiguration().getLight(i).isOn()) {
                mask = mask.setBit(i);
            }
        }
        return mask;
    }

    private BigInteger encodeButton(Button b) {
        return b.lightIndices().stream()
                .reduce(BigInteger.ZERO, (m, idx) -> m.flipBit(idx), BigInteger::xor);
    }

    private Split split(List<BigInteger> masks) {
        int mid = masks.size() / 2;
        return new Split(masks.subList(0, mid), masks.subList(mid, masks.size()));
    }

    private Map<BigInteger, Integer> bestCostsByXor(List<BigInteger> masks) {
        int n = masks.size();
        long total = 1L << n;

        Map<BigInteger, Integer> best = new HashMap<>();
        for (long s = 0; s < total; s++) {
            best.merge(xorOfSubset(masks, s), Long.bitCount(s), Math::min);
        }
        return best;
    }

    private int bestJoin(BigInteger target, Map<BigInteger, Integer> leftBest, List<BigInteger> right) {
        int n = right.size();
        long total = 1L << n;

        int best = Integer.MAX_VALUE;
        for (long s = 0; s < total; s++) {
            BigInteger xr = xorOfSubset(right, s);
            BigInteger need = target.xor(xr);
            Integer leftCost = leftBest.get(need);
            if (leftCost != null) {
                best = Math.min(best, leftCost + Long.bitCount(s));
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    private BigInteger xorOfSubset(List<BigInteger> masks, long subset) {
        BigInteger acc = BigInteger.ZERO;
        for (int i = 0; i < masks.size(); i++) {
            if (((subset >>> i) & 1L) == 1L) {
                acc = acc.xor(masks.get(i));
            }
        }
        return acc;
    }

    private record Encoded(BigInteger targetMask, List<BigInteger> buttonMasks) {}
    private record Split(List<BigInteger> left, List<BigInteger> right) {}
}
