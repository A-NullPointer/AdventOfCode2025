package software.aoc.day03.a.application;

import software.aoc.day03.a.application.control.Solver;
import software.aoc.day03.a.application.persistence.io.PlaintTextBankReader;
import software.aoc.day03.a.domain.persistence.io.BankReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        String path = "src/main/resources/day03/inputs.txt";

        BankReader reader = new PlaintTextBankReader(path);

        Solver solver = new Solver(reader);
        long solve = solver.solve();

        System.out.println(solve);

    }
}
