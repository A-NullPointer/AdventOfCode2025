package software.aoc.day01.b.model;

public enum Direction{
    L( -1),
    R( 1);

    private final int delta;

    Direction(int delta) {
        this.delta = delta;
    }

    public int getDelta() {
        return delta;
    }
}