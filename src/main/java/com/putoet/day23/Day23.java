package com.putoet.day23;

import com.putoet.resources.CSV;
import com.putoet.utils.Timer;

import java.util.List;

public class Day23 {
    public static void main(String[] args) {
        final var intCode = CSV.flatList("/day23.txt", Long::parseLong);
        Timer.run(() -> part1(intCode));
        Timer.run(() -> part2(intCode));
    }

    private static void part1(List<Long> intCode) {
        final var network = new Network(intCode);
        network.start();

        //noinspection StatementWithEmptyBody
        while (network.invalid().isEmpty());

        network.stop();

        System.out.println("First invalid package: " + network.invalid().get());
    }

    private static void part2(List<Long> intCode) {
        final var network = new Network(intCode);
        final var nat = new NotAlwaysTransmitting(network);
        network.nat(nat);

        network.start();
        nat.start();

        //noinspection StatementWithEmptyBody
        while (nat.twice().isEmpty());

        nat.stop();
        network.stop();

        System.out.println("First package send by NAT twice is: " + nat.twice().get());
    }
}
