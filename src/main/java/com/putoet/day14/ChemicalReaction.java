package com.putoet.day14;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChemicalReaction {
    private final List<Ingredient> inputChemicals;
    private final Ingredient outputChemical;

    public ChemicalReaction(Ingredient outputChemical, List<Ingredient> inputChemicals) {
        this.outputChemical = outputChemical;
        this.inputChemicals = inputChemicals;
    }

    public Ingredient result() { return outputChemical; }

    public List<Ingredient> ingredients() { return inputChemicals; }

    public Set<Chemical> chemicals() { return inputChemicals.stream().map(Ingredient::chemical).collect(Collectors.toSet()); }

    public static ChemicalReaction of(String reaction) {
        final String[] parts = reaction.split(" => ");
        if (parts.length != 2)
            throw new IllegalArgumentException("Not a valid reaction definition (" + reaction + ")");

        try {
            return of(parts[1], parts[0]);
        } catch (RuntimeException exc) {
            throw new IllegalArgumentException("Failed to create chemical reaction for '" + reaction + "'", exc);
        }
    }

    private static ChemicalReaction of(String resultString, String reaction) {
        final Ingredient result = Ingredient.of(resultString);
        final List<Ingredient> ingredientList = Ingredient.ofList(reaction);
        return new ChemicalReaction(result, ingredientList);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        inputChemicals.forEach(ingredient -> sb.append(ingredient.amount()).append(" ").append(ingredient.chemical()).append(", "));
        return sb.toString().substring(0, sb.length() - 2) + " => " + outputChemical.amount() + " " + outputChemical.chemical();
    }

    public ChemicalReaction multiplyBy(long factor) {
        return new ChemicalReaction(outputChemical.multiplyBy(factor), inputChemicals.stream().map(i -> i.multiplyBy(factor)).collect(Collectors.toList()));
    }
}
