package me.m0dii.pllib.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class CollectionUtils {
    public static <T> T getRandomElement(Collection<T> collection) {
        return collection.stream()
                .skip((int) (Math.random()*collection.size()))
                .findFirst()
                .orElse(null);
    }

    public static <T> T getRandomElement(T[] t) {
        return t[(int) (Math.random()*t.length)];
    }

    public static List<String> addStarting(List<String> list, String toMatch, String... toAdd) {
        if(list == null || toMatch == null || toAdd == null) {
            return list;
        }

        Stream.of(toAdd).filter(s -> StringUtils.startsWithIgnoreCase(s, toMatch)).forEach(list::add);

        return list;
    }

    public static List<String> addEnding(List<String> list, String toMatch, String... toAdd) {
        if(list == null || toMatch == null || toAdd == null) {
            return list;
        }

        Stream.of(toAdd).filter(s -> StringUtils.endsWithIgnoreCase(s, toMatch)).forEach(list::add);

        return list;
    }

    public static List<String> addMatching(List<String> list, String toMatch, String... toAdd) {
        if(list == null || toMatch == null || toAdd == null) {
            return list;
        }

        Stream.of(toAdd).filter(s -> s.equalsIgnoreCase(toMatch)).forEach(list::add);

        return list;
    }

    public static List<String> addContaining(List<String> list, String toMatch, String... toAdd) {
        if(list == null || toMatch == null || toAdd == null) {
            return list;
        }

        Stream.of(toAdd).filter(s -> StringUtils.containsIgnoreCase(s, toMatch)).forEach(list::add);

        return list;
    }

    public static List<String> addStarting(List<String> list, String toMatch, List<String> toAdd) {
        if(list == null || toMatch == null || toAdd == null) {
            return list;
        }

        toAdd.stream().filter(s -> StringUtils.startsWithIgnoreCase(s, toMatch)).forEach(list::add);

        return list;
    }

    public static List<String> addEnding(List<String> list, String toMatch, List<String> toAdd) {
        if(list == null || toMatch == null || toAdd == null) {
            return list;
        }

        toAdd.stream().filter(s -> StringUtils.endsWithIgnoreCase(s, toMatch)).forEach(list::add);

        return list;
    }

    public static List<String> addMatching(List<String> list, String toMatch, List<String> toAdd) {
        if(list == null || toMatch == null || toAdd == null) {
            return list;
        }

        toAdd.stream().filter(s -> s.equalsIgnoreCase(toMatch)).forEach(list::add);

        return list;
    }

    public static List<String> addContaining(List<String> list, String toMatch, List<String> toAdd) {
        if(list == null || toMatch == null || toAdd == null) {
            return list;
        }

        toAdd.stream().filter(s -> StringUtils.containsIgnoreCase(s, toMatch)).forEach(list::add);

        return list;
    }
}
