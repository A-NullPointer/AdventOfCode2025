package software.aoc.day08.a.domain.repository;

import software.aoc.day08.a.domain.model.JunctionBox;
import java.util.List;

public interface JunctionBoxRepository {
    List<JunctionBox> findAll();
}