package software.aoc.day01.b.model;

public class Dial {
    private final int position;
    private final int min;
    private final int max;

    public Dial(int position, int min, int max) {
        this.position = position;
        this.min = min;
        this.max = max;
    }

    public int getPosition() {
        return position;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}
