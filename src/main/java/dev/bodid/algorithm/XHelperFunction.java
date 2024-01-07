package dev.bodid.algorithm;

import java.util.*;

/**
 * @author danielbodi
 * @created 2023/12/10
 */
public class XHelperFunction {

    public List<Element> simplify(List<Integer> minterms, Map<Character, Element> primesMappedByNames) {
        final Map<Integer, List<Character>> map = new HashMap<>();
        for (Integer minterm : minterms) {
            final List<Character> characters = new ArrayList<>();
            for (Character character : primesMappedByNames.keySet()) {
                if (primesMappedByNames.get(character).getMinterms().contains(minterm)) {
                    characters.add(character);
                }
            }
            map.put(minterm, characters);
        }

        final List<List<Character>> temp = new ArrayList<>(map.values());
        final List<List<Character>> characterResult = new ArrayList<>();

        for (List<Character> characters : map.values()) {
            if (characters.size() == 1 && !characterResult.contains(characters)) {
                characterResult.add(characters);
                temp.removeAll(List.of(characters));
            }
        }

        outer: for (List<Character> characters : map.values()) {
            if (characters.size() != 1) {
                for (List<Character> c : characterResult) {
                    if (c.size() == 1 && characters.contains(c.get(0))) {
                        temp.removeAll(List.of(characters));
                        continue outer;
                    }
                }
            }
        }

        if (!temp.isEmpty()) {
            final List<List<Character>> extracted = new ArrayList<>();

            final ListIterator<List<Character>> it = temp.listIterator();
            while (it.hasNext()) {
                final List<Character> a = it.next();

                if (it.hasNext()) {
                    final List<Character> b = it.next();

                    for (Character c : primesMappedByNames.keySet()) {
                        if (a.contains(c) && b.contains(c)) {
                            extracted.add(List.of(c));

                            if (!temp.isEmpty()) {
                                it.remove();
                                it.previous();
                                it.remove();
                            }
                        }
                    }
                }
            }

            if (!extracted.isEmpty()) {
                characterResult.addAll(extracted);
            }
        }

        if (!temp.isEmpty()) {
            characterResult.addAll(temp);
        }

        return characterResult.stream()
                .flatMap(List::stream)
                .toList()
                .stream()
                .map(primesMappedByNames::get)
                .toList();
    }
}
