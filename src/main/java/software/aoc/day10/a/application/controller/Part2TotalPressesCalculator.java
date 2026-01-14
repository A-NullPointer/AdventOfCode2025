package software.aoc.day10.a.application.controller;

import software.aoc.day10.b.domain.model.Machine;
import software.aoc.day10.b.domain.service.MachineSolver;

import java.util.List;
import java.util.concurrent.*;

public final class Part2TotalPressesCalculator {

    private final MachineSolver solver;

    public Part2TotalPressesCalculator(MachineSolver solver) {
        this.solver = solver;
    }

    public int solveSequential(List<Machine> machines) {
        return machines.stream().mapToInt(solver::minPresses).sum();
    }

    public int solveParallel(List<Machine> machines) {
        int threads = Math.max(1, Runtime.getRuntime().availableProcessors());
        ExecutorService pool = Executors.newFixedThreadPool(threads);

        try {
            List<Future<Integer>> futures = machines.stream()
                    .map(m -> pool.submit(() -> solver.minPresses(m)))
                    .toList();

            int sum = 0;
            for (Future<Integer> f : futures) sum += f.get();
            return sum;

        } catch (ExecutionException e) {
            throw new IllegalStateException("GLPK failed: " + e.getCause(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted", e);
        } finally {
            pool.shutdownNow();
        }
    }
}
