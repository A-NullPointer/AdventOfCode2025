package software.aoc.day08.b.application.control;

import software.aoc.day08.b.domain.model.JunctionBox;
import software.aoc.day08.b.domain.service.UnifiedCircuitService;

import java.util.*;

/**
 * Conecta junction boxes hasta formar un único circuito
 */
public class UnifiedCircuitSolver implements UnifiedCircuitService {

    @Override
    public Result connectUntilUnified(List<JunctionBox> boxes) {
        int n = boxes.size();

        System.out.println("Generando todas las aristas...");

        // 1. Generar todas las aristas posibles y ordenarlas
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

        System.out.println("Total aristas generadas: " + edges.size());
        System.out.println();

        // 2. Union-Find
        UnionFind uf = new UnionFind(n);

        // 3. Conectar hasta tener un solo componente
        int edgesProcessed = 0;
        int connectionsSuccessful = 0;
        Edge lastConnection = null;

        while (!edges.isEmpty() && uf.getComponentCount() > 1) {
            Edge edge = edges.poll();
            edgesProcessed++;

            if (uf.union(edge.from, edge.to)) {
                connectionsSuccessful++;
                lastConnection = edge;

                if (connectionsSuccessful % 100 == 0) {
                    System.out.println("Conexiones: " + connectionsSuccessful +
                            ", Componentes restantes: " + uf.getComponentCount());
                }
            }
        }

        System.out.println("\n===== RESULTADO =====");
        System.out.println("Aristas procesadas: " + edgesProcessed);
        System.out.println("Conexiones exitosas: " + connectionsSuccessful);
        System.out.println("Componentes finales: " + uf.getComponentCount());

        if (lastConnection == null) {
            throw new IllegalStateException("No se pudo unificar todas las cajas");
        }

        // 4. Obtener las dos cajas de la última conexión
        JunctionBox box1 = boxes.get(lastConnection.from);
        JunctionBox box2 = boxes.get(lastConnection.to);

        System.out.println("\nÚltima conexión:");
        System.out.println("  Box 1 (índice " + lastConnection.from + "): " + box1);
        System.out.println("  Box 2 (índice " + lastConnection.to + "): " + box2);
        System.out.println("  Distancia: " + Math.sqrt(lastConnection.distance));

        long xProduct = box1.x() * box2.x();
        System.out.println("\nProducto de coordenadas X: " + box1.x() + " × " + box2.x() + " = " + xProduct);

        return new Result(box1, box2, xProduct, connectionsSuccessful);
    }

    // ========== CLASES INTERNAS ==========

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

    private static class UnionFind {
        private final int[] parent;
        private final int[] rank;
        private final int[] size;
        private int componentCount;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            size = new int[n];
            componentCount = n;

            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) {
                return false; // Ya conectados
            }

            // Union by rank
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

            componentCount--;
            return true;
        }

        int getComponentCount() {
            return componentCount;
        }
    }
}
