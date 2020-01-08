package com.putoet.day14;

import java.util.*;

public class Ingredient {
    private final Chemical chemical;
    private final long amount;

    public Ingredient(Chemical chemical) {
        this.chemical = chemical;
        this.amount = 1;
    }

    public Ingredient(Chemical chemical, long amount) {
        this.chemical = chemical;
        this.amount = amount;
    }

    public Chemical chemical() { return chemical; }

    public long amount() { return amount; }

    public static Ingredient of(String ingredient) {
        final String[] elements = ingredient.trim().split(" ");
        try {
            return new Ingredient(new Chemical(elements[1]), Integer.parseInt(elements[0].trim()));
        } catch (RuntimeException exc) {
            throw new IllegalArgumentException("Invalid ingredient (" + ingredient + ")", exc);
        }
    }

    public static List<Ingredient> ofList(String reaction) {
        final List<Ingredient> list = new ArrayList<>();
        final String[] ingredients = reaction.trim().split(",");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ingredient)) return false;
        Ingredient that = (Ingredient) o;
        return amount == that.amount &&
                Objects.equals(chemical, that.chemical);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chemical, amount);
    }
}
