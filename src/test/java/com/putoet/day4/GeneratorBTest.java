package com.putoet.day4;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneratorBTest {

    @Test
    public void testGeneratorB() {
        var generatorB = new GeneratorB(112233, 999999);
        assertEquals("112233", generatorB.next().toString());

        generatorB = new GeneratorB(123444, 999999);
        assertEquals("123445", generatorB.next().toString());

        generatorB = new GeneratorB(111122, 999999);
        assertEquals("111122", generatorB.next().toString());
    }
}
