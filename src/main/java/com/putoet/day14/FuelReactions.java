package com.putoet.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class FuelReactions {
    private final Map<Chemical, ChemicalReaction> map = new HashMap<>();
    private  List<Chemical> replacementOrder;

    public FuelReactions(List<ChemicalReaction> chemicalReactions) {
        chemicalReactions.forEach(reaction -> map.put(reaction.result().chemical(), reaction));
    }

    public static List<ChemicalReaction> loadFile(String fileName) {
        try {
            return Files.lines(Paths.get(fileName)).map(ChemicalReaction::of).collect(Collectors.toList());
        } catch (IOException exc) {
            System.out.println("Could not load file " + fileName);
        }
        return List.of();
    }

    public Map<Chemical, ChemicalReaction> chemicalReactions() {
        return Collections.unmodifiableMap(map);
    }

    public Optional<ChemicalReaction> reactionFor(Chemical chemical) {
        return Optional.ofNullable(map.get(chemical));
    }

    public Optional<ChemicalReaction> reactionForFuel() {
        return Optional.of(map.get(Chemical.FUEL));
    }

    public Optional<ChemicalReaction> simplifyChemicalReaction(ChemicalReaction reaction) {
        replacementOrder = new ArrayList<>();

        Optional<Chemical> chemical = nextToReplace(reaction);
        while (chemical.isPresent()) {
            replacementOrder.add(chemical.get());
            Optional<ChemicalReaction> newReaction = replaceChemicalInReaction(reaction, chemical.get());
            if (newReaction.isEmpty())
                return Optional.empty();

            reaction = newReaction.get();
            chemical = nextToReplace(reaction);
        }

        System.out.println("Number of replacements is " + replacementOrder.size());

        return Optional.of(reaction);
    }

    public Optional<Chemical> nextToReplace(ChemicalReaction reaction) {
        final Set<Chemical> topLevelChemicals = reaction.ingredients().stream()
                .map(Ingredient::chemical)
                .filter(chemical -> !chemical.equals(Chemical.ORE))
                .collect(Collectors.toSet());
        final Set<Chemical> independentChemicals = topLevelChemicals.stream().filter(chemical -> noneIsDependingOn(topLevelChemicals, chemical)).collect(Collectors.toSet());
        return independentChemicals.stream().findFirst();
    }

   public Set<Chemical> baseChemicals() {
        return map.entrySet().stream()
                .filter(e -> e.getValue().ingredients().stream().allMatch(i -> i.chemical().equals(Chemical.ORE)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    private boolean noneIsDependingOn(Set<Chemical> chemicals, Chemical chemical) {
        return chemicals.stream()
                .filter(c -> !c.equals(chemical) && !c.equals(Chemical.FUEL) && !c.equals(Chemical.ORE))
                .map(this::reactionFor)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .noneMatch(cr -> cr.ingredients().stream().anyMatch(i -> i.chemical().equals(chemical)));
    }

    public Optional<ChemicalReaction> replaceChemicalInReaction(ChemicalReaction chemicalReaction, Chemical chemicalToReplace) {
        final Optional<Ingredient> ingredientToReplace = chemicalReaction.ingredients().stream().filter(i -> i.chemical().equals(chemicalToReplace)).findFirst();
        if (ingredientToReplace.isPresent()) {
            final List<Ingredient> ingredientList = replaceIngredientInList(ingredientToReplace.get(), chemicalReaction.ingredients());
            return Optional.of(new ChemicalReaction(chemicalReaction.result(), combineIdenticalChemicals(ingredientList)));
        }

        return Optional.empty();
    }

    private List<Ingredient> combineIdenticalChemicals(List<Ingredient> ingredientList) {
        final Map<Chemical, List<Ingredient>> groupedIngredients = ingredientList.stream().collect(groupingBy(Ingredient::chemical));

        return groupedIngredients.entrySet().stream()
                .map(entry -> new Ingredient(entry.getKey(), entry.getValue().stream().mapToInt(Ingredient::amount).sum()))
                .collect(Collectors.toList());
    }

    private List<Ingredient> replaceIngredientInList(Ingredient ingredientToReplace, List<Ingredient> ingredientListToUpdate) {
        final List<Ingredient> replacementIngredientList = replacementForIngredient(ingredientToReplace);
        final List<Ingredient> newIngredientList = new ArrayList<>();

        ingredientListToUpdate.forEach(ingredient -> {
            if (!ingredient.chemical().equals(ingredientToReplace.chemical()))
                newIngredientList.add(ingredient);
            else
                newIngredientList.addAll(replacementIngredientList);
        });
        newIngredientList.sort(Comparator.comparing(o -> o.chemical().name()));

        return newIngredientList;
    }

    private List<Ingredient> replacementForIngredient(Ingredient ingredientToReplace) {
        if (!map.containsKey(ingredientToReplace.chemical()))
            throw new IllegalArgumentException("Cannot replace ingredient " + ingredientToReplace);

        final Ingredient toBeReplacedResult = map.get(ingredientToReplace.chemical()).result();
        final List<Ingredient> ingredientList = map.get(ingredientToReplace.chemical()).ingredients();
        final int factor = (ingredientToReplace.amount() / toBeReplacedResult.amount()) + (ingredientToReplace.amount() % toBeReplacedResult.amount() == 0 ? 0 : 1);

        return ingredientList.stream().map(i -> new Ingredient(i.chemical(), i.amount() * factor)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        map.values().forEach(reaction -> sb.append(reaction).append("\n"));
        return sb.toString();
    }
}
