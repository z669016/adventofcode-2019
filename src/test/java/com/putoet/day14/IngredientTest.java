package com.putoet.day14;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class IngredientTest {
    private Chemical a = new Chemical("a");
    private Chemical b = new Chemical("b");
    private Chemical c = new Chemical("c");

    @Test
    public void testOf() {
        assertEquals(new Ingredient(a), Ingredient.of("1 a"));
        assertEquals(new Ingredient(b, 2), Ingredient.of("2 b"));
        assertEquals(new Ingredient(c, 3), Ingredient.of("   3 c    "));
    }

    @Test
    public void ofList() {
        assertEquals(List.of(new Ingredient(a), new Ingredient(b, 2), new Ingredient(c, 3)), Ingredient.ofList("1 a, 2 b, 3 c"));
    }
}