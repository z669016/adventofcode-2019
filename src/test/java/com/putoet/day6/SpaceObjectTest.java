package com.putoet.day6;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpaceObjectTest {

    public static final String AAA_NAME = "AAA";
    public static final String BBB_NAME = "BBB";
    public static final String CCC_NAME = "CCC";
    public static final String DDD_NAME = "DDD";
    public static final String EEE_NAME = "EEE";

    @Test
    public void COM() {
        assertNotNull(SpaceObject.COM());
        assertEquals("COM", SpaceObject.COM().name());
        assertNull(SpaceObject.COM().center());
    }

    @Test
    public void properties() {
        final SpaceObject AAA = new SpaceObject(AAA_NAME, SpaceObject.COM());
        final SpaceObject BBB = new SpaceObject(BBB_NAME, AAA);

        assertEquals(AAA_NAME, AAA.name());
        assertEquals(SpaceObject.COM(), AAA.center());

        assertEquals(BBB_NAME, BBB.name());
        assertEquals(AAA, BBB.center());

        assertEquals("COM)AAA", AAA.toString());
        assertEquals("AAA)BBB", BBB.toString());
    }

    @Test
    public void reCenter() {
        final SpaceObject AAA = new SpaceObject(AAA_NAME, SpaceObject.COM());
        final SpaceObject BBB = new SpaceObject(BBB_NAME, SpaceObject.COM());
        final SpaceObject CCC = new SpaceObject(CCC_NAME, BBB);

        /// Recenter with the current center should not fail
        AAA.reCenter(SpaceObject.COM());
        CCC.reCenter(BBB);

        // Recenter when current center is COM should be okay
        BBB.reCenter(AAA);
        assertEquals(AAA, BBB.center());

        // Cannot recenter to null
        assertThrows(IllegalArgumentException.class, () -> AAA.reCenter(null));

        // Cannot recenter if center isn't COM
        assertThrows(IllegalStateException.class, () -> CCC.reCenter(AAA));
    }

    @Test
    public void testOrbitsTo() {
        final SpaceObject AAA = new SpaceObject(AAA_NAME, SpaceObject.COM());
        final SpaceObject BBB = new SpaceObject(BBB_NAME, AAA);
        final SpaceObject CCC = new SpaceObject(CCC_NAME, BBB);
        final SpaceObject DDD = new SpaceObject(DDD_NAME, BBB);
        final SpaceObject EEE = new SpaceObject(EEE_NAME, DDD);

        assertEquals(0, SpaceObject.orbitsToCom(SpaceObject.COM()));
        assertEquals(1, SpaceObject.orbitsToCom(AAA));
        assertEquals(2, SpaceObject.orbitsToCom(BBB));
        assertEquals(3, SpaceObject.orbitsToCom(CCC));

        assertEquals(3, SpaceObject.orbitsTo(EEE, SpaceObject.COM()));
        assertEquals(2, SpaceObject.orbitsTo(EEE, AAA));

        IllegalArgumentException ia = null;
        try {
            SpaceObject.orbitsTo(SpaceObject.COM(), AAA);
        } catch (IllegalArgumentException exc) {
            ia = exc;
        }
        assertNotNull(ia);
        assertEquals("Cannot count orbits from COM", ia.getMessage());

        IllegalStateException is = null;
        try {
            SpaceObject.orbitsTo(EEE, CCC);
        } catch (IllegalStateException exc) {
            is = exc;
        }
        assertNotNull(is);
        assertEquals("CCC is not at the route between EEE and COM", is.getMessage());
    }

    @Test
    public void testRoute() {
        final SpaceObject AAA = new SpaceObject(AAA_NAME, SpaceObject.COM());
        final SpaceObject BBB = new SpaceObject(BBB_NAME, AAA);
        final SpaceObject CCC = new SpaceObject(CCC_NAME, BBB);
        final SpaceObject DDD = new SpaceObject(DDD_NAME, BBB);
        final SpaceObject EEE = new SpaceObject(EEE_NAME, DDD);

        assertEquals(List.of(EEE, DDD, BBB, AAA), SpaceObject.route(EEE));
        assertEquals(List.of(CCC, BBB, AAA), SpaceObject.route(CCC));
        assertEquals(List.of(AAA), SpaceObject.route(AAA));
        assertEquals(List.of(), SpaceObject.route(SpaceObject.COM()));
    }
}