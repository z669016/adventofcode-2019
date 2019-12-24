package com.putoet.day7;

import java.util.function.Supplier;

public class ExceptionTester {
    public static final IllegalArgumentException ia(Supplier supplier) {
        IllegalArgumentException ia = null;
        try {
            supplier.get();
        } catch (IllegalArgumentException exc) {
            ia = exc;
        }
        return ia;
    }

    public static final IllegalStateException is(Supplier supplier) {
        IllegalStateException is = null;
        try {
            supplier.get();
        } catch (IllegalStateException exc) {
            is = exc;
        }
        return is;
    }

    public static final AssertionError ae(Supplier supplier) {
        AssertionError ae = null;
        try {
            supplier.get();
        } catch (AssertionError exc) {
            ae = exc;
        }
        return ae;
    }
}
