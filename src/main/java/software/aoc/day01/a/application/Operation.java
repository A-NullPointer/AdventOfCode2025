package software.aoc.day01.a.application;

import software.aoc.day01.a.model.Dial;
import software.aoc.day01.a.model.Rotation;

import java.util.List;

public class Operation {

    public List<Rotation> rotations;
    public Dial dial;
    private int currentPosition;
    private final int offLimit;
    private int sequence;

    public Operation(List<Rotation> rotations, Dial dial) {
        this.rotations = rotations;
        this.dial = dial;
        this.currentPosition = dial.getPosition();
        this.offLimit = dial.getMax() + 1;
        this.sequence = 0;
    }

    public int obtainSequence(){
        for (Rotation rotation : this.rotations) {

            System.out.println(this.currentPosition + " " + rotation.direction().getDelta() + " " + rotation.times() + " " + this.sequence);

            this.currentPosition = (this.currentPosition + (rotation.direction().getDelta() * rotation.times()));

            if(this.currentPosition == this.dial.getMin()){
                sequence++;
            }
            while(this.currentPosition < this.dial.getMin()){
                this.currentPosition = (this.offLimit) + this.currentPosition;
                if(this.currentPosition == this.dial.getMin()){
                    sequence++;
                }
                //sequence++;
            }
            while(this.currentPosition > this.dial.getMax()){
                this.currentPosition = this.currentPosition - (this.offLimit);
                if(this.currentPosition == this.dial.getMin()){
                    sequence++;
                }
                //sequence++;
            }
        }
        return sequence;
    }
}
