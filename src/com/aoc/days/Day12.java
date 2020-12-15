package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/12
public class Day12 {

    public static void main(String[] args) {
        String fileName = "input12.txt";
        Stream<String> input = readFileByLine(fileName);

        AtomicReference<String> currentDirection = new AtomicReference<>("E");
        Map<String, Integer> dist = new HashMap<>();
        dist.put("N", 0);
        dist.put("S", 0);
        dist.put("E", 0);
        dist.put("W", 0);
        input.forEach(ins -> {
            String dir = ins.substring(0, 1);
            int unit = Integer.parseInt(ins.substring(1));
            if (dir.equals("L")) {
                for (int i = 0; i < unit / 90; i++) {
                    switch (currentDirection.get()) {
                        case "E":
                            currentDirection.set("N");
                            break;
                        case "W":
                            currentDirection.set("S");
                            break;
                        case "N":
                            currentDirection.set("W");
                            break;
                        case "S":
                            currentDirection.set("E");
                            break;
                    }
                }
            } else if (dir.equals("R")) {
                for (int i = 0; i < unit / 90; i++) {
                    switch (currentDirection.get()) {
                        case "E":
                            currentDirection.set("S");
                            break;
                        case "W":
                            currentDirection.set("N");
                            break;
                        case "N":
                            currentDirection.set("E");
                            break;
                        case "S":
                            currentDirection.set("W");
                            break;
                    }
                }
            } else if (dir.equals("F")) {
                int d = unit + dist.getOrDefault(currentDirection.get(), 0);
                dist.put(currentDirection.get(), d);
            } else {
                int d = unit + dist.getOrDefault(dir, 0);
                dist.put(dir, d);
            }
        });

        int d1 = Math.abs(dist.get("N") - dist.get("S"));
        int d2 = Math.abs(dist.get("E") - dist.get("W"));
        System.out.println(d1 + d2);

        part2();
    }

    public static void part2() {
        String fileName = "input12.txt";
        Stream<String> input = readFileByLine(fileName);

        AtomicReference<String> currentDirection = new AtomicReference<>("E");
        Map<String, Long> wp = new HashMap<>();
        wp.put("N", 1L);
        wp.put("S", 0L);
        wp.put("E", 10L);
        wp.put("W", 0L);

        Map<String, Long> ship = new HashMap<>();
        ship.put("N", 0L);
        ship.put("S", 0L);
        ship.put("E", 0L);
        ship.put("W", 0L);

        input.forEach(ins -> {
            String dir = ins.substring(0, 1);
            int unit = Integer.parseInt(ins.substring(1));
            if (dir.equals("L")) {
                for (int i = 0; i < unit / 90; i++) {
                    long e = wp.get("E");
                    long w = wp.get("W");
                    long n = wp.get("N");
                    long s = wp.get("S");
                    for (String dirr : wp.keySet()) {
                        switch (dirr) {
                            case "E":
                                currentDirection.set("N");
                                wp.put("N", e);
                                break;
                            case "W":
                                currentDirection.set("S");
                                wp.put("S", w);
                                break;
                            case "N":
                                currentDirection.set("W");
                                wp.put("W", n);
                                break;
                            case "S":
                                currentDirection.set("E");
                                wp.put("E", s);
                                break;
                        }
                    }
                }
            } else if (dir.equals("R")) {
                for (int i = 0; i < unit / 90; i++) {
                    long e = wp.get("E");
                    long w = wp.get("W");
                    long n = wp.get("N");
                    long s = wp.get("S");
                    for (String dirr : wp.keySet()) {
                        switch (dirr) {
                            case "E":
                                currentDirection.set("S");
                                wp.put("S", e);
                                break;
                            case "W":
                                currentDirection.set("N");
                                wp.put("N", w);
                                break;
                            case "N":
                                currentDirection.set("E");
                                wp.put("E", n);
                                break;
                            case "S":
                                currentDirection.set("W");
                                wp.put("W", s);
                                break;
                        }
                    }
                }
            } else if (dir.equals("F")) {
                for (String dirr : wp.keySet()) {
                    long d = unit * wp.get(dirr) + ship.get(dirr);
                    ship.put(dirr, d);
                }
            } else {
                long d = unit + wp.get(dir);
                wp.put(dir, d);
            }
        });

        long d1 = Math.abs(ship.get("N") - ship.get("S"));
        long d2 = Math.abs(ship.get("E") - ship.get("W"));
        System.out.println(d1 + d2);
    }

}
