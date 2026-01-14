package software.aoc.day09.b.application.control;

import software.aoc.day09.b.domain.model.Point;

import java.util.List;

public class ConstrainedRectangleSolver {

    private final List<Point> points;

    public ConstrainedRectangleSolver(List<Point> points) {
        this.points = points;
    }

    /**
     * Encuentra el área del rectángulo más grande formado por dos puntos
     * que NO contiene otros puntos (ni en bordes ni en interior).
     * Solo se permiten los dos puntos que definen las esquinas.
     */
    public long findLargestConstrainedRectangle() {
        long maxArea = 0L;
        Point bestP1 = null, bestP2 = null;

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                Point p1 = points.get(i);
                Point p2 = points.get(j);

                // Verificar que formen un rectángulo válido
                if (isValidRectangle(p1, p2)) {
                    // Verificar que no contenga otros puntos (ni en bordes ni en interior)
                    if (!containsOtherPoints(p1, p2)) {
                        long area = calculateRectangleArea(p1, p2);

                        if (area > maxArea) {
                            maxArea = area;
                            bestP1 = p1;
                            bestP2 = p2;
                        }
                    }
                }
            }
        }

        if (bestP1 != null && bestP2 != null) {
            System.out.println("Mejor rectángulo: " + bestP1 + " a " + bestP2);
            System.out.println("Área: " + maxArea);
        }

        return maxArea;
    }

    /**
     * Verifica si dos puntos forman un rectángulo válido (no degenerado)
     */
    private boolean isValidRectangle(Point p1, Point p2) {
        return p1.x() != p2.x() && p1.y() != p2.y();
    }

    /**
     * Calcula el área del rectángulo formado por dos puntos opuestos
     */
    private long calculateRectangleArea(Point p1, Point p2) {
        int width = Math.abs(p2.x() - p1.x()) + 1;
        int height = Math.abs(p2.y() - p1.y()) + 1;
        return (long) width * height;
    }

    /**
     * Verifica si hay otros puntos dentro del rectángulo (incluyendo bordes)
     * Excluye solo los dos puntos que definen las esquinas
     */
    private boolean containsOtherPoints(Point p1, Point p2) {
        int minX = Math.min(p1.x(), p2.x());
        int maxX = Math.max(p1.x(), p2.x());
        int minY = Math.min(p1.y(), p2.y());
        int maxY = Math.max(p1.y(), p2.y());

        for (Point point : points) {
            // No considerar los dos puntos que definen las esquinas
            if (point.equals(p1) || point.equals(p2)) {
                continue;
            }

            // Verificar si el punto está dentro del rectángulo (incluyendo bordes)
            if (point.x() >= minX && point.x() <= maxX &&
                    point.y() >= minY && point.y() <= maxY) {
                return true; // Hay otro punto en el rectángulo
            }
        }

        return false; // No hay otros puntos
    }
}