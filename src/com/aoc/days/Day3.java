package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/3

public class Day3 {

    public static void main(String[] args) {
        String fileName = "input3.txt";
        Stream<String> input = readFileByLine(fileName);
        List<String> rows = input.collect(Collectors.toList());

        long treeCount1 = slope(1, 1, rows);
        //part 1
        long treeCount2 = slope(3, 1, rows);
        System.out.println(treeCount2);
        long treeCount3 = slope(5, 1, rows);
        long treeCount4 = slope(7, 1, rows);
        long treeCount5 = slope(1, 2, rows);
        long mul = treeCount1 * treeCount2 * treeCount3 * treeCount4 * treeCount5;
        //part 2
        System.out.println(mul);
    }

    private static long slope(int right, int down, List<String> rows) {
        AtomicInteger curIndex = new AtomicInteger();
        AtomicInteger treeCount = new AtomicInteger();
        AtomicInteger rowNum = new AtomicInteger();

        rows.forEach(row -> {
            if (rowNum.get() % down == 0) {
                int len = row.length();
                int ind = curIndex.get();
                if (ind >= len) {
                    ind = ind % len;
                    curIndex.set(ind);
                }
                if ((row.charAt(ind) == '#')) {
                    treeCount.getAndIncrement();
                }
                curIndex.set(curIndex.get() + right);
            }
            rowNum.getAndIncrement();
        });
        return treeCount.get();
    }

}
