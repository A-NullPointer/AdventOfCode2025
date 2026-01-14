package software.aoc.day08.a.application.persistence.repository;


import software.aoc.day08.a.domain.model.JunctionBox;
import software.aoc.day08.a.domain.repository.JunctionBoxRepository;
import software.aoc.day08.a.application.persistence.deserialization.JunctionBoxParser;
import software.aoc.day08.a.application.persistence.io.PlainTextReader;

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