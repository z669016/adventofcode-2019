package com.putoet.day14;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IngredientTest {
    private final Chemical a = new Chemical("a");
    private final Chemical b = new Chemical("b");
    private final Chemical c = new Chemical("c");

    @Test
    public void testOf() {
        assertEquals(new Ingredient(a, 1), Ingredient.of("1 a"));
        assertEquals(new Ingredient(b, 2), Ingredient.of("2 b"));
        assertEquals(new Ingredient(c, 3), Ingredient.of("   3 c    "));
    }

    @Test
    public void ofList() {
        assertEquals(List.of(new Ingredient(a, 1), new Ingredient(b, 2), new Ingredient(c, 3)), Ingredient.ofList("1 a, 2 b, 3 c"));
    }

    @Test
    public void testMultiplyBy() {
        assertThrows(IllegalArgumentException.class, () -> Ingredient.of("3 c").multiplyBy(0));
        assertEquals(new Ingredient(c, 9), Ingredient.of("3 c").multiplyBy(3));
    }
}