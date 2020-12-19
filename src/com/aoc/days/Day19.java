package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/19

public class Day19 {

    public static void main(String[] args) {
        String fileName = "input19.txt";
        Stream<String> input = readFileByLine(fileName);

        Map<Boolean, List<String>> parts = input.collect(Collectors.partitioningBy(line -> line.contains(":")));
        List<String> ruleLines = parts.get(true);
        List<String> messages = parts.get(false);

        // rule id -> rule
        Map<Integer, HashSet<String>> rulesMap = new HashMap<>();
        // rule id -> boolean rule expanded or not
        Map<Integer, Boolean> ruleExpanded = new HashMap<>();
        for (String ruleLine : ruleLines) {
            String[] s = ruleLine.split(": ");
            int ruleId = Integer.parseInt(s[0]);
            if (s[1].contains("\"")) {
                HashSet<String> set = new HashSet<String>();
                set.add(s[1].substring(1, s[1].length() - 1));
                rulesMap.put(ruleId, set);
                ruleExpanded.put(ruleId, true);
            } else {
                HashSet<String> set = new HashSet<>(Arrays.asList(s[1].split(" \\| ")));
                rulesMap.put(ruleId, set);
                ruleExpanded.put(ruleId, false);
            }
        }

        HashSet<String> rules0 = getRules(0, rulesMap, ruleExpanded);

        //part 1
        //8: 42
        //11: 42 31
        //0: 8 11
        int match = 0;
        List<String> remains = messages.stream().filter(m -> !rules0.contains(m)).collect(Collectors.toList());

        match = messages.size() - remains.size();
        System.out.println(match); //part 1 : 182

        //part 2
        //8: 42 | 42 8 => 42 42 42  => (42)y+ y>1
        //11: 42 31 | 42 11 31 => 42 42 11 31 31 => (42)x+ (31)x+ x>1
        //0: 8 11 = (42)(y+x)+ (31)x+;
        HashSet<String> rules42 = getRules(42, rulesMap, ruleExpanded);
        HashSet<String> rules31 = getRules(31, rulesMap, ruleExpanded);
        List<String> remains2 = remains.stream().filter(m -> {
            // m should ends with x(>1) 31 rules
            // before x 31 rules, x+1 42 rules should appear

            //first check how many 31 rules m ends with
            String m31 = m;
            int x = 0;
            while (true) {
                String k = m31;
                for (String rule : rules31) {
                    if (m31.endsWith(rule)) {
                        x++;
                        m31 = m31.substring(0, m31.length() - rule.length());
                    }

                }
                if (k.equals(m31)) {
                    break;
                }
            }
            if (x < 1) {
                return true;
            }

            //now check remaining string has x+1 42 rules
            String m42 = m31;
            int y = x + 1;
            while (true) {
                String k = m42;
                for (String rule : rules42) {
                    if (m42.endsWith(rule)) {
                        y--;
                        m42 = m42.substring(0, m42.length() - rule.length());
                    }

                }
                if (k.equals(m42)) {
                    break;
                }
            }
            if (y > 0) {
                return true;
            }
            if (!m42.equals("")) {
                return true;
            }
            return false;

        }).collect(Collectors.toList());

        match = messages.size() - remains2.size();
        System.out.println(match); //part 2 : 334
    }

    private static HashSet<String> getRules(int ruleId, Map<Integer, HashSet<String>> rulesMap,
            Map<Integer, Boolean> ruleExpanded) {
        HashSet<String> rules = new HashSet<>();
        HashSet<String> rule = rulesMap.get(ruleId);
        if (ruleExpanded.get(ruleId)) {
            return rule;
        }

        for (String r : rule) {
            String[] sn = r.split(" ");
            HashSet<String> rl = new HashSet<>();//init rule
            for (String n : sn) {
                HashSet<String> concatRule = getRules(Integer.parseInt(n), rulesMap, ruleExpanded);
                HashSet<String> concated = new HashSet<>();
                for (String start : rl) {
                    for (String cr : concatRule) {
                        concated.add(start + cr);
                    }
                }
                if (rl.isEmpty()) {
                    rl = concatRule; //start case
                } else {
                    rl = concated;
                }
            }
            rules.addAll(rl);
        }

        rulesMap.put(ruleId, rules);
        ruleExpanded.put(ruleId, true);
        return rules;
    }

}
