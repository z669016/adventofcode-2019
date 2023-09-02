package com.putoet.day17;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class Route {
    private final List<String> route;
    private final List<List<String>> function;

    public Route(@NotNull List<String> route) {
        this.function = List.of(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        this.route = splitRoute(function, new ArrayList<>(route));
    }

    private static List<String> splitRoute(List<List<String>> function, List<String> route) {
        final var replacement = new String[]{"A", "B", "C"};

        for (var i = 0; i < 3; i++) {
            final var longest = longestRecurring(route);
            if (!longest.isEmpty()) {
                function.get(i).addAll(longest);
                replaceSubList(route, function.get(i), replacement[i]);
            }
        }

        return route;
    }

    public static String routeAsString(@NotNull List<String> route) {
        return String.join(",", route);
    }

    public static List<String> longestRecurring(@NotNull List<String> route) {
        var len = 10;
        var longestRecurring = recurringOf(len, route);
        while (longestRecurring.isEmpty()) {
            len -= 2;
            longestRecurring = recurringOf(len, route);
        }

        return longestRecurring;
    }

    private static List<String> recurringOf(int len, List<String> route) {
        for (var offset = 0; offset < route.size() - len; offset++) {
            final var subList = route.subList(offset, offset + len);
            if (routeAsString(subList).length() > 20)
                continue;

            if (subList.stream().anyMatch("A|B|C"::contains))
                continue;

            if (containsSubList(route, offset + len, subList))
                return subList;
        }

        return List.of();
    }

    public static int indexOf(@NotNull List<String> list, int offset, @NotNull List<String> subList) {
        for (var i = offset; i <= list.size() - subList.size(); i++) {
            var match = true;
            for (var x = 0; x < subList.size() && match; x++) {
                match = list.get(i + x).equals(subList.get(x));
            }
            if (match)
                return i;
        }

        return -1;
    }

    public static boolean containsSubList(@NotNull List<String> list, int offset, @NotNull List<String> subList) {
        return indexOf(list, offset, subList) != -1;
    }

    public static List<String> replaceSubList(@NotNull List<String> list, @NotNull List<String> subList, @NotNull String replacement) {
        var offset = indexOf(list, 0, subList);
        while (offset != -1) {
            for (var i = 0; i < subList.size(); i++) {
                if (i == 0)
                    list.set(offset + i, replacement);
                else
                    list.remove(offset + 1);
            }

            offset = indexOf(list, 0, subList);
        }
        return list;
    }

    public String mainRoute() {
        return routeAsString(route);
    }

    public String functionA() {
        return routeAsString(function.get(0));
    }

    public String functionB() {
        return routeAsString(function.get(1));
    }

    public String functionC() {
        return routeAsString(function.get(2));
    }
}
