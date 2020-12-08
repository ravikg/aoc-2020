package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/8

public class Day8 {

    public static void main(String[] args) {
        String fileName = "input8.txt";
        Stream<String> input = readFileByLine(fileName);

        List<String> instructions = input.collect(Collectors.toList());

        System.out.println(part1(instructions));
        System.out.println(part2(instructions));
    }

    private static long part1(List<String> instructions) {
        boolean repeat = false;

        boolean[] visited = new boolean[instructions.size()];
        long accumulator = 0;
        int next = 0;
        int i = -1;
        while (!repeat) {
            visited[next] = true;
            String instruction = instructions.get(next);
            String[] parts = instruction.split(" ");
            switch (parts[0]) {
                case "nop":
                    next++;
                    break;
                case "jmp":
                    next = next + Integer.parseInt(parts[1]);
                    break;
                case "acc":
                    next++;
                    accumulator = accumulator + Integer.parseInt(parts[1]);
            }

            if (visited[next]) {
                repeat = true;
            }
        }

        return accumulator;
    }

    private static long part2(List<String> instructions) {
        int last = instructions.size() - 1;

        boolean repeat;
        boolean terminate = false;
        long accumulator = 0;

        for (int i = 0; i < instructions.size(); i++) {
            if (instructions.get(i).contains("acc")) {
                continue;
            }

            repeat = false;
            boolean[] visited = new boolean[instructions.size()];
            accumulator = 0;
            int next = 0;

            while (!repeat) {
                visited[next] = true;
                String instruction = instructions.get(next);
                String[] parts = instruction.split(" ");
                switch (parts[0]) {
                    case "nop":
                        if (next == i) {
                            next = next + Integer.parseInt(parts[1]);
                        } else {
                            next++;
                        }
                        break;
                    case "jmp":
                        if (next == i) {
                            next++;
                        } else {
                            next = next + Integer.parseInt(parts[1]);
                        }
                        break;
                    case "acc":
                        next++;
                        accumulator = accumulator + Integer.parseInt(parts[1]);
                }

                if (next > last) {
                    terminate = true;
                    break;
                }

                if (visited[next]) {
                    repeat = true;
                }
            }
            if (terminate) {
                break;
            }
        }

        return accumulator;
    }

}
