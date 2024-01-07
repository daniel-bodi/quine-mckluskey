package dev.bodid.cli;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static dev.bodid.cli.CliArgumentParser.CliArgumentParserConstants.*;

/**
 * @author danielbodi
 * @created 2023/12/10
 */
public class CliArgumentParser {

    public ApplicationArguments parse(String[] args) {
        int variableCount = 0;
        List<Integer> minterms = null;
        List<Integer> dontCares = null;

        for (int i = 0; i < args.length; i++) {
            if (VARIABLE_COUNT_CLI_OPT_SHORT.equals(args[i]) || VARIABLE_COUNT_CLI_OPT_LONG.equals(args[i])) {
                if (i + 1 < args.length) {
                    variableCount = parseInt(args[++i]);
                } else {
                    exitWithError(MISSING_INPUT_ERROR_TEMPLATE.formatted(args[i]));
                }
            } else if (MINTERM_OPT_SHORT.equals(args[i]) || MINTERM_OPT_LONG.equals(args[i])) {
                if (i + 1 < args.length) {
                    minterms = parseIntList(args[++i]);
                } else {
                    exitWithError(MISSING_INPUT_ERROR_TEMPLATE.formatted(args[i]));
                }
            } else if (DONT_CARE_OPT_SHORT.equals(args[i]) || DONT_CARE_OPT_LONG.equals(args[i])) {
                if (i + 1 < args.length) {
                    dontCares = parseIntList(args[++i]);
                } else {
                    exitWithError(MISSING_INPUT_ERROR_TEMPLATE.formatted(args[i]));
                }
            } else {
                exitWithError(UNRECOGNIZED_ARG_ERROR_TEMPLATE.formatted(args[i]));
            }
        }

        if (minterms == null || minterms.isEmpty() || variableCount < 1) {
            exitWithError(INVALID_INPUT_ERROR_TEMPLATE.formatted("Invalid variable count and/or minterm list"));
        }

        return new ApplicationArguments(variableCount, minterms, dontCares);
    }

    private int parseInt(String argument) {
        try {
            return Integer.parseInt(argument);
        } catch (Exception exception) {
            exitWithError(INVALID_INPUT_ERROR_TEMPLATE.formatted(argument));
            // Only for the compiler - the function above will exit with status code 1
            return -1;
        }
    }

    private List<Integer> parseIntList(String argument) {
        try {
            return Arrays.stream(argument.split(Pattern.quote(",")))
                    .map(Integer::parseInt)
                    .toList();
        } catch (Exception exception) {
            exitWithError(INVALID_INPUT_ERROR_TEMPLATE.formatted(argument));
            // Only for the compiler - the function above will exit with status code 1
            return Collections.emptyList();
        }
    }

    private void exitWithError(String msg) {
        System.err.printf("ERROR: %s%n", msg);
        System.exit(1);
    }

    static class CliArgumentParserConstants {
        static final String VARIABLE_COUNT_CLI_OPT_SHORT = "-c";
        static final String VARIABLE_COUNT_CLI_OPT_LONG = "--variable-count";
        static final String MINTERM_OPT_SHORT = "-m";
        static final String MINTERM_OPT_LONG = "--minterms";
        static final String DONT_CARE_OPT_SHORT = "-d";
        static final String DONT_CARE_OPT_LONG = "--dont-cares";

        static final String INVALID_INPUT_ERROR_TEMPLATE = "Invalid input: [%s]";
        static final String MISSING_INPUT_ERROR_TEMPLATE = "Missing input for option: [%s]";
        static final String UNRECOGNIZED_ARG_ERROR_TEMPLATE = "Unrecognized argument: [%s]";
    }
}
