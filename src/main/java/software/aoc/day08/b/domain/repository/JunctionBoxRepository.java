package software.aoc.day08.b.domain.repository;

import software.aoc.day08.b.domain.model.JunctionBox;

import java.util.List;

public interface JunctionBoxRepository {
    List<JunctionBox> findAll();
}