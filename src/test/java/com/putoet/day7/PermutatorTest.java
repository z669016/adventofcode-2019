package com.putoet.day7;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PermutatorTest {

    @Test
    public void permute() {
        Permutator<Integer> permutator = new Permutator<>();
        List<List<Integer>> list = permutator.permute(List.of(1, 2, 3));
        assertEquals(List.of(
                List.of(1, 2, 3),
                List.of(1, 3, 2),
                List.of(2, 1, 3),
                List.of(2, 3, 1),
                List.of(3, 1, 2),
                List.of(3, 2, 1)), list);
    }
}