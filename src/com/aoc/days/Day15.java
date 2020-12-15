package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/15

public class Day15 {

    public static void main(String[] args) {
        String fileName = "input15.txt";
        Stream<String> input = readFileByLine(fileName);

        List<Long> inputs = input.map(Long::parseLong).collect(Collectors.toList());

        Map<Long, List<Integer>> numIndexMap = new HashMap<>();

        for (int i = 0; i < inputs.size(); i++) {
            long num = inputs.get(i);
            List<Integer> index = numIndexMap.getOrDefault(num, new ArrayList<>());
            index.add(i);
            numIndexMap.put(num, index);
        }

        int len = inputs.size();
        int get = 2020; //part1
//        int get = 30000000; //part2
        for (int i = len - 1; i < get; i++) {
            long last = inputs.get(i);
            List<Integer> index = numIndexMap.getOrDefault(last, new ArrayList<>());
            if (index.size() == 1) {
                last = 0;
            } else {
                int size = index.size();
                last = index.get(size - 1) - index.get(size - 2);
            }
            inputs.add(last);
            index = numIndexMap.getOrDefault(last, new ArrayList<>());
            index.add(i + 1);
            numIndexMap.put(last, index);
        }

        System.out.println(inputs.get(get - 1));
    }

}
