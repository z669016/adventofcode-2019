package com.putoet.day21;

import com.putoet.resources.CSV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class SpringDroidProgrammingDeviceTest {
    private List<Long> intCode;

    @BeforeEach
    void setup() {
        intCode = CSV.flatList("/day21.txt", Long::parseLong);
    }

    @Test
    void springDroid() {
        // .................
        // .................
        // @................
        // #####.#..########
        final SpringDroidProgrammingDevice device = new SpringDroidProgrammingDevice(intCode);
        final List<String> output = device.springDroid(List.of(
                "NOT A J",
                "NOT C T",
                "OR T J",
                "AND D J",
                "WALK"
        ));

        output.forEach(System.out::println);
    }
}