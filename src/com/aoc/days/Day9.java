package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/2

public class Day9 {

    public static void main(String[] args) {
        String fileName = "input9.txt";
        Stream<String> input = readFileByLine(fileName);

        List<Long> numbers = input.map(Long::parseLong).collect(Collectors.toList());
        int p = 25;
        long invalid = 0l;
        for (int i = p; i < numbers.size(); i++) {
            if (!notValid(i, numbers, p)) {
                invalid = numbers.get(i);
            }
        }

        //part 1 : 1492208709
        System.out.println(invalid);

        //part 2 : 238243506
        long ew = encryWeek(numbers, invalid);
        System.out.println(ew);

    }

    private static boolean notValid(int ind, List<Long> numbers, int p) {
        long num = numbers.get(ind);
        Set<Long> n = new HashSet<>();
        for (int i = ind - p; i < ind; i++) {
            n.add(numbers.get(i));
        }
        for (long a : n) {
            if (n.contains(num - a)) {
                return true;
            }
        }
        return false;
    }

    private static long encryWeek(List<Long> numbers, long invalidNum) {
        LinkedList<Long> n = new LinkedList<>();
        long sum = 0;
        for (long jth : numbers) {
            n.addLast(jth);
            sum = sum + jth;
            if (sum == invalidNum) {
                long min = n.stream().mapToLong(v -> v).min().getAsLong();
                long max = n.stream().mapToLong(v -> v).max().getAsLong();
                return min + max;
            }
            while (sum > invalidNum) {
                long r = n.removeFirst();
                sum = sum - r;
                if (sum == invalidNum) {
                    long min = n.stream().mapToLong(v -> v).min().getAsLong();
                    long max = n.stream().mapToLong(v -> v).max().getAsLong();
                    return min + max;
                }
            }
        }
        return 0L;
    }

}
