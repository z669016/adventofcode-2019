package com.putoet.day14;

import org.jetbrains.annotations.NotNull;

import java.util.*;

record Ingredient(@NotNull Chemical chemical, long amount) {

    public static Ingredient of(@NotNull String ingredient) {
        final var elements = ingredient.trim().split(" ");
        try {
            return new Ingredient(new Chemical(elements[1]), Integer.parseInt(elements[0].trim()));
        } catch (RuntimeException exc) {
            throw new IllegalArgumentException("Invalid ingredient (" + ingredient + ")", exc);
        }
    }

    public static List<Ingredient> ofList(@NotNull String reaction) {
        final var list = new ArrayList<Ingredient>();
        final var ingredients = reaction.trim().split(",");
        if (ingredients.length == 0)
            throw new IllegalArgumentException("Ingredients list is empty");

        Arrays.stream(ingredients).forEach(s -> list.add(Ingredient.of(s)));
        return Collections.unmodifiableList(list);
    }


    public Ingredient multiplyBy(long factor) {
        if (factor <= 0)
            throw new IllegalArgumentException("Factor cannot be smallert han  or equal to 0.");

        return new Ingredient(chemical, amount * factor);
    }

    @Override
    public String toString() {
        return amount() + " " + chemical();
    }
}
