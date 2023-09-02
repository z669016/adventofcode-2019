package com.putoet.day14;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

record Chemical(@NotNull String name) {
    private static final Pattern pattern = Pattern.compile("[A-Z]+");
    public static final Chemical ORE = new Chemical("ORE");
    public static final Chemical FUEL = new Chemical("FUEL");

    Chemical {
        assert !name.isEmpty();
        name = name.trim().toUpperCase();

        if (!pattern.matcher(name).matches())
            throw new IllegalArgumentException("Invalid chemical name " + name);
    }

    @Override
    public String toString() {
        return name();
    }
}
