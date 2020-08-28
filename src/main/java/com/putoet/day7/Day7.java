package com.putoet.day7;

import com.putoet.resources.CSV;

import java.io.IOException;
import java.util.List;

public class Day7 {
    public static void main(String[] args) throws IOException {
        System.out.println("[Day7 version 2.0]");

        final List<Integer> intCode = CSV.list("/day7.txt", Integer::parseInt).get(0);

        // Part one
        List<List<Integer>> sequences = new Permutator<Integer>().permute(List.of(0, 1, 2, 3, 4));

        Integer max = sequences.stream().map(sequence -> {
            final AmplifierArray amplifierArray = AmplifierArray.builder()
                    .intCode(intCode)
                    .sequence(sequence)
                    .inputSignal(0)
                    .build();

            return amplifierArray.run();

        }).max(Integer::compare).get();

        System.out.println("Available output for 7a is " + max);

        // Part two
        sequences = new Permutator<Integer>().permute(List.of(5, 6, 7, 8, 9));

        max = sequences.stream().map(sequence -> {
            final AmplifierArray amplifierArray = FeedbackAmplifierArray.builder()
                    .intCode(intCode)
                    .sequence(sequence)
                    .inputSignal(0)
                    .build();

            return amplifierArray.run();

        }).max(Integer::compare).get();

        System.out.println("Available output for 7b is " + max);
    }
}



