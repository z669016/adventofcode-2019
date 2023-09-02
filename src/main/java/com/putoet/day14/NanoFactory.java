package com.putoet.day14;

import com.putoet.resources.ResourceLines;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

record NanoFactory(@NotNull Map<Chemical, ChemicalReaction> map) {

    public NanoFactory {
        if (!map.containsKey(Chemical.FUEL)) {
            throw new AssertionError("List of chemicals must contain a chemical reaction fro FUEL");
        }

        map = Collections.unmodifiableMap(map);
    }

    public static NanoFactory of(@NotNull String fileName) {
        return NanoFactory.of(ResourceLines.stream(fileName)
                        .map(ChemicalReaction::of)
                        .toList());
    }

    public static NanoFactory of(@NotNull List<ChemicalReaction> reactions) {
        return new NanoFactory(reactions.stream()
                .collect(Collectors.toMap(reaction -> reaction.result().chemical(), reaction -> reaction))
        );
    }

    public Map<Chemical, ChemicalReaction> chemicalReactions() {
        return Collections.unmodifiableMap(map);
    }

    public Optional<ChemicalReaction> reactionFor(@NotNull Chemical chemical) {
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
        var chemical = nextToReplace(reaction);
        while (chemical.isPresent()) {
            var newReaction = replaceChemicalInReaction(reaction, chemical.get());
            if (newReaction.isEmpty())
                return Optional.empty();

            reaction = newReaction.get();
            chemical = nextToReplace(reaction);
        }

        return Optional.of(reaction);
    }

    public Optional<Chemical> nextToReplace(ChemicalReaction reaction) {
        final var topLevelChemicals = reaction.chemicals().stream()
                .filter(chemical -> !chemical.equals(Chemical.ORE))
                .collect(Collectors.toSet());

        return topLevelChemicals.stream()
                .filter(chemical -> noneIsDependingOn(topLevelChemicals, chemical))
                .findFirst();
    }

    private boolean noneIsDependingOn(Set<Chemical> chemicals, Chemical chemical) {
        return chemicals.stream()
                .filter(c -> !c.equals(chemical) && !c.equals(Chemical.FUEL) && !c.equals(Chemical.ORE))
                .map(this::reactionFor)
                .flatMap(Optional::stream)
                .noneMatch(cr -> cr.chemicals().contains(chemical) || !noneIsDependingOn(cr.chemicals(), chemical));
    }

    public Optional<ChemicalReaction> replaceChemicalInReaction(ChemicalReaction chemicalReaction, Chemical chemicalToReplace) {
        final var ingredientToReplace = chemicalReaction.ingredients().stream().filter(i -> i.chemical().equals(chemicalToReplace)).findFirst();
        if (ingredientToReplace.isPresent()) {
            final var ingredientList = replaceIngredientInList(ingredientToReplace.get(), chemicalReaction.ingredients());
            return Optional.of(new ChemicalReaction(combineIdenticalChemicals(ingredientList), chemicalReaction.result()));
        }

        return Optional.empty();
    }

    private List<Ingredient> combineIdenticalChemicals(List<Ingredient> ingredientList) {
        final var groupedIngredients = ingredientList.stream().collect(groupingBy(Ingredient::chemical));

        return groupedIngredients.entrySet().stream()
                .map(entry -> new Ingredient(entry.getKey(), entry.getValue().stream().mapToLong(Ingredient::amount).sum()))
                .toList();
    }

    private List<Ingredient> replaceIngredientInList(Ingredient ingredientToReplace, List<Ingredient> ingredientListToUpdate) {
        final var replacementIngredientList = replacementForIngredient(ingredientToReplace);
        final var newIngredientList = new ArrayList<Ingredient>();

        ingredientListToUpdate.forEach(ingredient -> {
            if (!ingredient.chemical().equals(ingredientToReplace.chemical()))
                newIngredientList.add(ingredient);
            else
                newIngredientList.addAll(replacementIngredientList);
        });
        newIngredientList.sort(Comparator.comparing(o -> o.chemical().name()));

        return Collections.unmodifiableList(newIngredientList);
    }

    private List<Ingredient> replacementForIngredient(@NotNull Ingredient ingredientToReplace) {
        if (!map.containsKey(ingredientToReplace.chemical()))
            throw new IllegalArgumentException("Cannot replace ingredient " + ingredientToReplace);

        final var toBeReplacedResult = map.get(ingredientToReplace.chemical()).result();
        final var ingredientList = map.get(ingredientToReplace.chemical()).ingredients();
        final var factor = (ingredientToReplace.amount() / toBeReplacedResult.amount()) + (ingredientToReplace.amount() % toBeReplacedResult.amount() == 0 ? 0 : 1);

        return ingredientList.stream().map(i -> new Ingredient(i.chemical(), i.amount() * factor)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return map.values().stream()
                .map(ChemicalReaction::toString)
                .collect(Collectors.joining("\n"));
    }

    public long minimalOreForFuel() {
        final var fuelReaction = reactionForFuel();
        final var simplifiedFuelReaction = simplifyChemicalReaction(fuelReaction).orElseThrow();

        return simplifiedFuelReaction.ingredients().get(0).amount();
    }

    public Optional<ChemicalReaction> maxFuelReactionFor(long availableOre) {
        final var minimalOreForFuel = minimalOreForFuel();

        if (availableOre < minimalOreForFuel)
            throw new IllegalArgumentException("Insufficient fuel for 1 fuel, you need a minimum of " + minimalOreForFuel + " ORE.");

        return maxFuelFor(availableOre, minimalOreForFuel);
    }

    private Optional<ChemicalReaction> maxFuelFor(long availableOre, long minimalOreForFuel) {
        final var fuelReaction = reactionForFuel();
        Optional<ChemicalReaction> maxReaction = Optional.empty();
        long max = 0;
        long used = 0;
        long rest = availableOre - used;

        while (rest > minimalOreForFuel) {
            final var reaction = fuelReaction.multiplyBy(max + ((availableOre - used) / minimalOreForFuel));
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
