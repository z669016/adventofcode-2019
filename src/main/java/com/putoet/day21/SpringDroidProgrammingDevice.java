package com.putoet.day21;

import com.putoet.intcode.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpringDroidProgrammingDevice {
    private final IntCodeInputOutputDevice input;
    private final IntCodeInputOutputDevice output;
    private final IntCodeDevice device;


    public SpringDroidProgrammingDevice(@NotNull List<Long> intCode) {
        var memory = new ExpandableMemory(intCode);
        input = new IntCodeInputOutputDevice();
        output = new IntCodeInputOutputDevice();
        device = IntCodeComputer.builder().memory(memory).input(input).output(output).resumable().build();
    }

    public List<String> springDroid(@NotNull List<String> program) {
        for (var instruction : program) {
            instruction.chars().forEach(input::offer);
            input.offer('\n');
        }
        device.run();

        return outputAsStringList();
    }

    private List<String> outputAsStringList() {
        final var result = new ArrayList<String>();
        var sb = new StringBuilder();
        for (var l : output.asList()) {
            if (l >= 0 && l < 128) {
                if (l == 10) {
                    result.add(sb.toString());
                    sb = new StringBuilder();
                    continue;
                }

                sb.append(Character.valueOf((char) l.byteValue()));
            } else {
                sb.append(l);
            }
        }

        result.add(sb.toString());
        return result;
    }
}
