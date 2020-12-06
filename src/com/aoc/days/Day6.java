package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import com.aoc.PasswordPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/6

public class Day6 {

    public static void main(String[] args) {
        String fileName = "input6.txt";
        Stream<String> input = readFileByLine(fileName);

        List<String> lines = input.collect(Collectors.toList());
        List<String> passports = new ArrayList<>();
        String passport = "";
        for (String line : lines) {
            String p = line.trim();
            if (p.equals("")) {
                passports.add(passport.trim());
                passport = "";
            } else {
                passport = passport + p;
            }
        }
        // issue was end of file missing empty lines handling
        // adding last line from file
        passports.add(passport.trim());

        System.out.println(passports.get(0));

        long sum = passports.stream().map(s -> {
            Set<String> c = new HashSet<>();
            for (int i = 0; i < s.length(); i++) {
                c.add(s.substring(i,i+1));
            }
            return c.size();
        }).mapToLong(v->v).sum();
        System.out.println(sum);

        System.out.println(part2(lines));
    }

    private static long part2(List<String> lines){

        List<String> passports = new ArrayList<>();
        String passport = "";
        for (String line : lines) {
            String p = line.trim();
            if (p.equals("")) {
                passports.add(passport.trim());
                passport = "";
            } else {
                passport = passport + " " + p;
            }
        }
        // issue was end of file missing empty lines handling
        // adding last line from file
        passports.add(passport.trim());

        long sum = passports.stream().map(group -> {
            List<String> individuals = Arrays.asList(group.split(" "));
            Map<String, Integer> c = new HashMap<>();
            for (String individual : individuals) {
                for (int i = 0; i < individual.length(); i++) {
                    int count = c.getOrDefault(individual.substring(i,i+1), 0);
                    c.put(individual.substring(i,i+1), count + 1);
                }
            }
            return c.entrySet().stream().filter(es -> es.getValue() == individuals.size()).count();

        }).mapToLong(v->v).sum();

        return sum;
    }

}
