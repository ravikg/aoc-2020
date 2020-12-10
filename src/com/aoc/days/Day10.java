package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import com.aoc.JoltNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/10

public class Day10 {

    public static void main(String[] args) {
        String fileName = "input10.txt";
        Stream<String> input = readFileByLine(fileName);

        List<JoltNode> joltNodes = new ArrayList<>();

        AtomicInteger diff1 = new AtomicInteger();
        AtomicInteger diff2 = new AtomicInteger();
        AtomicInteger diff3 = new AtomicInteger();
        AtomicLong pJolt = new AtomicLong();

        input.map(Long::parseLong).sorted().forEach(jolt -> {
            int diff = (int) (jolt - pJolt.get());
            if (diff < 4) {
                switch (diff) {
                    case 1:
                        diff1.getAndIncrement();
                        break;
                    case 2:
                        diff2.getAndIncrement();
                        break;
                    case 3:
                        diff3.getAndIncrement();
                        break;
                }
                pJolt.set(jolt);
                JoltNode joltNode = new JoltNode(jolt);
                joltNodes.add(joltNode);
            }
        });

        //part 1 : 1856
        System.out.println(diff1.get() * (diff3.get() + 1));

        int size = joltNodes.size();
        for (int i = 0; i < size; i++) {
            JoltNode iNode = joltNodes.get(i);
            for (int j = i + 1; j < size && j < i + 4; j++) {
                JoltNode jNode = joltNodes.get(j);
                if ((jNode.jolt - iNode.jolt) < 4) {
                    iNode.joltNodes.add(jNode);
                }
            }
        }

        Map<Long, Long> jc = new HashMap<>();

        long d1 = 0L;
        for (int i = 0; i < 3; i++) {
            JoltNode joltNode = joltNodes.get(i);
            if (joltNode.jolt < 4) {
                d1 = d1 + tree(joltNode, jc);
            } else {
                break;
            }
        }

        //part 2: 2314037239808
        System.out.println(d1);
    }

    private static long tree(JoltNode root, Map<Long, Long> jc) {
        if (root.joltNodes.isEmpty()) {
            return 1;
        }
        long count = 0;
        if (jc.containsKey(root.jolt)) {
            return jc.get(root.jolt);
        }
        for (JoltNode joltNode : root.joltNodes) {
            count = count + tree(joltNode, jc);
        }
        jc.put(root.jolt, count);
        return count;
    }

}
