package software.aoc.day03.b.application.control;

import software.aoc.day03.b.domain.model.Bank;
import software.aoc.day03.b.domain.persistence.io.BankReader;

import java.util.List;

// TODO: Elejir en qué capa de la arquitectura debería de estar
public class Solver {
    private final BankReader reader;

    public Solver(BankReader reader) {
        this.reader = reader;
    }
    // ✓ DIP: depende de abstracciones

    public long solve() {
        List<Bank> banks = reader.read();
        return calculateSumOfbatteries(banks);
    }

    private long calculateSumOfbatteries(List<Bank> banks) {
        return banks.stream()
                .mapToLong(Bank::getLargestJoltage)
                .sum();
    }
}
