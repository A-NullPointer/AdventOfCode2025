package software.aoc.day10.a.application.persistence.deserialization;

import software.aoc.day10.a.domain.model.Button;
import software.aoc.day10.a.domain.model.Light;
import software.aoc.day10.a.domain.model.LightConfiguration;
import software.aoc.day10.a.domain.model.Machine;
import software.aoc.day10.a.domain.persistence.deserialization.Parser;

import java.util.List;
import java.util.stream.IntStream;

public class MachineParser implements Parser {

    private final MachineLineTokenizer tokenizer;

    public MachineParser() {
        this(new MachineLineTokenizer());
    }

    public MachineParser(MachineLineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public List<Machine> parse(List lines) {
        return ((List<String>) lines).stream()
                .filter(l -> !l.isBlank())
                .map(this::parseLine)
                .toList();
    }

    private Machine parseLine(String line) {
        MachineLineTokens tokens = tokenizer.tokenize(line);
        return new Machine(
                parseLights(tokens.lightsToken()),
                parseButtons(tokens.buttonTokens()),
                parseNumbers(tokens.shakesToken())
        );
    }

    private LightConfiguration parseLights(String token) {
        String raw = strip(token);
        List<Light> lights = raw.chars()
                .mapToObj(c -> Light.fromSymbol((char) c))
                .toList();
        return new LightConfiguration(lights);
    }

    private List<Button> parseButtons(List<String> buttonTokens) {
        return IntStream.range(0, buttonTokens.size())
                .mapToObj(i -> new Button(i, parseNumbers(buttonTokens.get(i))))
                .toList();
    }

    private List<Integer> parseNumbers(String token) {
        String raw = strip(token);
        if (raw.isBlank()) return List.of();
        return List.of(raw.split(",")).stream().map(String::trim).map(Integer::parseInt).toList();
    }

    private String strip(String token) {
        return token.substring(1, token.length() - 1);
    }
}
