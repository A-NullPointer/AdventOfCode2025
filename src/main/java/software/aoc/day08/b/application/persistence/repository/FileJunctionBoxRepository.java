package software.aoc.day08.b.application.persistence.repository;


import software.aoc.day08.b.application.persistence.deserialization.JunctionBoxParser;
import software.aoc.day08.b.application.persistence.io.PlainTextReader;
import software.aoc.day08.b.domain.model.JunctionBox;
import software.aoc.day08.b.domain.repository.JunctionBoxRepository;

import java.util.List;

public class FileJunctionBoxRepository implements JunctionBoxRepository {
    private final String filePath;

    public FileJunctionBoxRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<JunctionBox> findAll() {
        PlainTextReader reader = new PlainTextReader(filePath);
        JunctionBoxParser parser = new JunctionBoxParser();
        return parser.parse(reader.read().toList());
    }
}