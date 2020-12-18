package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import com.aoc.Rules16;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/16

public class Day16 {

    public static void main(String[] args) {
        String fileName = "input16.txt";
        Stream<String> input = readFileByLine(fileName);

        List<String> inputs = input.collect(Collectors.toList());
        List<Rules16> rules16s = new ArrayList<>();

        int ind = 0;

        for (int i = ind; i < inputs.size(); i++) {
            String rule = inputs.get(i);
            if (rule.contains(":")) {
                rules16s.add(new Rules16(rule));
            } else {
                break;
            }
            ind++;
        }
        ind += 2;//your ticket index
        String yourTicket = inputs.get(ind);

        ind += 3;
        List<String> tickets = new ArrayList<>();
        for (int i = ind; i < inputs.size(); i++) {
            tickets.add(inputs.get(i));
        }

        List<String> invalids = tickets.stream().filter(s -> {
            boolean valid;
            String[] fields = s.split(",");
            int validFields = 0;
            for (String field : fields) {
                boolean validField = false;
                for (Rules16 rule : rules16s) {
                    valid = rule.validField(field);
                    if (valid) {
                        validField = true;
                        break;
                    }
                }
                if (validField) {
                    validFields++;
                }
            }
            valid = validFields == fields.length;
            return !(valid);
        }).collect(Collectors.toList());

        int sum = invalids.stream().map(s -> {
            boolean valid;
            String[] fields = s.split(",");
            for (String field : fields) {
                boolean validField = false;
                for (Rules16 rule : rules16s) {
                    valid = rule.validField(field);
                    if (valid) {
                        validField = true;
                        break;
                    }
                }
                if (!validField) {
                    return Integer.parseInt(field);
                }
            }
            return 0;
        }).mapToInt(v -> v).sum();

        //21980 part 1
        System.out.println(sum);

        //1439429522627 part 2
        part2();
    }


    public static void part2() {
        String fileName = "input16.txt";
        Stream<String> input = readFileByLine(fileName);

        List<String> inputs = input.collect(Collectors.toList());
        List<Rules16> rules16s = new ArrayList<>();

        int ind = 0;

        for (int i = ind; i < inputs.size(); i++) {
            String rule = inputs.get(i);
            if (rule.contains(":")) {
                rules16s.add(new Rules16(rule));
            } else {
                break;
            }
            ind++;
        }
        ind += 2;//your ticket index
        String[] yourTicket = inputs.get(ind).split(",");

        ind += 3;
        List<String> tickets = new ArrayList<>();
        for (int i = ind; i < inputs.size(); i++) {
            tickets.add(inputs.get(i));
        }

        List<String> valids = tickets.stream().filter(s -> {
            boolean valid;
            String[] fields = s.split(",");
            int validFields = 0;
            for (String field : fields) {
                boolean validField = false;
                for (Rules16 rule : rules16s) {
                    valid = rule.validField(field);
                    if (valid) {
                        validField = true;
                        break;
                    }
                }
                if (validField) {
                    validFields++;
                }
            }
            valid = validFields == fields.length;
            return valid;
        }).collect(Collectors.toList());

        // field => list of rules
        List<Map<Integer, HashSet<Rules16>>> maps = valids.stream().map(s -> {
            String[] fields = s.split(",");
            Map<Integer, HashSet<Rules16>> rulesMap = new HashMap<>();
            for (int i = 0; i < fields.length; i++) {
                String field = fields[i];
                for (Rules16 rule : rules16s) {
                    boolean valid = rule.validField(field);
                    if (valid) {
                        HashSet<Rules16> rules16s1 = rulesMap.getOrDefault(i, new HashSet<>());
                        rules16s1.add(rule);
                        rulesMap.put(i, rules16s1);
                    }
                }
            }
            return rulesMap;
        }).collect(Collectors.toList());

        Map<Integer, HashSet<Rules16>> rulesMapF = new HashMap<>();
        for (int i = 0; i < maps.size(); i++) {
            Map<Integer, HashSet<Rules16>> rulesMap = maps.get(i);
            for (Entry<Integer, HashSet<Rules16>> entry : rulesMap.entrySet()) {
                HashSet<Rules16> r = entry.getValue();
                Integer index = entry.getKey();

                HashSet<Rules16> r2 = rulesMapF.get(index);
                if (r2 == null) {
                    r2 = new HashSet<>(r);
                    rulesMapF.put(index, r2);
                } else {
                    r2.retainAll(r);
                }
            }
        }

        Map<Rules16, Integer> ruleMap = new HashMap<>();
        Set<Integer> indexUsed = new HashSet<>();
        int size = 0;
        long departureMultiply = 1L;
        while (size != 20) {
            for (Entry<Integer, HashSet<Rules16>> entry : rulesMapF.entrySet()) {
                HashSet<Rules16> r = entry.getValue();
                Integer index = entry.getKey();
                if (indexUsed.contains(index)) {
                    continue;
                }

                r.removeAll(ruleMap.keySet());
                if (r.size() == 1) {
                    Rules16 rules16 = new ArrayList<Rules16>(r).get(0);
                    if (!ruleMap.containsKey(rules16)) {
                        if (rules16.field.contains("departure")) {
                            departureMultiply *= Long.parseLong(yourTicket[index]);
                        }
                        ruleMap.put(rules16, index);
                        indexUsed.add(index);
                    }
                }
            }
            size = ruleMap.size();
        }
        System.out.println(departureMultiply);
    }
}
