package software.aoc.day05.a.application.persistence.io;

import software.aoc.day05.a.application.persistence.deserialization.SpliterByBlankLine;
import software.aoc.day05.a.domain.model.IdRange;
import software.aoc.day05.a.domain.model.IngredientId;
import software.aoc.day05.a.domain.persistence.deserialization.Parser;
import software.aoc.day05.a.domain.persistence.io.Reader;

import java.util.List;

public class Loader {

    private Reader reader;
    private Parser IdRangeRarser;
    private Parser IngredientIdsParser;
    private SpliterByBlankLine spliter;

    // mirar si hacder builder
    public Loader(Reader reader, Parser IdRangeRarser, Parser IngredientIdsParser, SpliterByBlankLine spliter) {
        this.reader = reader;
        this.IdRangeRarser = IdRangeRarser;
        this.IngredientIdsParser = IngredientIdsParser;
        this.spliter = spliter;
        obtainObjects();
    }

    // Devuelvo una lista inmutable o copia
    private void obtainObjects() {
        spliter.split(reader.read());
    }

    public List<IdRange> obtainIdRanges() {
        return IdRangeRarser.parse(spliter.IdRanges());
    }

    public List<IngredientId> obtainIngredientIds() {
        return IngredientIdsParser.parse(spliter.IngredientIds());
    }


}
