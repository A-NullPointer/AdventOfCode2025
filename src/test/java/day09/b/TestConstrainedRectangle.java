package day09.b;

import software.aoc.day09.b.application.control.ConstrainedRectangleSolver;
import software.aoc.day09.b.domain.model.Point;

import java.util.Arrays;
import java.util.List;

public class TestConstrainedRectangle {

    public static void main(String[] args) {
        System.out.println("===== TEST CON EJEMPLO DEL ENUNCIADO =====\n");

        List<Point> points = Arrays.asList(
                new Point(7, 1),
                new Point(11, 1),
                new Point(11, 7),
                new Point(9, 7),
                new Point(9, 5),
                new Point(2, 5),
                new Point(2, 3),
                new Point(7, 3)
        );

        System.out.println("Puntos del input:");
        points.forEach(p -> System.out.println("  " + p));
        System.out.println();

        // Test casos específicos del enunciado
        System.out.println("===== CASOS DE EJEMPLO =====\n");

        testRectangle(points, new Point(7, 3), new Point(11, 1), 15, true);
        testRectangle(points, new Point(9, 7), new Point(9, 5), 3, true);
        testRectangle(points, new Point(9, 5), new Point(2, 3), 24, true);
        testRectangle(points, new Point(9, 7), new Point(2, 3), 40, false); // Contiene (9,5), (2,5), (7,3)

        // Buscar el óptimo
        System.out.println("\n===== BÚSQUEDA DEL ÓPTIMO =====\n");
        ConstrainedRectangleSolver solver = new ConstrainedRectangleSolver(points);
        long result = solver.findLargestConstrainedRectangle();

        System.out.println("\n===== RESULTADO =====");
        System.out.println("Área esperada: 24");
        System.out.println("Área obtenida: " + result);

        if (result == 24) {
            System.out.println("✓ TEST PASADO!");
        } else {
            System.out.println("✗ TEST FALLIDO");
        }
    }

    private static void testRectangle(List<Point> points, Point p1, Point p2,
                                      long expectedArea, boolean shouldBeValid) {
        System.out.println("Rectángulo entre " + p1 + " y " + p2);

        int width = Math.abs(p2.x() - p1.x()) + 1;
        int height = Math.abs(p2.y() - p1.y()) + 1;
        long area = (long) width * height;

        System.out.println("  Ancho: " + width + ", Alto: " + height);
        System.out.println("  Área calculada: " + area);
        System.out.println("  Área esperada: " + expectedArea);

        if (area == expectedArea) {
            System.out.println("  ✓ Área correcta");
        } else {
            System.out.println("  ✗ Área incorrecta");
        }

        // Verificar otros puntos (incluyendo bordes)
        int minX = Math.min(p1.x(), p2.x());
        int maxX = Math.max(p1.x(), p2.x());
        int minY = Math.min(p1.y(), p2.y());
        int maxY = Math.max(p1.y(), p2.y());

        boolean hasOtherPoints = false;
        for (Point p : points) {
            if (p.equals(p1) || p.equals(p2)) continue;

            if (p.x() >= minX && p.x() <= maxX && p.y() >= minY && p.y() <= maxY) {
                System.out.println("  ✗ Contiene punto: " + p);
                hasOtherPoints = true;
            }
        }

        if (!hasOtherPoints) {
            System.out.println("  ✓ No contiene otros puntos");
        }

        boolean isValid = !hasOtherPoints;
        if (isValid == shouldBeValid) {
            System.out.println("  ✓ Validez esperada: " + shouldBeValid);
        } else {
            System.out.println("  ✗ Validez incorrecta (esperada: " + shouldBeValid + ", obtenida: " + isValid + ")");
        }

        System.out.println();
    }
}
