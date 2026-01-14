package software.aoc.day02.a.application.validator;

import software.aoc.day02.a.domain.validators.IdValidator;

// TODO: Elegir un mejor nombre para la clase
public class RepeatedPatternIdValidator implements IdValidator {
    @Override
    public boolean isInvalid(long id) {
        int digits = (int) Math.log10(id) + 1;
        if (digits % 2 != 0) return false;

        long divisor = (long) Math.pow(10, digits / 2);
        long firstHalf = id / divisor;
        long secondHalf = id % divisor;

        return firstHalf == secondHalf;
    }
}
