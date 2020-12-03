package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import com.aoc.PasswordPolicy;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/2

public class Day2 {

    public static void main(String[] args) {
        String fileName = "input2.txt";
        Stream<String> input = readFileByLine(fileName);

        List<PasswordPolicy> passwordPolicyList = input.map(PasswordPolicy::new).collect(Collectors.toList());
        System.out.println(passwordPolicyList.stream().filter(PasswordPolicy::isValid1).count());
        System.out.println(passwordPolicyList.stream().filter(PasswordPolicy::isValid2).count());
    }

}
