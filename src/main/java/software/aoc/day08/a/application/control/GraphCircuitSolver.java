package software.aoc.day08.a.application.control;

import software.aoc.day08.a.domain.model.JunctionBox;
import software.aoc.day08.a.domain.service.CircuitAnalysisService;

import java.util.*;

public class GraphCircuitSolver implements CircuitAnalysisService {

    private static class Edge implements Comparable<Edge> {
        final int from;
        final int to;
        final double distanceSquared;

        Edge(int from, int to, double distanceSquared) {
            this.from = from;
            this.to = to;
            this.distanceSquared = distanceSquared;
        }

        @Override
        public int compareTo(Edge other) {
            return Double.compare(this.distanceSquared, other.distanceSquared);
        }
    }

    private static class UnionFind {
        private final int[] parent;
        private final int[] rank;
        private final int[] size;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) {
                return false;
            }

            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
                rank[rootX]++;
            }

            return true;
        }

        int getSize(int x) {
            return size[find(x)];
        }

        List<Integer> getComponentSizes() {
            Map<Integer, Integer> sizes = new HashMap<>();
            for (int i = 0; i < parent.length; i++) {
                int root = find(i);
                sizes.put(root, getSize(root));
            }
            return new ArrayList<>(sizes.values());
        }
    }

    @Override
    public long analyzeCircuits(List<JunctionBox> boxes, int edgesToProcess) {
        int n = boxes.size();

        System.out.println("Generando aristas...");

        PriorityQueue<Edge> edges = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            JunctionBox box1 = boxes.get(i);
            for (int j = i + 1; j < n; j++) {
                JunctionBox box2 = boxes.get(j);
                double dist = box1.distanceSquared(box2);
                edges.offer(new Edge(i, j, dist));
            }

            if ((i + 1) % 100 == 0) {
                System.out.println("Procesadas " + (i + 1) + " cajas...");
            }
        }

        System.out.println("Total aristas: " + edges.size());

        UnionFind uf = new UnionFind(n);

        // PROCESAR las K aristas más cortas
        int processed = 0;
        int successfulConnections = 0;

        while (!edges.isEmpty() && processed < edgesToProcess) {
            Edge edge = edges.poll();
            processed++;

            if (uf.union(edge.from, edge.to)) {
                successfulConnections++;
            }
        }

        System.out.println("Aristas procesadas: " + processed);
        System.out.println("Conexiones exitosas: " + successfulConnections);

        List<Integer> componentSizes = uf.getComponentSizes();

        System.out.println("Número de circuitos: " + componentSizes.size());
        componentSizes.sort(Comparator.reverseOrder());
        System.out.println("Top 3 circuitos: " + componentSizes.subList(0, Math.min(3, componentSizes.size())));

        return componentSizes.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToLong(Integer::longValue)
                .reduce(1L, (a, b) -> a * b);
    }
}
