package software.aoc.day10.b.application.controller;

import software.aoc.day09.b.domain.model.AreaCalculator;
import software.aoc.day09.b.domain.model.Point;
import software.aoc.day09.b.domain.model.RectangleAreaCalculator;

import java.util.*;
import java.util.stream.Collectors;

public final class ConstrainedRectangleSolver {

    private final List<Point> inputPoints;
    private final AreaCalculator areaCalculator;

    public ConstrainedRectangleSolver(List<Point> points) {
        this(points, new RectangleAreaCalculator());
    }

    public ConstrainedRectangleSolver(List<Point> points, AreaCalculator areaCalculator) {
        this.inputPoints = List.copyOf(points);
        this.areaCalculator = areaCalculator;
    }

    public long findLargestConstrainedRectangle() {
        if (inputPoints.size() < 2) return 0L;

        // 1) Enriquecer puntos (v1, v2, cuadrantes)
        List<EnrichedPoint> enriched = buildEnrichedPoints(inputPoints);

        // 2) Asignar ángulos (sharp/obtuse) recorriendo el contorno
        enriched = assignAngles(enriched);

        // 3) Construir lados globales y asociarlos a cada punto
        enriched = assignSides(enriched);

        // 4) Evaluar todos los pares y devolver el máximo área permitido
        long best = 0L;
        for (int i = 0; i < enriched.size(); i++) {
            for (int j = i + 1; j < enriched.size(); j++) {
                EnrichedPoint p1 = enriched.get(i);
                EnrichedPoint p2 = enriched.get(j);
                if (allowed(p1, p2)) {
                    long area = areaCalculator.calculateArea(p1.base, p2.base);
                    if (area > best) best = area;
                }
            }
        }
        return best;
    }

    // -------------------- Enriched model --------------------

    private enum AngleKind { SHARP, OBTUSE }

    private record Vec(int dx, int dy) {
        Vec opposite() { return new Vec(-dx, -dy); }
        int dot(Vec other) { return dx * other.dx + dy * other.dy; }
    }

    private record Segment(Point a, Point b) {
        static Segment normalized(Point p1, Point p2) {
            if (p1.x() < p2.x() || (p1.x() == p2.x() && p1.y() <= p2.y())) return new Segment(p1, p2);
            return new Segment(p2, p1);
        }

        boolean isHorizontal() { return a.y() == b.y(); }
        boolean isVertical() { return a.x() == b.x(); }

        int y() { return a.y(); } // para horizontal
        int x() { return a.x(); } // para vertical

        int minX() { return Math.min(a.x(), b.x()); }
        int maxX() { return Math.max(a.x(), b.x()); }
        int minY() { return Math.min(a.y(), b.y()); }
        int maxY() { return Math.max(a.y(), b.y()); }
    }

    private static final class EnrichedPoint {
        final Point base;

        final int x;
        final int y;

        final Vec v1;
        final Vec v2;

        AngleKind angle; // se asigna después

        final Set<Point> tl;
        final Set<Point> tr;
        final Set<Point> bl;
        final Set<Point> br;

        Set<Segment> top = Set.of();
        Set<Segment> bottom = Set.of();
        Set<Segment> left = Set.of();
        Set<Segment> right = Set.of();

        EnrichedPoint(
                Point base,
                Vec v1,
                Vec v2,
                Set<Point> tl,
                Set<Point> tr,
                Set<Point> bl,
                Set<Point> br
        ) {
            this.base = base;
            this.x = base.x();
            this.y = base.y();
            this.v1 = v1;
            this.v2 = v2;
            this.tl = tl;
            this.tr = tr;
            this.bl = bl;
            this.br = br;
        }

        Point add(Vec v) { return new Point(x + v.dx(), y + v.dy()); }
    }

    // -------------------- Step 1: build --------------------

    private List<EnrichedPoint> buildEnrichedPoints(List<Point> points) {
        // Para búsquedas rápidas por coord
        Set<Point> all = new HashSet<>(points);

        return points.stream()
                .map(p -> enrich(p, points, all))
                .collect(Collectors.toList());
    }

    private EnrichedPoint enrich(Point p, List<Point> points, Set<Point> all) {
        int x = p.x();
        int y = p.y();

        Set<Point> tl = new HashSet<>();
        Set<Point> tr = new HashSet<>();
        Set<Point> bl = new HashSet<>();
        Set<Point> br = new HashSet<>();

        for (Point q : points) {
            if (q.equals(p)) continue;
            if (q.x() < x && q.y() > y) tl.add(q);
            if (q.x() > x && q.y() > y) tr.add(q);
            if (q.x() < x && q.y() < y) bl.add(q);
            if (q.x() > x && q.y() < y) br.add(q);
        }

        Point verticalNeighbor = points.stream()
                .filter(q -> q.x() == x && q.y() != y)
                .min(Comparator.comparingInt(q -> Math.abs(q.y() - y)))
                .orElseThrow(() -> new IllegalArgumentException("No vertical neighbor for point " + p));

        Vec v1 = new Vec(0, verticalNeighbor.y() - y);

        Point horizontalNeighbor = points.stream()
                .filter(q -> q.y() == y && q.x() != x)
                .min(Comparator.comparingInt(q -> Math.abs(q.x() - x)))
                .orElseThrow(() -> new IllegalArgumentException("No horizontal neighbor for point " + p));

        Vec v2 = new Vec(horizontalNeighbor.x() - x, 0);

        return new EnrichedPoint(p, v1, v2, tl, tr, bl, br);
    }

    // -------------------- Step 2: angles --------------------

    private List<EnrichedPoint> assignAngles(List<EnrichedPoint> points) {
        Map<Point, EnrichedPoint> index = points.stream()
                .collect(Collectors.toMap(ep -> ep.base, ep -> ep));

        Point startKey = points.stream()
                .map(ep -> ep.base)
                .min(Comparator.comparingInt(Point::x).thenComparingInt(Point::y))
                .orElseThrow();

        EnrichedPoint start = index.get(startKey);
        start.angle = AngleKind.SHARP;

        int visited = 1;
        EnrichedPoint current = start;
        Dir dir = Dir.V1;
        AngleKind kind = AngleKind.SHARP;

        while (visited < points.size()) {
            Vec step = (dir == Dir.V1) ? current.v1 : current.v2;
            Point nextCoord = current.add(step);
            EnrichedPoint next = index.get(nextCoord);

            if (next == null) {
                throw new IllegalStateException("Broken contour: missing point at " + nextCoord);
            }

            AngleKind newKind = computeNextAngleKind(current, next, dir, kind);
            if (next.angle == null) visited++;
            next.angle = newKind;

            current = next;
            dir = (dir == Dir.V1) ? Dir.V2 : Dir.V1;
            kind = newKind;
        }

        return points;
    }

    private enum Dir { V1, V2 }

    private AngleKind computeNextAngleKind(EnrichedPoint cur, EnrichedPoint nxt, Dir dir, AngleKind currentKind) {
        boolean keep =
                (dir == Dir.V1 && cur.v2.dot(nxt.v2) >= 0) ||
                (dir == Dir.V2 && cur.v1.dot(nxt.v1) >= 0);

        if (keep) return currentKind;
        return (currentKind == AngleKind.SHARP) ? AngleKind.OBTUSE : AngleKind.SHARP;
    }

    // -------------------- Step 3: sides --------------------

    private List<EnrichedPoint> assignSides(List<EnrichedPoint> points) {
        Set<Segment> allSides = computeAllSides(points);

        for (EnrichedPoint p : points) {
            p.top = allSides.stream()
                    .filter(Segment::isHorizontal)
                    .filter(s -> s.y() > p.y)
                    .collect(Collectors.toSet());

            p.bottom = allSides.stream()
                    .filter(Segment::isHorizontal)
                    .filter(s -> s.y() < p.y)
                    .collect(Collectors.toSet());

            p.left = allSides.stream()
                    .filter(Segment::isVertical)
                    .filter(s -> s.x() < p.x)
                    .collect(Collectors.toSet());

            p.right = allSides.stream()
                    .filter(Segment::isVertical)
                    .filter(s -> s.x() > p.x)
                    .collect(Collectors.toSet());
        }
        return points;
    }

    private Set<Segment> computeAllSides(List<EnrichedPoint> points) {
        Set<Segment> sides = new HashSet<>();
        for (EnrichedPoint p : points) {
            sides.add(Segment.normalized(p.base, p.add(p.v1)));
            sides.add(Segment.normalized(p.base, p.add(p.v2)));
        }
        return sides;
    }

    // -------------------- Pair rules (allowed?) --------------------

    private boolean allowed(EnrichedPoint a, EnrichedPoint b) {
        EnrichedPoint[] sorted = sort(a, b);
        EnrichedPoint p1 = sorted[0];
        EnrichedPoint p2 = sorted[1];

        Vec v12 = new Vec(p2.x - p1.x, p2.y - p1.y);
        Vec v21 = v12.opposite();

        return insideVector(p1, v12)
                && insideVector(p2, v21)
                && !intersections(p1, p2)
                && noVertexInside(p1, p2);
    }

    private EnrichedPoint[] sort(EnrichedPoint a, EnrichedPoint b) {
        if (a.x < b.x || (a.x == b.x && a.y <= b.y)) return new EnrichedPoint[]{a, b};
        return new EnrichedPoint[]{b, a};
    }

    private boolean insideVector(EnrichedPoint p, Vec v) {
        if (p.angle == null) {
            throw new IllegalStateException("Angle not assigned for point " + p.base);
        }
        boolean between = betweenVectors(p, v);
        return (p.angle == AngleKind.SHARP) ? between : !between;
    }

    private boolean betweenVectors(EnrichedPoint p, Vec v) {
        return p.v1.dot(v) >= 0 && p.v2.dot(v) >= 0;
    }

    private boolean noVertexInside(EnrichedPoint p1, EnrichedPoint p2) {
        // si diagonal sube: interior sería (p1.tr ∩ p2.bl) vacío
        if (p1.y < p2.y) {
            return intersectionEmpty(p1.tr, p2.bl);
        }
        return intersectionEmpty(p1.br, p2.tl);
    }

    private boolean intersectionEmpty(Set<Point> a, Set<Point> b) {
        // iterar el menor
        if (a.size() > b.size()) return intersectionEmpty(b, a);
        for (Point p : a) if (b.contains(p)) return false;
        return true;
    }

    private boolean intersections(EnrichedPoint p1, EnrichedPoint p2) {
        EnrichedPoint[] sorted = sort(p1, p2);
        p1 = sorted[0];
        p2 = sorted[1];

        Set<Segment> horizontal =
                (p1.y < p2.y)
                        ? intersectSegments(p1.top, p2.bottom)
                        : intersectSegments(p1.bottom, p2.top);

        Set<Segment> vertical = intersectSegments(p1.right, p2.left);

        for (Segment s : concat(horizontal, vertical)) {
            if (intersectsDiagonal(p1.base, p2.base, s)) return true;
        }
        return false;
    }

    private Set<Segment> intersectSegments(Set<Segment> a, Set<Segment> b) {
        if (a.size() > b.size()) return intersectSegments(b, a);
        Set<Segment> out = new HashSet<>();
        for (Segment s : a) if (b.contains(s)) out.add(s);
        return out;
    }

    private List<Segment> concat(Set<Segment> a, Set<Segment> b) {
        List<Segment> out = new ArrayList<>(a.size() + b.size());
        out.addAll(a);
        out.addAll(b);
        return out;
    }

    private boolean intersectsDiagonal(Point p1, Point p2, Segment seg) {
        int x1 = p1.x(), y1 = p1.y();
        int x2 = p2.x(), y2 = p2.y();

        int dx = x2 - x1;
        int dy = y2 - y1;

        // diagonal degenerada => no interesa “cruzar” con lados
        if (dx == 0 && dy == 0) return false;

        if (seg.isHorizontal()) {
            // y = constante
            int y = seg.y();

            if (dy == 0) return false; // paralela

            double x = (dx / (double) dy) * (y - y1) + x1;
            return seg.minX() < x && x < seg.maxX();
        }

        if (seg.isVertical()) {
            int x = seg.x();

            if (dx == 0) return false; // paralela

            double y = (dy / (double) dx) * (x - x1) + y1;
            return seg.minY() < y && y < seg.maxY();
        }

        return false;
    }
}
