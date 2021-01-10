package com.putoet.day7;

import com.putoet.resources.CSV;
import com.putoet.statistics.Permutator;

import java.io.IOException;
import java.util.List;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

public class Day7 {
    public static void main(String[] args) {
        final List<Integer> intCodeProgram = CSV.list("/day7.txt", Integer::parseInt).get(0);
        final Permutator<Integer> permutator = new Permutator<>();

        List<List<Integer>> phaseSettings = permutator.permute(List.of(0, 1, 2, 3, 4));
        part(intCodeProgram, phaseSettings, "simple amplifier", Day7::simpleAmplifier);

        phaseSettings = permutator.permute(List.of(5, 6, 7, 8, 9));
        part(intCodeProgram, phaseSettings, "feedback amplifier", Day7::feedbackAmplifier);
    }

    private static void part(List<Integer> intCodeProgram,
                             List<List<Integer>> phaseSettings,
                             String name,
                             BiFunction<List<Integer>, PhaseSetting, OptionalLong> applifier) {
        final OptionalLong max = phaseSettings.stream()
                .map(PhaseSetting::new)
                .map(phaseSetting -> applifier.apply(intCodeProgram, phaseSetting))
                .filter(OptionalLong::isPresent)
                .mapToLong(OptionalLong::getAsLong)
                .max();

        if (max.isPresent())
            System.out.println("the highest signal calculated, using the " + name +
                    ", that can be sent to the thrusters is " + max.getAsLong());
        else
            System.out.println("No max available...");
    }

    public static OptionalLong simpleAmplifier(List<Integer> intCodeProgram, PhaseSetting phaseSetting) {
        final AmplifierArray amplifierArray = new SimpleAmplifierArray(intCodeProgram, phaseSetting);

        amplifierArray.input(0);
        amplifierArray.run();

        return amplifierArray.output(100, TimeUnit.MILLISECONDS);
    }

    public static OptionalLong feedbackAmplifier(List<Integer> intCodeProgram, PhaseSetting phaseSetting) {
        final AmplifierArray amplifierArray = new FeedbackAmplifierArray(intCodeProgram, phaseSetting);

        amplifierArray.input(0);
        amplifierArray.run();

        return amplifierArray.output(100, TimeUnit.MILLISECONDS);
    }
}



