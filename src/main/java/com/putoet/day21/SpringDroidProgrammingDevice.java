package com.putoet.day21;

import com.putoet.intcode.*;

import java.util.ArrayList;
import java.util.List;

public class SpringDroidProgrammingDevice {
    private final List<Long> intCode;
    private final Memory memory;
    private final IntCodeInputOutputDevice input;
    private final IntCodeInputOutputDevice output;
    private final IntCodeDevice device;


    public SpringDroidProgrammingDevice(List<Long> intCode) {
        this.intCode = intCode;

        memory = new ExpandableMemory(intCode);
        input = new IntCodeInputOutputDevice();
        output = new IntCodeInputOutputDevice();
        device = IntCodeComputer.builder().memory(memory).input(input).output(output).resumable().build();
    }

    public List<String> springDroid(List<String> program) {
        for (String instruction : program) {
            instruction.chars().forEach(input::offer);
            input.offer('\n');
        }
        device.run();

        return outputAsStringList();
    }

    private List<String> outputAsStringList() {
        final List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (Long l : output.asList()) {
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
