package com.putoet.day14;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ChemicalReactionTest {

    @Test
    public void testOfSimple() {
        final ChemicalReaction reaction = ChemicalReaction.of("118 ORE => 7 GTPZ");
        assertEquals(new Ingredient(new Chemical("gtpz"), 7), reaction.result());
        assertEquals(List.of(new Ingredient(new Chemical("ORE"), 118)), reaction.ingredients());
    }

    @Test
    public void testOfComplex() {
        final ChemicalReaction reaction = ChemicalReaction.of("1 PZFG, 2 LFZS, 2 CJNQ, 2 FPRJW, 17 MVCH, 7 MGNCW, 26 KXBGP => 6 TBTL");
        assertEquals(new Ingredient(new Chemical("tbtl"), 6), reaction.result());
        assertEquals(List.of(new Ingredient(new Chemical("pzfg"), 1),
                new Ingredient(new Chemical("lfzs"), 2),
                new Ingredient(new Chemical("cjnq"), 2),
                new Ingredient(new Chemical("fprjw"), 2),
                new Ingredient(new Chemical("mvch"), 17),
                new Ingredient(new Chemical("mgncw"), 7),
                new Ingredient(new Chemical("kxbgp"), 26)), reaction.ingredients());
    }

    @Test
    public void testMultiplyBy() {
        final ChemicalReaction reaction = ChemicalReaction.of("118 ORE, 5 A => 7 GTPZ");
        final ChemicalReaction multipliedReaction = reaction.multiplyBy(3);
        assertEquals(new Ingredient(new Chemical("gtpz"), 21), multipliedReaction.result());
        assertEquals(List.of(new Ingredient(new Chemical("ORE"), 354), new Ingredient(new Chemical("A"), 15)), multipliedReaction.ingredients());
    }

}