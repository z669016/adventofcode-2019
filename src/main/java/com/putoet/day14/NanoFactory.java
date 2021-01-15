package com.putoet.day14;

import com.putoet.resources.ResourceLines;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class NanoFactory {
    private final Map<Chemical, ChemicalReaction> map = new HashMap<>();

    public NanoFactory(List<ChemicalReaction> chemicalReactions) {
        chemicalReactions.forEach(reaction -> map.put(reaction.result().chemical(), reaction));
        if (!map.containsKey(Chemical.FUEL)) {
            throw new AssertionError("List of chemicals must contain a chemical reaction fro FUEL");
        }
    }

    public static List<ChemicalReaction> loadFile(String fileName) {
        return ResourceLines.stream(fileName).map(ChemicalReaction::of).collect(Collectors.toList());
    }

    public Map<Chemical, ChemicalReaction> chemicalReactions() {
        return Collections.unmodifiableMap(map);
    }

    public Optional<ChemicalReaction> reactionFor(Chemical chemical) {
        return Optional.ofNullable(map.get(chemical));
    }

    public ChemicalReaction reactionForFuel() {
        return map.get(Chemical.FUEL);
    }

    public Set<Chemical> baseChemicals() {
        return map.entrySet().stream()
                .filter(e -> e.getValue().ingredients().stream().allMatch(i -> i.chemical().equals(Chemical.ORE)))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public Optional<ChemicalReaction> simplifyChemicalReaction(ChemicalReaction reaction) {
        Optional<Chemical> chemical = nextToReplace(reaction);
        while (chemical.isPresent()) {
            Optional<ChemicalReaction> newReaction = replaceChemicalInReaction(reaction, chemical.get());
            if (newReaction.isEmpty())
                return Optional.empty();

            reaction = newReaction.get();
            chemical = nextToReplace(reaction);
        }

        return Optional.of(reaction);
    }

    public Optional<Chemical> nextToReplace(ChemicalReaction reaction) {
        final Set<Chemical> topLevelChemicals = reaction.chemicals().stream()
                .filter(chemical -> !chemical.equals(Chemical.ORE))
                .collect(Collectors.toSet());

        final Set<Chemical> independentChemicals = topLevelChemicals.stream()
                .filter(chemical -> noneIsDependingOn(topLevelChemicals, chemical))
                .collect(Collectors.toSet());

        return independentChemicals.stream().findFirst();
    }

    private boolean noneIsDependingOn(Set<Chemical> chemicals, Chemical chemical) {
        return chemicals.stream()
                .filter(c -> !c.equals(chemical) && !c.equals(Chemical.FUEL) && !c.equals(Chemical.ORE))
                .map(this::reactionFor)
                .flatMap(Optional::stream)
                .noneMatch(cr -> cr.chemicals().contains(chemical) || !noneIsDependingOn(cr.chemicals(), chemical));
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
                .map(entry -> new Ingredient(entry.getKey(), entry.getValue().stream().mapToLong(Ingredient::amount).sum()))
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
        final long factor = (ingredientToReplace.amount() / toBeReplacedResult.amount()) + (ingredientToReplace.amount() % toBeReplacedResult.amount() == 0 ? 0 : 1);

        return ingredientList.stream().map(i -> new Ingredient(i.chemical(), i.amount() * factor)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        map.values().forEach(reaction -> sb.append(reaction).append("\n"));
        return sb.toString();
    }

    public long minimalOreForFuel() {
        final ChemicalReaction fuelReaction = reactionForFuel();

        final Optional<ChemicalReaction> simplifiedFuelReaction = simplifyChemicalReaction(fuelReaction);
        if (simplifiedFuelReaction.isEmpty())
            throw new IllegalStateException("Cannot determine max fuel without simplified chemical reaction for fuel");

        return simplifiedFuelReaction.get().ingredients().get(0).amount();
    }

    public Optional<ChemicalReaction> maxFuelReactionFor(long availableOre) {
        final long minimalOreForFuel = minimalOreForFuel();

        if (availableOre < minimalOreForFuel)
            throw new IllegalArgumentException("Insufficient fuel for 1 fuel, you need a minimum of " + minimalOreForFuel + " ORE.");

        return maxFuelFor(availableOre, minimalOreForFuel);
    }

    private Optional<ChemicalReaction> maxFuelFor(long availableOre, long minimalOreForFuel) {
        final ChemicalReaction fuelReaction = reactionForFuel();
        Optional<ChemicalReaction> maxReaction = Optional.empty();
        long max = 0;
        long used = 0;
        long rest = availableOre - used;

        while (rest > minimalOreForFuel) {
            final ChemicalReaction reaction = fuelReaction.multiplyBy(max + ((availableOre - used) / minimalOreForFuel));
            maxReaction = simplifyChemicalReaction(reaction);
            if (maxReaction.isEmpty())
                return Optional.empty();

            max = maxReaction.get().result().amount();
            used = maxReaction.get().ingredients().get(0).amount();
            rest = availableOre - used;
        }

        return maxReaction;
    }
}
