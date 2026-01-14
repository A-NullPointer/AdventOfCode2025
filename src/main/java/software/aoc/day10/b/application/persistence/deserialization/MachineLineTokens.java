package software.aoc.day10.b.application.persistence.deserialization;

import java.util.List;

public record MachineLineTokens(
        String lightsToken,
        List<String> buttonTokens,
        String shakesToken
) {}
