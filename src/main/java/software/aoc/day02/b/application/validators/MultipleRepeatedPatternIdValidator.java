package software.aoc.day02.b.application.validators;

import software.aoc.day02.b.domain.validators.IdValidator;

import java.util.stream.IntStream;

// Devide and Conquer
public class MultipleRepeatedPatternIdValidator implements IdValidator {
    @Override
    public boolean isInvalid(long id) {
        String str = String.valueOf(id);

        return IntStream.rangeClosed(1, str.length() / 2)
                .filter(len -> str.length() % len == 0)
                .anyMatch(len -> str.equals(
                        str.substring(0, len).repeat(str.length() / len)
                ));
    }
}
