package software.aoc.day03.a.domain.persistence.io;

import software.aoc.day03.a.domain.model.Bank;

import java.util.List;

public interface BankReader {
    List<Bank> read();
}
