package software.aoc.day01.b.application;

import software.aoc.day01.b.model.Dial;
import software.aoc.day01.b.model.Rotation;

import java.util.List;
import java.util.stream.IntStream;

public class Operation {

    private final List<Rotation> rotations;
    private final Dial dial;
    private int currentPosition;
    private final int range;
    private int sequence;

    public Operation(List<Rotation> rotations, Dial dial) {
        this.rotations = rotations;
        this.dial = dial;
        this.currentPosition = dial.getPosition();
        this.range = dial.getMax() - dial.getMin() + 1;
        this.sequence = 0;
    }

    public int obtainSequence() {
        rotations.forEach(this::applyRotationStepByStep);
        return sequence;
    }

    private void applyRotationStepByStep(Rotation rotation) {
        int delta = rotation.direction().getDelta();

        IntStream.range(0, rotation.times())
                .forEach(i -> executeStep(delta));
    }

    private void executeStep(int delta) {
        currentPosition += delta;
        currentPosition = normalize(currentPosition);

        if (isAtMinimum()) {
            incrementSequenceCounter();
        }
    }

    private void incrementSequenceCounter(){
        this.sequence++;
    }

    private int normalize(int position) {
        int min = dial.getMin();
        int offset = position - min;
        int normalized = ((offset % range) + range) % range;
        return normalized + min;
    }

    private boolean isAtMinimum() {
        return currentPosition == dial.getMin();
    }
}
