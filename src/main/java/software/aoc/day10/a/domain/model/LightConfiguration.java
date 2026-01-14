package software.aoc.day10.a.domain.model;

import java.util.List;

public record LightConfiguration(List<Light> lights) {
    public LightConfiguration {
        lights = List.copyOf(lights);
    }

    public int size() {
        return lights.size();
    }

    public Light getLight(int index) {
        return lights.get(index);
    }

    public boolean matches(LightConfiguration target) {
        return this.lights.equals(target.lights);
    }
}
