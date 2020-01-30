package com.putoet.day16;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class FFTImpl implements FFT {
    private final List<Integer> baseList;
    private final List<Integer> list;
    private String toString = null;

    private FFTImpl(List<Integer> list) {
        this.baseList = list;
        this.list = list;
    }

    private FFTImpl(List<Integer> baseList, List<Integer> list) {
        this.baseList = baseList;
        this.list = new ArrayList<>(baseList.size());
        this.list.addAll(list);
    }

    public static FFTImpl from(String numbers) {
        assert numbers != null;
        assert numbers.matches("[0-9]+");

        return new FFTImpl(numbers.chars().mapToObj(c -> c - '0').collect(Collectors.toList()));
    }

    @Override
    public List<Integer> asList() {
        return Collections.unmodifiableList(list);
    }

    @Override
    public FFT apply(Pattern pattern) {
        final List<Integer> newList = new ArrayList<>(list.size());
        for (int idx = 0; idx < list.size(); idx++) {
            System.out.print("\rPhase " + idx);
            System.out.flush();

            newList.add(idx, applyPhase(pattern));
            pattern = pattern.nextPhasePattern();
        }

        return new FFTImpl(baseList, newList);
    }

    @Override
    public FFT apply(Pattern pattern, int times) {
        assert times > 0;

        FFT series = new FFTImpl(this.baseList, this.list);
        while (times-- > 0) {
            series = series.apply(pattern);
        }
        return series;
    }

    @Override
    public String message(int offsetSize) {
        assert offsetSize > 0 && offsetSize <= 7;

        long offset = Long.parseLong(baseList.stream()
                .limit(offsetSize)
                .map(String::valueOf)
                .collect(Collectors.joining("")));
        if (offset > list.size())
            throw new IllegalStateException("Cannot get message at offset " + offset + " while list is only " + list.size() + " elements long");

        return list.stream()
                .skip(offset)
                .limit(8)
                .map(String::valueOf)
                .collect(Collectors.joining(""));
    }

    private Integer applyPhase(Pattern pattern) {
        final Iterator<Integer> iter = pattern.iterator();

        int sum = 0;
        for (Integer i : list) {
            sum += (i * iter.next());
        }

        return Math.abs(sum % 10);
    }

    @Override
    public String toString() {
        if (toString == null) {
            final StringBuilder sb = new StringBuilder();
            list.forEach(sb::append);
            toString = sb.toString();
        }
        return toString;
    }
}
