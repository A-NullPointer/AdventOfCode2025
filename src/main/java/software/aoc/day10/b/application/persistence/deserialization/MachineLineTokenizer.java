package software.aoc.day10.b.application.persistence.deserialization;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class MachineLineTokenizer {

    private static final Pattern LIGHTS = Pattern.compile("\\[[.#]+\\]");
    private static final Pattern BUTTON = Pattern.compile("\\([0-9,]+\\)");
    private static final Pattern SHAKES = Pattern.compile("\\{[0-9,]+\\}");

    public MachineLineTokens tokenize(String line) {
        return new MachineLineTokens(
                first(line, LIGHTS),
                all(line, BUTTON),
                first(line, SHAKES)
        );
    }

    private String first(String line, Pattern p) {
        return p.matcher(line).results().findFirst().map(MatchResult::group).orElseThrow();
    }

    private List<String> all(String line, Pattern p) {
        return p.matcher(line).results().map(MatchResult::group).toList();
    }
}
