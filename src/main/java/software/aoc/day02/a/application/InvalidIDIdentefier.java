package software.aoc.day02.a.application;

import software.aoc.day02.a.model.IdRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

public class InvalidIDIdentefier {

    public List<IdRange> rangesOfIds;
    public List<Long> invalidIds;

    public InvalidIDIdentefier(List<IdRange> rangesOfIds) {
        this.rangesOfIds = rangesOfIds;
        this.invalidIds = new ArrayList<>();
    }

    public Optional<Long> identifyInvalidIds(){
        rangesOfIds.stream()
                .forEach(i -> findeInvalidIDsBetween(i.firstId().Id(), i.lastId().Id()));
        return addInvalidsIds(this.invalidIds);
    }

    private Optional<Long> addInvalidsIds(List<Long> invalidIds) {
        return invalidIds.stream()
                .reduce((a,b) -> a + b);
    }

    private void findeInvalidIDsBetween(long min, long max) {
        LongStream.rangeClosed(min, max)
                .filter(this::isRepeatedPattren)
                .forEach(l -> invalidIds.add(l));
    }

    private boolean isRepeatedPattren(long l) {
        String str = String.valueOf(l);
        int len = str.length();
        String firstHalf = str.substring(0, len/2);
        String secondHalf = str.substring(len/2);
        return firstHalf.equals(secondHalf);
    }
}
