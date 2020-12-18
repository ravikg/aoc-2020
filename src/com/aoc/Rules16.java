package com.aoc;

import java.util.ArrayList;
import java.util.List;

public class Rules16 {
    public String field;
    public List<Integer> mins;
    public List<Integer> maxs;

    public Rules16(String line) {
        String[] s = line.split(": ");
        field = s[0];
        mins = new ArrayList<>();
        maxs = new ArrayList<>();
        String[] vals = s[1].split("-| or ");
        mins.add(Integer.valueOf(vals[0]));
        mins.add(Integer.valueOf(vals[2]));
        maxs.add(Integer.valueOf(vals[1]));
        maxs.add(Integer.valueOf(vals[3]));
    }

    public boolean validField(String field) {
        int val = Integer.parseInt(field);
        for (int j = 0; j < maxs.size(); j++) {
            if (val >= mins.get(j) && val <= maxs.get(j)) {
                return true;
            }
        }
        return false;
    }



}
