package software.aoc.day10.b.domain.model;

import java.util.List;

public record Machine(
        LightConfiguration targetConfiguration,
        List<Button> buttons,
        List<Integer> shakeRequirements // se parsea pero el solver de part1 la ignora
) {
    public Machine {
        buttons = List.copyOf(buttons);
        shakeRequirements = List.copyOf(shakeRequirements);
    }

    public int getLightCount() {
        return targetConfiguration.size();
    }

    public int getButtonCount() {
        return buttons.size();
    }
}
