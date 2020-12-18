package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/18

public class Day18 {

    public static void main(String[] str) {
        String fileName = "input18.txt";
        Stream<String> input = readFileByLine(fileName);
        List<String> expressions = input.collect(Collectors.toList());
        long part1 = expressions.stream().map(e -> removeParentheses(e, true)).mapToLong(v -> v).sum();
        long part2 = expressions.stream().map(e -> removeParentheses(e, false)).mapToLong(v -> v).sum();

        System.out.println(part1); // 11076907812171

        System.out.println(part2); // 283729053022731
    }

    //this works for expression with no (, ) in it
    //part 1
    private static long evaluateP1(String expression) {
        String[] n = expression.split(" ");
        long value = Long.parseLong(n[0]);
        int ind = 1;
        while (ind < n.length) {
            long x = Long.parseLong(n[ind + 1]);
            if (n[ind].equals("+")) {
                value += x;
            } else {
                value *= x;
            }
            ind += 2;
        }
        return value;
    }

    private static long removeParentheses(String expression, boolean part1) {
        //if no parentheses
        while (expression.indexOf('(') != -1) {
            //find last of 'open (' and first 'close )' which occurs after 'open ('
            // this need to be evaluated first
            int openInd = expression.lastIndexOf('(');
            int closeInd = expression.indexOf(')', openInd);
            String subExp = expression.substring(openInd + 1, closeInd);
            long se = part1 ? evaluateP1(subExp) : evaluateP2(subExp);
            expression = expression.substring(0, openInd) + se + expression.substring(closeInd + 1);
        }
        return part1 ? evaluateP1(expression) : evaluateP2(expression);
    }

    //this works for expression with no (, ) in it
    //part 2
    private static long evaluateP2(String expression) {
        String[] n = expression.split(" ");
        LinkedList<Long> ns = new LinkedList<>();
        long value = Long.parseLong(n[0]);
        int ind = 1;
        ns.add(value);
        while (ind < n.length) {
            long x = Long.parseLong(n[ind + 1]);
            if (n[ind].equals("+")) {
                value = ns.pollLast() + x;
                ns.addLast(value);
            } else {
                ns.add(x);
            }
            ind += 2;
        }
        value = 1L;
        for (Long aLong : ns) {
            value *= aLong;
        }
        return value;
    }
}
