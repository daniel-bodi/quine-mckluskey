package dev.bodid.algorithm;

import java.util.*;

/**
 * @author danielbodi
 * @created 2023/12/10
 */
public class MintermMerger {

    private static final int DO_NOT_MERGE = -1;

    public Map<Character, Element> merge(List<Integer> mintermsToMerge) {
        final Map<Integer, List<Element>> elementsMappedByBitCount = new HashMap<>();
        int maxBitCount = 0;
        for (Integer minterm : mintermsToMerge) {
            final int bitCount = Integer.bitCount(minterm);
            if (!elementsMappedByBitCount.containsKey(bitCount)) {
                elementsMappedByBitCount.put(bitCount, new ArrayList<>());
            }

            elementsMappedByBitCount.get(bitCount).add(Element.with(minterm));

            if (maxBitCount < bitCount) {
                maxBitCount = bitCount;
            }
        }

        final List<Element> primes = doMerge(maxBitCount, elementsMappedByBitCount, new ArrayList<>());
        final Map<Character, Element> result = new HashMap<>();
        for (int i = 0; i < primes.size(); i++) {
            result.put((char) (97 + i), primes.get(i));
        }

        return result;
    }

    private List<Element> doMerge(int limit, Map<Integer, List<Element>> previousColumn, List<Element> primes) {
        final Map<Integer, List<Element>> currentColumn = new HashMap<>();
        int segmentCounter = 0;

        for (int i = 0; i < limit; i++) {
            final List<Element> currentSegment = previousColumn.get(i);
            final List<Element> nextSegment = previousColumn.get(i + 1);

            if (currentSegment == null) {
                continue;
            } else if (nextSegment == null) {
                // Add any leftover prime implicant if present
                primes.addAll(
                        currentSegment.stream()
                                .filter(element -> !element.isMerged())
                                .toList()
                );
                continue;
            }

            for (Element a : currentSegment) {
                for (Element b : nextSegment) {
                    final int score = getMergedScore(a, b);
                    if (score != DO_NOT_MERGE) {
                        if (!currentColumn.containsKey(segmentCounter)) {
                            currentColumn.put(segmentCounter, new ArrayList<>());
                        }

                        a.setMerged(true);
                        b.setMerged(true);
                        final Element result = Element.ofMerge(a, b, score);

                        final boolean alreadyMerged = currentColumn.get(segmentCounter).stream()
                                .anyMatch(
                                        element -> element.getMinterms().equals(result.getMinterms())
                                );
                        if (!alreadyMerged) {
                            currentColumn.get(segmentCounter).add(result);
                        }
                    }
                }
            }
            segmentCounter++;
            primes.addAll(
                    currentSegment.stream()
                            .filter(element -> !element.isMerged())
                            .toList()
            );
        }

        if (currentColumn.keySet().size() > 1) {
            return doMerge(currentColumn.keySet().size(), currentColumn, primes);

        } else {
            // Add any leftover prime implicant if present
            primes.addAll(
                    currentColumn.values().stream()
                            .flatMap(Collection::stream)
                            .toList()
            );
            return primes;
        }
    }

    private int getMergedScore(Element a, Element b) {
        final int x = a.getMinterms().get(0);
        final int y = b.getMinterms().get(0);

        final int score = y - x;
        if (y > x && ((score & score - 1) == 0)) {
            if (a.getScores().isEmpty() || a.getScores().equals(b.getScores())) {
                return score;
            }
        }

        return DO_NOT_MERGE;
    }
}
