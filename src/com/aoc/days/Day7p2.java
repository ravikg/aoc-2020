package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import com.aoc.OuterBag;
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

public class Day7p2 {

    public static void main(String[] args) {
        String fileName = "input7.txt";
        Stream<String> input = readFileByLine(fileName);

        List<String> rules = input.collect(Collectors.toList());
        Map<String, List<String>> bagsMap = new HashMap<>();
        List<OuterBag> outerBags = new ArrayList<>();
        OuterBag shinyBag = new OuterBag("");
        for (String rule : rules) {
            String[] bags = rule.split(" bags contain ");
            String outerBagColour = bags[0];
            OuterBag outerBag = new OuterBag(outerBagColour);
            if (outerBagColour.equals("shiny gold")) {
                shinyBag = outerBag;
            }
            String[] innerBags = bags[1].split(", ");
            String bag1 = innerBags[0];
            List<String> rb = new ArrayList<>();
            if (!bag1.contains("no other")) {
                for (String bag : innerBags) {
                    String[] parts = bag.split(" ");
                    int innerBagCount = Integer.parseInt(parts[0]);
                    String innerBagColour = parts[1] + " " + parts[2];
                    rb.add(innerBagColour);
                    outerBag.addBag(innerBagColour, innerBagCount);
                }
                bagsMap.put(outerBagColour, rb);
            }
            outerBags.add(outerBag);
        }

        Map<String, OuterBag> remainingBagsMap = outerBags.stream()
                .collect(Collectors.toMap(OuterBag::getColour, outerBag -> outerBag));

        long count = countBag(shinyBag, remainingBagsMap);
        Set<String> oBags = new HashSet<>();
        oBags.add("shiny gold");

        System.out.println(count-1);
    }

    private static long countBag(OuterBag outerBag, Map<String, OuterBag> bagsMap) {
        long count = 1;
        for (Entry<String, Integer> m : outerBag.getInnerBagCountMap().entrySet()) {
            String col = m.getKey();
            int cou = m.getValue();
            OuterBag iBag = bagsMap.get(col);
            if (iBag != null) {
                count += cou * countBag(iBag, bagsMap);
            } else {
                count += cou;
            }
        }
        return count;
    }

}
