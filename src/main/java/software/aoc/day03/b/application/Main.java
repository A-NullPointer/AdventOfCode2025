package software.aoc.day03.b.application;

import software.aoc.day03.b.application.control.Solver;
import software.aoc.day03.b.application.persistence.io.PlaintTextBankReader;
import software.aoc.day03.b.domain.persistence.io.BankReader;

public class Main {
    public static void main(String[] args) {

        String path = "src/main/resources/day03/inputs.txt";

        BankReader reader = new PlaintTextBankReader(path);

        Solver solver = new Solver(reader);
        long solve = solver.solve();

        System.out.println(solve);

    }
}
