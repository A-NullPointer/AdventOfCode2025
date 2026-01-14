package software.aoc.day08.b.domain.model;

public record JunctionBox(long x, long y, long z) {
    public double distanceSquared(JunctionBox other) {
        double dx = x - other.x;
        double dy = y - other.y;
        double dz = z - other.z;
        return dx * dx + dy * dy + dz * dz;
    }
}