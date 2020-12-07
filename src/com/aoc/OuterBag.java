package com.aoc;

import java.util.HashMap;
import java.util.Map;

public class OuterBag {

    String colour;

    Map<String, Integer> innerBagCountMap;

    public OuterBag(String colour) {
        this.colour = colour;
        innerBagCountMap = new HashMap<>();
    }

    public void addBag(String bag, int count) {
        innerBagCountMap.put(bag, count);
    }

    public String getColour() {
        return colour;
    }

    public Map<String, Integer> getInnerBagCountMap() {
        return innerBagCountMap;
    }

    @Override
    public String toString() {
        return "OuterBag{" +
                "colour='" + colour + '\'' +
                ", innerBagCountMap=" + innerBagCountMap +
                '}';
    }
}
