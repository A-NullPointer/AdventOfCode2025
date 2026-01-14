package software.aoc.day08.a.application.control;

import software.aoc.day08.a.domain.model.JunctionBox;
import software.aoc.day08.a.domain.service.CircuitAnalysisService;

import java.util.*;
import java.util.stream.IntStream;

public class StreamCircuitSolver implements CircuitAnalysisService {

    @Override
    public long analyzeCircuits(List<JunctionBox> boxes, int connectionsToMake) {
        int n = boxes.size();

        PriorityQueue<Edge> edges = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                edges.offer(new Edge(i, j, boxes.get(i).distanceSquared(boxes.get(j))));
            }
        }

        System.out.println("Total aristas generadas: " + edges.size());

        int[] parent = IntStream.range(0, n).toArray();
        int[] size = new int[n];
        Arrays.fill(size, 1);

        java.util.function.IntUnaryOperator find = new java.util.function.IntUnaryOperator() {
            public int applyAsInt(int x) {
                while (parent[x] != x) {
                    parent[x] = parent[parent[x]];
                    x = parent[x];
                }
                return x;
            }
        };

        int edgesProcessed = 0;
        int successfulConnections = 0;
        int skippedConnections = 0;

        while (!edges.isEmpty() && edgesProcessed < connectionsToMake) {
            Edge edge = edges.poll();
            edgesProcessed++;

            int rootA = find.applyAsInt(edge.from);
            int rootB = find.applyAsInt(edge.to);

            if (rootA != rootB) {
                // Conexión exitosa
                if (size[rootA] < size[rootB]) {
                    parent[rootA] = rootB;
                    size[rootB] += size[rootA];
                } else {
                    parent[rootB] = rootA;
                    size[rootA] += size[rootB];
                }
                successfulConnections++;
            } else {
                // Arista saltada (ya conectados)
                skippedConnections++;
            }
        }

        System.out.println("Aristas procesadas: " + edgesProcessed);
        System.out.println("Conexiones exitosas: " + successfulConnections);
        System.out.println("Conexiones saltadas: " + skippedConnections);

        Map<Integer, Integer> componentSizes = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = find.applyAsInt(i);
            componentSizes.put(root, size[root]);
        }

        System.out.println("Número de circuitos: " + componentSizes.size());
        List<Integer> sizes = new ArrayList<>(componentSizes.values());
        sizes.sort(Comparator.reverseOrder());
        System.out.println("Top 3 tamaños: " + sizes.subList(0, Math.min(3, sizes.size())));

        return componentSizes.values().stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToLong(Integer::longValue)
                .reduce(1L, (a, b) -> a * b);
    }

    private static class Edge implements Comparable<Edge> {
        final int from, to;
        final double distance;

        Edge(int from, int to, double distance) {
            this.from = from;
            this.to = to;
            this.distance = distance;
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(this.distance, other.distance);
        }
    }
}
