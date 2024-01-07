package dev.bodid.cli;

import java.util.List;
import java.util.Optional;

/**
 * @author danielbodi
 * @created 2023/12/10
 */
public class ApplicationArguments {
    private final int variableCount;
    private final List<Integer> minterms;
    private final List<Integer> dontCares;

    public ApplicationArguments(int variableCount, List<Integer> mintermList, List<Integer> dontCares) {
        this.variableCount = variableCount;
        this.minterms = mintermList;
        this.dontCares = dontCares;
    }

    public int getVariableCount() {
        return variableCount;
    }

    public List<Integer> getMinterms() {
        return minterms;
    }

    public Optional<List<Integer>> getDontCares() {
        return Optional.ofNullable(dontCares);
    }
}
