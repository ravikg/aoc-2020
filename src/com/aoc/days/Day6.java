package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/6

public class Day6 {

    public static void main(String[] args) {
        String fileName = "input6.txt";
        Stream<String> input = readFileByLine(fileName);

        List<String> passports = new ArrayList<>();
        List<Long> groups = new ArrayList<>();
        passports.add("");
        groups.add(0L);
        AtomicInteger ind = new AtomicInteger();
        AtomicLong groupCount = new AtomicLong();
        input.forEach(line -> {
            if (line.trim().equals("")) {
                ind.getAndIncrement();
                passports.add("");
                groups.add(0L);
                groupCount.set(0);
            } else {
                passports.set(ind.get(), passports.get(ind.get()) + line);
                groups.set(ind.get(), groupCount.incrementAndGet());
            }
        });

        long sum = passports.stream()
                .map(s -> s.chars().distinct().count())
                .mapToLong(v -> v).sum();

        System.out.println(sum);
        System.out.println(part2(passports, groups));
    }

    private static long part2(List<String> passports, List<Long> groups) {
        AtomicInteger ind = new AtomicInteger(0);
        return passports.stream().map(group -> {
            Map<String, Long> mc = group.chars()
                    .mapToObj(c -> Character.toString((char) c))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            long groupCount = groups.get(ind.getAndIncrement());
            return mc.entrySet().stream().filter(es -> es.getValue().equals(groupCount)).count();
        }).mapToLong(v -> v).sum();
    }

}
