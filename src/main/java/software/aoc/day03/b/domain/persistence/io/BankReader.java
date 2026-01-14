package software.aoc.day03.b.domain.persistence.io;

import software.aoc.day03.b.domain.model.Bank;

import java.util.List;

public interface BankReader {
    List<Bank> read();
}
