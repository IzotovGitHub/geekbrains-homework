package geek.brains.quarter.one.java2.lesson3;

import java.util.*;

import static java.util.Collections.emptySet;

public class PhoneBook {

    Map<String, Set<Integer>> book = new HashMap<>();

    public void add(String name, int number) {
        if (book.containsKey(name)) {
            book.get(name).add(number);
        } else {
            book.put(name, new HashSet<>(List.of(number)));
        }
    }

    public Set<Integer> get(String name) {
        return Set.copyOf(book.getOrDefault(name, emptySet()));
    }

}
