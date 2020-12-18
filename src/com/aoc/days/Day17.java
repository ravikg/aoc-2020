package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import com.aoc.Cube;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/17

public class Day17 {

    public static void main(String[] args) {
        String fileName = "input17.txt";
        Stream<String> input = readFileByLine(fileName);
        List<String> inputs = input.collect(Collectors.toList());

        Map<Cube, Boolean> cubeMap = new HashMap<>();
        AtomicInteger wmax = new AtomicInteger();
        wmax.set(0);
        AtomicInteger zmax = new AtomicInteger();
        zmax.set(0);
        AtomicInteger ymax = new AtomicInteger();
        AtomicInteger xmax = new AtomicInteger();
        for (String line : inputs) {
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                Cube cube = new Cube(i, ymax.get(), zmax.get(), wmax.get());
                if (c == '#') {
                    cubeMap.put(cube, true);
                } else {
                    cubeMap.put(cube, false);
                }
            }
            xmax.set(line.length());
            ymax.getAndIncrement();
        }

        Map<Cube, Boolean> cubeMapUpd = new HashMap<>();
        int xmin = 0, ymin = 0, zmin = 0, wmin = 0;
        while (zmax.get() < 6) {
            for (int i = xmin - 1; i < xmax.get() + 1; i++) {
                for (int j = ymin - 1; j < ymax.get() + 2; j++) {
                    for (int k = zmin - 1; k < zmax.get() + 2; k++) {
                        for (int l = wmin - 1; l < wmax.get() + 2; l++) {
                            Cube cube = new Cube(i, j, k, l);
                            boolean ns = newState(cube, cubeMap);
                            cubeMapUpd.put(cube, ns);
                        }
                    }
                }
            }
            xmax.getAndIncrement();
            ymax.getAndIncrement();
            zmax.getAndIncrement();
            wmax.getAndIncrement();
            xmin--;
            ymin--;
            zmin--;
            wmin--;
            cubeMap = cubeMapUpd;
            cubeMapUpd = new HashMap<>();
        }

        // for part1 : need to comment the code of dimension w(max, min) : 291
        //part 2: 1524
        System.out.println(cubeMap.values().stream().filter(Boolean::booleanValue).count());

    }

    private static boolean newState(Cube cube, Map<Cube, Boolean> cubeMap) {
        int x = cube.x;
        int y = cube.y;
        int z = cube.z;
        int w = cube.w;
        int on = 0;
        int off = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        if (i == 0 && j == 0 && k == 0 && l == 0) {
                            continue;
                        }
                        Cube neighbour = new Cube(x + i, y + j, z + k, w + l);
                        Boolean nc = cubeMap.getOrDefault(neighbour, false);
                        if (nc) {
                            on++;
                        } else {
                            off++;
                        }
                    }
                }
            }
        }
        Boolean current = cubeMap.getOrDefault(cube, false);
        if (current) {
            if (on == 2 || on == 3) {
                return true;
            } else {
                return false;
            }
        } else {
            if (on == 3) {
                return true;
            } else {
                return false;
            }
        }
    }

}
