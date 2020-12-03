package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/1

public class Day1 {

    public static void main(String[] str) {
        String fileName = "input1.txt";
        Stream<String> input = readFileByLine(fileName);

        HashSet<Integer> nums = new HashSet<>();
        input.forEach(s -> {
            int n = Integer.parseInt(s);
            nums.add(n);
        });

        //Part 1:
        System.out.println(findProd(nums, 2020));

        //Part 2
        nums.stream().anyMatch(n -> {
            HashSet<Integer> cloned = (HashSet<Integer>) nums.clone();
            cloned.remove(n);
            AtomicLong p = findProd(cloned, 2020 - n);
            if (p != null) {
                System.out.println(p.get() * n);
                return true;
            }
            return false;
        });

    }

    private static AtomicLong findProd(Set<Integer> nums, Integer sum) {
        AtomicLong p = new AtomicLong();
        boolean b = nums.stream().anyMatch(n -> {
            Integer d = sum - n;
            if (nums.contains(d)) {
                p.set(n * d);
                return true;
            }
            return false;
        });
        return b ? p : null;
    }

}
