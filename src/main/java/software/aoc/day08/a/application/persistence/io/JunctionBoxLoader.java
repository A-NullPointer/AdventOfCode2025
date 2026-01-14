package software.aoc.day08.a.application.persistence.io;
import software.aoc.day08.a.domain.model.JunctionBox;
import software.aoc.day08.a.domain.repository.JunctionBoxRepository;

import java.util.List;

public class JunctionBoxLoader {
    private final JunctionBoxRepository repository;

    public JunctionBoxLoader(JunctionBoxRepository repository) {
        this.repository = repository;
    }

    public List<JunctionBox> load() {
        return repository.findAll();
    }
}