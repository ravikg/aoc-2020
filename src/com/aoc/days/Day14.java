package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/14
public class Day14 {

    public static void main(String[] args) {
        String fileName = "input14.txt";
        Stream<String> input = readFileByLine(fileName);

        List<String> inputs = input.collect(Collectors.toList());
        String maski = inputs.get(0).split(" ")[2];
        Map<String, Long> sums2 = new HashMap<>();
        for (int i = 0; i < inputs.size(); i++) {
            String[] dd = inputs.get(i).split(" ");
            if (dd[0].equals("mask")) {
                //[mask,=,110000011XX0000X101000X10X01XX001011]
                maski = dd[2];
            } else {
                //[mem[49397],=,468472]
                String address = dd[0].substring(4, dd[0].length() - 1);
                long value = Long.parseLong(dd[2]);

                String a = Long.toBinaryString(value);
                int bitlen = a.length();
                for (int j = 0; j < 36 - bitlen; j++) {
                    a = "0" + a;
                }
                StringBuilder nb = new StringBuilder(a);
                for (int k = 0; k < 36; k++) {
                    char nc = maski.charAt(k);
                    if (nc != 'X') {
                        nb.setCharAt(k, nc);
                    }
                }
                sums2.put(address, Long.parseLong(nb.toString(), 2));
            }
        }

        long sum222 = sums2.values().stream().mapToLong(v -> v).sum();
        System.out.println(sum222);//part1

        part2();

    }

    public static void part2() {
        String fileName = "input14.txt";
        Stream<String> input = readFileByLine(fileName);

        System.out.println("--------------");

        List<String> inputs = input.collect(Collectors.toList());
        String maski = inputs.get(0).split(" ")[2];
        Map<Long, Long> sums2 = new HashMap<>();
        for (int i = 0; i < inputs.size(); i++) {
            String[] dd = inputs.get(i).split(" ");
            if (dd[0].equals("mask")) {
                //[mask,=,110000011XX0000X101000X10X01XX001011]
                maski = dd[2];
            } else {
                //[mem[49397],=,468472]
                long address = Long.parseLong(dd[0].substring(4, dd[0].length() - 1));
                long value = Long.parseLong(dd[2]);

                String a = Long.toBinaryString(address);
                int bitlen = a.length();
                for (int j = 0; j < 36 - bitlen; j++) {
                    a = "0" + a;
                }
                StringBuilder nb = new StringBuilder(a);
                for (int k = 0; k < 36; k++) {
                    char nc = maski.charAt(k);
                    if (nc != '0') {
                        nb.setCharAt(k, nc);
                    }
                }
                List<Long> addresses = address(nb.toString());
                for (int j = 0; j < addresses.size(); j++) {
                    sums2.put(addresses.get(j), value);
                }

            }

        }

        long sum222 = sums2.values().stream().mapToLong(v -> v).sum();
        System.out.println(sum222);

    }

    private static List<Long> address(String addWithX) {
        int ind = addWithX.indexOf('X');
        if (ind == -1) {
            List<Long> ls1 = new ArrayList<>();
            ls1.add(Long.parseLong(addWithX, 2));
            return ls1;
        } else {
            StringBuilder s1 = new StringBuilder(addWithX);
            s1.setCharAt(ind, '0');
            List<Long> ls1 = address(s1.toString());

            StringBuilder s2 = new StringBuilder(addWithX);
            s2.setCharAt(ind, '1');
            List<Long> ls2 = address(s2.toString());

            ls1.addAll(ls2);
            return ls1;
        }
    }


}
