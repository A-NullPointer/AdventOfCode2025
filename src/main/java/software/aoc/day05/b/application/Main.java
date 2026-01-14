package software.aoc.day05.b.application;

import software.aoc.day05.b.application.control.FreshnessChecker;
import software.aoc.day05.b.application.control.FreshnessPosibleIdsCounter;
import software.aoc.day05.b.application.persistence.deserialization.IdRangeParser;
import software.aoc.day05.b.application.persistence.deserialization.IngredientIdParser;
import software.aoc.day05.b.application.persistence.deserialization.SpliterByBlankLine;
import software.aoc.day05.b.application.persistence.io.Loader;
import software.aoc.day05.b.application.persistence.io.PlainTextReader;
import software.aoc.day05.b.domain.model.IdRange;
import software.aoc.day05.b.domain.model.IngredientId;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String path = "src/main/resources/day06/inputs.txt";

        PlainTextReader reader = new PlainTextReader(path);
        IdRangeParser idRangeParser = new IdRangeParser();
        IngredientIdParser ingredientIdParser = new IngredientIdParser();
        SpliterByBlankLine spliterByBlankLine = new SpliterByBlankLine();
        Loader loader = new Loader(reader, idRangeParser, ingredientIdParser, spliterByBlankLine);

        List<IdRange> idRanges = loader.obtainIdRanges();
        List<IngredientId> ingredientIds = loader.obtainIngredientIds();

        System.out.println("Id ranges:" + idRanges);
        System.out.println("Ingredient ids:" + ingredientIds);

        // Verificar frescura
        FreshnessChecker checker = new FreshnessChecker(idRanges);
        long freshCount = checker.countFresh(ingredientIds);
        System.out.println("\nFresh ingredients count: " + freshCount);

        FreshnessPosibleIdsCounter freshnessPosibleIdsCounter = new FreshnessPosibleIdsCounter(idRanges);
        long freshPossibleIds = freshnessPosibleIdsCounter.howManyFreshIds();
        System.out.println("\nIds frescos posibles: " + freshPossibleIds);
    }
}
