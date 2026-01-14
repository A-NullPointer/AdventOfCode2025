package software.aoc.day03.a.application.persistence.io;

import software.aoc.day03.a.domain.model.Bank;
import software.aoc.day03.a.domain.persistence.io.BankReader;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


public class PlaintTextBankReader implements BankReader {

    private String filePath;

    public PlaintTextBankReader(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Bank> read() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(bank -> new Bank(bank))
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found: " + filePath, e);
        } catch (IOException e) {
            throw new UncheckedIOException("Error reading file: " + filePath, e);
        }
    }
}
