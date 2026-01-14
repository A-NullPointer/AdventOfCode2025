package software.aoc.day08.b.application.persistence.deserialization;

import software.aoc.day08.b.domain.model.JunctionBox;
import software.aoc.day08.b.domain.persistence.deserialization.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JunctionBoxParser implements Parser<JunctionBox> {

    private final List<JunctionBox> junctionBoxes;

    public JunctionBoxParser() {
        junctionBoxes = new ArrayList<>();
    }

    @Override
    public List<JunctionBox> parse(List<String> lines) {

        lines.forEach(line -> junctionBoxes.add(split(line)));
        return Collections.unmodifiableList(junctionBoxes);
    }

    private JunctionBox split(String ranges){
        //refactorizar
        String[] split = ranges.split(",");
        // mejorar porque la linea es super larga
        return new JunctionBox(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2]));
    }

}
