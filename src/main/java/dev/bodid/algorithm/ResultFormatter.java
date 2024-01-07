package dev.bodid.algorithm;

import java.util.List;

/**
 * @author danielbodi
 * @created 2023/12/10
 */
public class ResultFormatter {

    public String format(int variableCount, List<Element> primes) {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < primes.size(); i++) {
            final char[] c = toBinWithLeadingZeros(variableCount, primes.get(i).getMinterms().get(0));
            final List<char[]> sc = primes.get(i).getScores().stream()
                    .map(score -> toBinWithLeadingZeros(variableCount, score))
                    .toList();

            sb.append('(');
            for (int j = 0; j < c.length; j++) {
                final int k = j;
                final boolean skip = sc.stream()
                        .map(chars -> chars[k])
                        .anyMatch(character -> character == '1');
                if (skip) {
                    continue;
                }

                if (c[j] == '0') {
                    sb.append('/');
                }
                sb.append((char) (65 + j));
            }
            sb.append(')');

            if (i != primes.size() - 1) {
                sb.append('+');
            }
        }

        return sb.toString();
    }

    private char[] toBinWithLeadingZeros(int targetLength, int number) {
        final String s = Integer.toBinaryString(number);
        if (s.length() >= targetLength) {
            return s.toCharArray();
        }

        final StringBuilder sb = new StringBuilder();
        while (sb.length() < targetLength - s.length()) {
            sb.append('0');
        }
        sb.append(s);

        return sb.toString().toCharArray();
    }
}
