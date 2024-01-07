package dev.bodid.application;

import dev.bodid.algorithm.Element;
import dev.bodid.algorithm.MintermMerger;
import dev.bodid.algorithm.ResultFormatter;
import dev.bodid.algorithm.XHelperFunction;
import dev.bodid.cli.ApplicationArguments;
import dev.bodid.cli.CliArgumentParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author danielbodi
 * @created 2023/12/10
 */
public class QuineMcLuskeyApplication {

    public void run(String[] arguments) {
        final ApplicationArguments applicationArguments = parse(arguments);

        final Map<Character, Element> primesMappedByNames = merge(applicationArguments.getMinterms(), applicationArguments.getDontCares());
        final List<Element> xHelperFunctionResult = simplify(applicationArguments.getMinterms(), primesMappedByNames);
        final String simplestFunctionString = format(applicationArguments.getVariableCount(), xHelperFunctionResult);

        System.out.println("Function (simplest form):");
        System.out.printf("Q=%s%n", simplestFunctionString);
        System.out.println();

        final String withoutHazardFunctionString = format(applicationArguments.getVariableCount(), primesMappedByNames.values().stream().toList());
        System.out.println("Function (without hazards):");
        System.out.printf("Q=%s%n", withoutHazardFunctionString);
    }

    private ApplicationArguments parse(String[] arguments) {
        final CliArgumentParser cliArgumentParser = new CliArgumentParser();
        final ApplicationArguments applicationArguments = cliArgumentParser.parse(arguments);

        System.out.printf("Variable count: %s%n", applicationArguments.getVariableCount());
        System.out.printf("Minterms to consider: %s%n", applicationArguments.getMinterms());
        System.out.printf("Dont cares: %s%n%n", applicationArguments.getDontCares());

        return applicationArguments;
    }

    private Map<Character, Element> merge(List<Integer> minterms, Optional<List<Integer>> dontCares) {
        final List<Integer> mintermsToMerge = new ArrayList<>(minterms);
        dontCares.ifPresent(mintermsToMerge::addAll);

        final MintermMerger mintermMerger = new MintermMerger();
        final Map<Character, Element> primesMappedByNames = mintermMerger.merge(mintermsToMerge);

        System.out.println("Prime implicants:");
        for (Character c : primesMappedByNames.keySet()) {
            System.out.println(primesMappedByNames.get(c));
        }
        System.out.println();

        return primesMappedByNames;
    }

    private List<Element> simplify(List<Integer> minterms, Map<Character, Element> primesMappedByNames) {
        final XHelperFunction xHelperFunction = new XHelperFunction();
        final List<Element> xHelperFunctionResult = xHelperFunction.simplify(minterms, primesMappedByNames);

        System.out.println("Prime implicants for simplest form:");
        xHelperFunctionResult.forEach(System.out::println);
        System.out.println();

        return xHelperFunctionResult;
    }

    private String format(int variableCount, List<Element> primes) {
        final ResultFormatter resultFormatter = new ResultFormatter();
        return resultFormatter.format(variableCount, primes);
    }
}
