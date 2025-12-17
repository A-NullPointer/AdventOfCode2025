package software.aoc.day01.a.model;

public class Dial {
    private final int position;
    private final int max;
    private final int min;

    public Dial(int position, int max, int min) {
        this.position = position;
        this.max = max;
        this.min = min;
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
