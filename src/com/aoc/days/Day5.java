package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/5

public class Day5 {

    public static void main(String[] args) {
        String fileName = "input5.txt";
        Stream<String> input = readFileByLine(fileName);

        //sorted seat
        List<Long> seatIds = input.map(Day5::seatId).sorted().collect(Collectors.toList());
        //part1
        long maxSeatId = seatIds.get(seatIds.size() - 1);
        System.out.println(maxSeatId);

        //part2
        List<Long> missingSeats = new ArrayList<>();
        for (long i = 0; i < maxSeatId; i++) {
            if (!seatIds.contains(i)) {
                missingSeats.add(i);
            }
        }
        //choose the missing one
        long m = missingSeats.stream().reduce(0L, (mis, ele) -> {
            if (ele == mis + 1) {
                return mis;
            } else {
                return ele;
            }
        });
        System.out.println(m);

        //part2 - another sol.
        long misSeat = 0;
        for (int i = 1; i < seatIds.size() - 1; i++) {
            long cur = seatIds.get(i);
            long prev = seatIds.get(i - 1);
            long next = seatIds.get(i + 1);
            if (cur == prev + 2 && cur == next - 1) {
                misSeat = cur - 1;
                break;
            } else if (cur == next - 2 && cur == prev + 1) {
                misSeat = cur + 1;
                break;
            }
        }
        System.out.println(misSeat);

        //part 2 - another sol.
        long minSeatId = seatIds.get(0);
        seatIds.stream()
                .filter(s -> (!seatIds.contains(s - 1) || !seatIds.contains(s + 1)) && (s != maxSeatId
                        && s != minSeatId))
                .forEach(System.out::println);//should print two adjacent seats
    }

    private static long seatId(String pass) {
        long row = 0;
        long col = 0;
        int rowS = 0;//start
        int rowE = 127;//end
        for (int i = 0; i < 6; i++) {
            if (pass.charAt(i) == 'F') {
                rowE = (rowS + rowE - 1) / 2;
            }
            if (pass.charAt(i) == 'B') {
                rowS = (rowS + rowE + 1) / 2;
            }
        }
        if (pass.charAt(6) == 'F') {
            row = rowS;
        }
        if (pass.charAt(6) == 'B') {
            row = rowE;
        }

        int colS = 0;
        int colE = 7;
        for (int i = 7; i < 9; i++) {
            if (pass.charAt(i) == 'L') {
                colE = (colS + colE - 1) / 2;
            }
            if (pass.charAt(i) == 'R') {
                colS = (colS + colE + 1) / 2;
            }
        }
        if (pass.charAt(9) == 'L') {
            col = colS;
        }
        if (pass.charAt(9) == 'R') {
            col = colE;
        }

        return row * 8 + col;
    }

}
