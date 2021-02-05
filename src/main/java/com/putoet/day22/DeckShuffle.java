package com.putoet.day22;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.math.BigInteger.*;

/**
 * Code based on the solution developed by Said Aspen, the original can be found here:
 *  https://github.com/saidaspen/aoc2019/blob/master/java/src/main/java/se/saidaspen/aoc2019/day22/Day22.java
 */
public final class DeckShuffle {
    private static final String ERR_OP = "Unsupported shuffle operation '%s'";

    private static final String DEAL_INTO = "deal into new stack";
    private static final String CUT = "cut";
    private static final String DEAL_W_INC = "deal with increment";

    /* The identity function: f(x)=x */
    private static final LinearFunction ID = new LinearFunction(ONE, ZERO);

    private final BigInteger size, count;
    private final List<String> input;

    public DeckShuffle(List<String> input, BigInteger size, BigInteger count) {
        this.input = input;
        this.size = size;
        this.count = count;
    }

    public static List<String> reverse(List<String> list) {
        final List<String> reverse = new ArrayList<>(list);
        Collections.reverse(reverse);

        return reverse;
    }

    private static BigInteger argOf(String s) {
        return new BigInteger(s.replaceAll("[^-?0-9]+", ""));
    }

    /**
     * Aggregate two functions f(x) and g(x) to create a new function h(x)=g(f(x))
     */
    private LinearFunction aggregate(LinearFunction f, LinearFunction g) {
        // Let f(x)=k*x+m and g(x)=j*x+n, then
        //      h(x) = g(f(x)) = Ax+B = j*(k*x+m)+n = j*k*x + (j*m+n) => A=j*k, B=j*m+n
        return new LinearFunction(g.k.multiply(f.k), g.k.multiply(f.m).add(g.m));
    }

    public BigInteger positionOf(BigInteger in) {
        final LinearFunction shuffle = input.stream()
                .map(s -> {
                    if (s.startsWith(DEAL_INTO))
                        return new LinearFunction(ONE.negate(), ONE.negate());
                    else if (s.startsWith(CUT))
                        return new LinearFunction(ONE, argOf(s).mod(size).negate());
                    else if (s.startsWith(DEAL_W_INC))
                        return new LinearFunction(argOf(s), ZERO);

                    throw new RuntimeException(String.format(ERR_OP, s));
                })
                .reduce(ID, this::aggregate); // Create one func of all shuffle operations, i.e. like: f(x)=f1((f2(f3(x)))
        return executeTimes(shuffle.k, shuffle.m, count).apply(in).mod(size);
    }

    public BigInteger cardAt(BigInteger in) {
        final LinearFunction shuffle = reverse(input).stream()
                .map(s -> {
                    if (s.startsWith(DEAL_INTO))
                        return new LinearFunction(ONE.negate(), ONE.negate().subtract(size));
                    else if (s.startsWith(CUT))
                        return new LinearFunction(ONE, argOf(s).mod(size));
                    else if (s.startsWith(DEAL_W_INC)) {
                        BigInteger z = argOf(s).modInverse(size);
                        return new LinearFunction(ONE.multiply(z).mod(size), ZERO);
                    }
                    throw new RuntimeException(String.format(ERR_OP, s));
                })
                .reduce(ID, this::aggregate); // Create one func of all shuffle operations, i.e. like: f(x)=f1((f2(f3(x)))
        return executeTimes(shuffle.k, shuffle.m, count).apply(in).mod(size);
    }

    private LinearFunction executeTimes(BigInteger k, BigInteger m, BigInteger count) {
        if (count.equals(ZERO)) {
            return ID;
        } else if (count.mod(TWO).equals(ZERO)) {
            return executeTimes(k.multiply(k).mod(size), k.multiply(m).add(m).mod(size), count.divide(TWO));
        } else {
            final LinearFunction cd = executeTimes(k, m, count.subtract(ONE));
            return new LinearFunction(k.multiply(cd.k).mod(size), k.multiply(cd.m).add(m).mod(size));
        }
    }

    private static class LinearFunction {
        public final BigInteger k, m;

        public LinearFunction(BigInteger k, BigInteger m) {
            this.k = k;
            this.m = m;
        }

        public BigInteger apply(BigInteger x) {
            return x.multiply(k).add(m);
        }
    }
}