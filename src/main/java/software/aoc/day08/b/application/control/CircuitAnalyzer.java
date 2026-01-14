package software.aoc.day08.b.application.control;

import software.aoc.day08.b.application.persistence.deserialization.JunctionBoxParser;
import software.aoc.day08.b.application.persistence.io.PlainTextReader;
import software.aoc.day08.b.domain.model.JunctionBox;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CircuitAnalyzer {

    public static void main(String[] args) {
        String filePath = "src/main/resources/input.txt";

        // Cargar cajas
        List<JunctionBox> boxes = new JunctionBoxParser()
                .parse(new PlainTextReader(filePath).read().collect(Collectors.toList()));

        int n = boxes.size();
        int[] parent = IntStream.range(0, n).toArray();

        // Generar y ordenar todas las aristas por distancia
        List<Edge> edges = IntStream.range(0, n)
                .boxed()
                .flatMap(i -> IntStream.range(i + 1, n)
                        .mapToObj(j -> new Edge(i, j, distance(boxes.get(i), boxes.get(j)))))
                .sorted(Comparator.comparingDouble(e -> e.dist))
                .limit(1000)
                .collect(Collectors.toList());

        // Union-Find functions
        java.util.function.IntUnaryOperator find = new java.util.function.IntUnaryOperator() {
            public int applyAsInt(int x) {
                while (parent[x] != x) {
                    parent[x] = parent[parent[x]];
                    x = parent[x];
                }
                return x;
            }
        };

        // Conectar aristas
        edges.forEach(e -> {
            int rootA = find.applyAsInt(e.a);
            int rootB = find.applyAsInt(e.b);
            if (rootA != rootB) parent[rootB] = rootA;
        });

        // Contar tamaños de componentes
        Map<Integer, Long> componentSizes = IntStream.range(0, n)
                .boxed()
                .collect(Collectors.groupingBy(
                        i -> find.applyAsInt(i),
                        Collectors.counting()
                ));

        // Obtener 3 componentes más grandes y multiplicar
        long result = componentSizes.values().stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(1L, (a, b) -> a * b);

        System.out.println("Resultado: " + result);
    }

    static double distance(JunctionBox a, JunctionBox b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        double dz = a.z() - b.z();
        return dx * dx + dy * dy + dz * dz;
    }

    static class Edge {
        final int a, b;
        final double dist;
        Edge(int a, int b, double dist) {
            this.a = a; this.b = b; this.dist = dist;
        }
    }
}