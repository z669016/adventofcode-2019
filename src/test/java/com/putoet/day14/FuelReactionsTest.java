package com.putoet.day14;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FuelReactionsTest {

    @Test
    public void testLoadFile() {
        final List<ChemicalReaction> reactions = FuelReactions.loadFile("/day14.txt");
        final FuelReactions fuelReactions = new FuelReactions(reactions);
        assertEquals(reactions.size(), fuelReactions.chemicalReactions().size());

        final Optional<ChemicalReaction> reaction = fuelReactions.reactionFor(Chemical.FUEL);
        assertTrue(reaction.isPresent());
        // System.out.println(reaction.get());
    }

    @Test
    public void testChemicalToReplaceNext() {
        final FuelReactions fuelReactions = new FuelReactions(createFuelReactionsSample1());
        final Optional<ChemicalReaction> reaction = fuelReactions.reactionForFuel();
        assertTrue(reaction.isPresent());
        final Optional<Chemical> toReplace = fuelReactions.nextToReplace(reaction.get());
        assertTrue(toReplace.isPresent());
        assertEquals(toReplace.get(), new Chemical("E"));
    }

    @Test
    public void testReplaceChemical() {
        final FuelReactions fuelReactions = new FuelReactions(createFuelReactionsSample1());
        final Optional<ChemicalReaction> reaction = fuelReactions.reactionForFuel();
        assertTrue(reaction.isPresent());
        final Optional<ChemicalReaction> newReaction = fuelReactions.replaceChemicalInReaction(reaction.get(), new Chemical("E"));
        assertTrue(newReaction.isPresent());
        assertEquals("14 A, 1 D => 1 FUEL", newReaction.get().toString());
    }

    @Test
    public void testSimplifyChemicalReaction() {
        final FuelReactions fuelReactions = new FuelReactions(createFuelReactionsSample1());
        final Optional<ChemicalReaction> reaction = fuelReactions.reactionFor(Chemical.FUEL);
        assertTrue(reaction.isPresent());
        final Optional<ChemicalReaction> newReaction = fuelReactions.simplifyChemicalReaction(reaction.get());
        assertTrue(newReaction.isPresent());
        assertEquals("31 ORE => 1 FUEL", newReaction.get().toString());
    }

    @Test
    public void testBaseChemicalsSample1() {
        final FuelReactions fuelReactions = new FuelReactions(createFuelReactionsSample1());
        final Set<Chemical> baseChemicals = fuelReactions.baseChemicals();
        assertEquals("[A, B]", baseChemicals.toString());
    }

    @Test
    public void testBaseChemicalsSample2() {
        final FuelReactions fuelReactions = new FuelReactions(createFuelReactionsSample2());
        final Set<Chemical> baseChemicals = fuelReactions.baseChemicals();
        assertEquals("[A, B, C]", baseChemicals.toString());
    }

    @Test
    public void testReactionForFuel2() {
        final FuelReactions fuelReactions = new FuelReactions(createFuelReactionsSample2());
        final Optional<ChemicalReaction> reaction = fuelReactions.reactionForFuel();
        assertTrue(reaction.isPresent());
        final Optional<ChemicalReaction> newReaction = fuelReactions.simplifyChemicalReaction(reaction.get());
        assertTrue(newReaction.isPresent());
        assertEquals(165, newReaction.get().ingredients().get(0).amount());
    }

    @Test
    public void testReactionForFuel3() {
        final FuelReactions fuelReactions = new FuelReactions(createFuelReactionsSample3());
        final Optional<ChemicalReaction> reaction = fuelReactions.reactionForFuel();
        assertTrue(reaction.isPresent());
        final Optional<ChemicalReaction> newReaction = fuelReactions.simplifyChemicalReaction(reaction.get());
        assertTrue(newReaction.isPresent());
        assertEquals(13312, newReaction.get().ingredients().get(0).amount());
    }

    @Test
    public void testReactionForFuel4() {
        final FuelReactions fuelReactions = new FuelReactions(createFuelReactionsSample4());
        final Optional<ChemicalReaction> reaction = fuelReactions.reactionForFuel();
        assertTrue(reaction.isPresent());
        final Optional<ChemicalReaction> newReaction = fuelReactions.simplifyChemicalReaction(reaction.get());
        assertTrue(newReaction.isPresent());
        assertEquals(180697, newReaction.get().ingredients().get(0).amount());
    }

    @Test
    public void testReactionForFuel5() {
        final FuelReactions fuelReactions = new FuelReactions(createFuelReactionsSample5());
        final Optional<ChemicalReaction> reaction = fuelReactions.reactionForFuel();
        assertTrue(reaction.isPresent());
        final Optional<ChemicalReaction> newReaction = fuelReactions.simplifyChemicalReaction(reaction.get());
        assertTrue(newReaction.isPresent());
        assertEquals(2210736, newReaction.get().ingredients().get(0).amount());
    }

    @Test
    public void testMaxFuelForTooLittle() {
        final FuelReactions fuelReactions = new FuelReactions(createFuelReactionsSample3());

        final long availableOre = 1_000L;
        assertThrows(IllegalArgumentException.class, () -> fuelReactions.maxFuelReactionFor(availableOre));
    }

    @Test
    public void testMaxFuelFor1() {
        final FuelReactions fuelReactions = new FuelReactions(createFuelReactionsSample3());

        final long availableOre = 1_000_000_000_000L;
        final Optional<ChemicalReaction> maxReaction = fuelReactions.maxFuelReactionFor(availableOre);
        assertTrue(maxReaction.isPresent());
        System.out.println(maxReaction);
        assertEquals(82892753L, maxReaction.get().result().amount());
    }

    @Test
    public void testMaxFuelFor2() {
        final FuelReactions fuelReactions = new FuelReactions(createFuelReactionsSample4());

        final long availableOre = 1_000_000_000_000L;
        final Optional<ChemicalReaction> maxReaction = fuelReactions.maxFuelReactionFor(availableOre);
        assertTrue(maxReaction.isPresent());
        System.out.println(maxReaction);
        assertEquals(5586022L, maxReaction.get().result().amount());
    }

    @Test
    public void testMaxFuelFor3() {
        final FuelReactions fuelReactions = new FuelReactions(createFuelReactionsSample5());

        final long availableOre = 1_000_000_000_000L;
        final Optional<ChemicalReaction> maxReaction = fuelReactions.maxFuelReactionFor(availableOre);
        assertTrue(maxReaction.isPresent());
        System.out.println(maxReaction);
        assertEquals(460664L, maxReaction.get().result().amount());
    }

    private List<ChemicalReaction> createFuelReactionsSample1() {
        return List.of(
                ChemicalReaction.of("10 ORE => 10 A"),
                ChemicalReaction.of("1 ORE => 1 B"),
                ChemicalReaction.of("7 A, 1 B => 1 C"),
                ChemicalReaction.of("7 A, 1 C => 1 D"),
                ChemicalReaction.of("7 A, 1 D => 1 E"),
                ChemicalReaction.of("7 A, 1 E => 1 FUEL")
        );
    }

    private List<ChemicalReaction> createFuelReactionsSample2() {
        return List.of(
                ChemicalReaction.of("9 ORE => 2 A"),
                ChemicalReaction.of("8 ORE => 3 B"),
                ChemicalReaction.of("7 ORE => 5 C"),
                ChemicalReaction.of("3 A, 4 B => 1 AB"),
                ChemicalReaction.of("5 B, 7 C => 1 BC"),
                ChemicalReaction.of("4 C, 1 A => 1 CA"),
                ChemicalReaction.of("2 AB, 3 BC, 4 CA => 1 FUEL")
        );
    }

    private List<ChemicalReaction> createFuelReactionsSample3() {
        return List.of(
                ChemicalReaction.of("157 ORE => 5 NZVS"),
                ChemicalReaction.of("165 ORE => 6 DCFZ"),
                ChemicalReaction.of("44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL"),
                ChemicalReaction.of("12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ"),
                ChemicalReaction.of("179 ORE => 7 PSHF"),
                ChemicalReaction.of("177 ORE => 5 HKGWZ"),
                ChemicalReaction.of("7 DCFZ, 7 PSHF => 2 XJWVT"),
                ChemicalReaction.of("165 ORE => 2 GPVTF"),
                ChemicalReaction.of("3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT")
        );
    }

    private List<ChemicalReaction> createFuelReactionsSample4() {
        return List.of(
                ChemicalReaction.of("2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG"),
                ChemicalReaction.of("17 NVRVD, 3 JNWZP => 8 VPVL"),
                ChemicalReaction.of("53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL"),
                ChemicalReaction.of("22 VJHF, 37 MNCFX => 5 FWMGM"),
                ChemicalReaction.of("139 ORE => 4 NVRVD"),
                ChemicalReaction.of("144 ORE => 7 JNWZP"),
                ChemicalReaction.of("5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC"),
                ChemicalReaction.of("5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV"),
                ChemicalReaction.of("145 ORE => 6 MNCFX"),
                ChemicalReaction.of("1 NVRVD => 8 CXFTF"),
                ChemicalReaction.of("1 VJHF, 6 MNCFX => 4 RFSQX"),
                ChemicalReaction.of("176 ORE => 6 VJHF")
                // ChemicalReaction.of("")
        );
    }

    private List<ChemicalReaction> createFuelReactionsSample5() {
        return List.of(
                ChemicalReaction.of("171 ORE => 8 CNZTR"),
                ChemicalReaction.of("7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL"),
                ChemicalReaction.of("114 ORE => 4 BHXH"),
                ChemicalReaction.of("14 VRPVC => 6 BMBT"),
                ChemicalReaction.of("6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL"),
                ChemicalReaction.of("6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT"),
                ChemicalReaction.of("15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW\n"),
                ChemicalReaction.of("13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW"),
                ChemicalReaction.of("5 BMBT => 4 WPTQ"),
                ChemicalReaction.of("189 ORE => 9 KTJDG"),
                ChemicalReaction.of("1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP"),
                ChemicalReaction.of("12 VRPVC, 27 CNZTR => 2 XDBXC"),
                ChemicalReaction.of("15 KTJDG, 12 BHXH => 5 XCVML"),
                ChemicalReaction.of("3 BHXH, 2 VRPVC => 7 MZWV"),
                ChemicalReaction.of("121 ORE => 7 VRPVC"),
                ChemicalReaction.of("7 XCVML => 6 RJRHP"),
                ChemicalReaction.of("5 BHXH, 4 VRPVC => 5 LTCX")
        );
    }
}