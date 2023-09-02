package com.putoet.day14;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

record ChemicalReaction(@NotNull List<Ingredient> ingredients, @NotNull Ingredient result) {

    public Set<Chemical> chemicals() { return ingredients.stream().map(Ingredient::chemical).collect(Collectors.toSet()); }

    public static ChemicalReaction of(@NotNull String reaction) {
        final var parts = reaction.split(" => ");
        if (parts.length != 2)
            throw new IllegalArgumentException("Not a valid reaction definition (" + reaction + ")");

        try {
            return of(parts[1], parts[0]);
        } catch (RuntimeException exc) {
            throw new IllegalArgumentException("Failed to create chemical reaction for '" + reaction + "'", exc);
        }
    }

    private static ChemicalReaction of(String resultString, String reaction) {
        final var result = Ingredient.of(resultString);
        final var ingredientList = Ingredient.ofList(reaction);
        return new ChemicalReaction(ingredientList, result);
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        ingredients.forEach(ingredient -> sb.append(ingredient.amount()).append(" ").append(ingredient.chemical()).append(", "));
        return sb.substring(0, sb.length() - 2) + " => " + result.amount() + " " + result.chemical();
    }

    public ChemicalReaction multiplyBy(long factor) {
        return new ChemicalReaction(ingredients.stream().map(i -> i.multiplyBy(factor)).toList(), result.multiplyBy(factor));
    }
}
