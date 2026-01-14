package software.aoc.day10.a.application.persistence.deserialization;

import java.util.List;

public record MachineLineTokens(
        String lightsToken,
        List<String> buttonTokens,
        String shakesToken
) {}
