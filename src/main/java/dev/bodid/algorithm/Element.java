package dev.bodid.algorithm;

import java.util.*;

/**
 * @author danielbodi
 * @created 2023/12/10
 */
public class Element {

    private static final String TO_STRING_PATTERN = "%s (%s)";
    private static final String DELIMITER = ",";

    private final List<Integer> minterms = new ArrayList<>();
    private final List<Integer> scores = new ArrayList<>();
    private boolean merged = false;

    public static Element with(int initialMinterm) {
        final Element element = new Element();
        element.minterms.add(initialMinterm);
        return element;
    }

    public static Element ofMerge(Element a, Element b, int score) {
        final Element element = new Element();
        element.minterms.addAll(a.getMinterms());
        element.minterms.addAll(b.getMinterms());

        if (!a.getScores().isEmpty()) {
            element.scores.addAll(a.getScores());
        }
        element.scores.add(score);

        Collections.sort(element.minterms);
        Collections.sort(element.scores);

        return element;
    }

    public List<Integer> getMinterms() {
        return minterms;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public boolean isMerged() {
        return merged;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return merged == element.merged
                && Objects.equals(minterms, element.minterms)
                && Objects.equals(scores, element.scores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minterms, scores, merged);
    }

    @Override
    public String toString() {
        return TO_STRING_PATTERN.formatted(
                String.join(DELIMITER, minterms.stream().map(Objects::toString).toList()),
                String.join(DELIMITER, scores.stream().map(Objects::toString).toList())
        );
    }
}
