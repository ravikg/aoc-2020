package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/7

public class Day7 {

    public static void main(String[] args) {
        String fileName = "input7.txt";
        Stream<String> input = readFileByLine(fileName);

        List<String> rules = input.collect(Collectors.toList());
        Map<String, List<String>> bagsMap = new HashMap<>();
        for (String rule : rules) {
            String[] bags = rule.split(" bags contain ");
            String outerBag = bags[0];
            String[] innerBags = bags[1].split(", ");
            String bag1 = innerBags[0];
            List<String> rb = new ArrayList<>();
            if (!bag1.contains("no other")) {
                for (String bag : innerBags) {
                    String[] parts = bag.split(" ");
                    rb.add(parts[1] + " " + parts[2]);
                }
                bagsMap.put(outerBag, rb);
            }
        }

        int count = 0;
        Set<String> obags = new HashSet<>();
        Map<String, List<String>> remainingBagsMap = new HashMap<>();

        //direct count
        for (Entry<String, List<String>> entry : bagsMap.entrySet()) {
            if (entry.getValue().stream().anyMatch(bag -> bag.contains("shiny gold"))) {
                count++;
                obags.add(entry.getKey());
                System.out.println(entry.getKey());
            } else {
                remainingBagsMap.put(entry.getKey(), entry.getValue());
            }
        }

        long icount = 1;
        while (icount > 0) {
            icount = inDirectCount(remainingBagsMap, obags);
            count += icount;
        }

        System.out.println(obags);
        System.out.println(obags.size());
        System.out.println(count);
    }

    private static long inDirectCount(Map<String, List<String>> remainingbagsmap, Set<String> obags) {
        Set<String> obags2 = new HashSet<>();
        Map<String, List<String>> remainingbagsmap2 = new HashMap<>();
        long count = 0;
        for (Entry<String, List<String>> entry : remainingbagsmap.entrySet()) {
            if (entry.getValue().stream().filter(bag -> !bag.contains("shiny gold"))
                    .filter(bag -> {
                        return obags.stream().filter(obag -> {
                            return bag.contains(obag);
                        }).count() > 0;
                    }).count() > 0) {
                count++;
                obags2.add(entry.getKey());
            } else {
                remainingbagsmap2.put(entry.getKey(), entry.getValue());
            }
        }

        obags.addAll(obags2);
        remainingbagsmap.clear();
        remainingbagsmap.putAll(remainingbagsmap2);

        return count;
    }

}
