package com.putoet.day23;

import com.putoet.resources.CSV;

import java.util.List;

public class Day23 {
    public static void main(String[] args) {
        final List<Long> intCode = CSV.flatList("/day23.txt", Long::parseLong);
        part1(intCode);
        part2(intCode);
    }

    private static void part1(List<Long> intCode) {
        final Network network = new Network(intCode);
        network.start();

        while (network.invalid().isEmpty());
        network.stop();

        System.out.println("First invalid package: " + network.invalid().get());
    }

    private static void part2(List<Long> intCode) {
        final Network network = new Network(intCode);
        final NotAlwaysTransmitting nat = new NotAlwaysTransmitting(network);
        network.nat(nat);

        network.start();
        nat.start();

        while (nat.twice().isEmpty());
        nat.stop();
        network.stop();

        System.out.println("First package send by NAT twice is: " + nat.twice().get());

        // 17803 is too high
    }
}
