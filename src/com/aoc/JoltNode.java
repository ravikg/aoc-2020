package com.aoc;

import java.util.ArrayList;
import java.util.List;

public class JoltNode {

    public long jolt;
    public List<JoltNode> joltNodes;

    public JoltNode(long jolt) {
        this.jolt = jolt;
        joltNodes = new ArrayList<>();
    }

}
