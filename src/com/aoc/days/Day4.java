package com.aoc.days;

import static com.aoc.Utils.readFileByLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2020/day/4

public class Day4 {

    public static void main(String[] args) {
        String fileName = "input4.txt";
        Stream<String> input = readFileByLine(fileName);

        List<String> lines = input.collect(Collectors.toList());
        List<String> passports = new ArrayList<>();
        String passport = "";
        for (String line : lines) {
            String p = line.trim();
            if (p.equals("")) {
                passports.add(passport.trim());
                passport = "";
            } else {
                passport = passport + " " + p;
            }
        }
        // issue was end of file missing empty lines handling
        // adding last line from file
        passports.add(passport.trim());

        Set<String> requiredFields = new HashSet<>();
        requiredFields.add("byr");
        requiredFields.add("iyr");
        requiredFields.add("eyr");
        requiredFields.add("hgt");
        requiredFields.add("hcl");
        requiredFields.add("ecl");
        requiredFields.add("pid");

        //part 1
        System.out.println(passports.stream().filter(p -> Day4.isValidPart1(p, requiredFields)).count());

        //part 2
        System.out.println(passports.stream().filter(p -> Day4.isValidPart1(p, requiredFields))
                .filter(Day4::isValidPart2).count());
    }

    private static boolean isValidPart1(String passport, Set<String> requiredFields) {
        String[] fields = passport.split(":| ");
        Set<String> f = Arrays.stream(fields).filter(requiredFields::contains).collect(Collectors.toSet());
        return f.size() == 7;
    }

    private static boolean isValidPart2(String passport) {
        String[] p = passport.split(":| ");
        for (int i = 0; i < p.length; i = i + 2) {
            String key = p[i];
            String value = p[i + 1];
            if (key.equals("byr")) {
                int year = Integer.parseInt(value);
                if (value.length() != 4 || year < 1920 || year > 2002) {
                    return false;
                }
            } else if (key.equals("iyr")) {
                int year = Integer.parseInt(value);
                if (value.length() != 4 || year < 2010 || year > 2020) {
                    return false;
                }
            } else if (key.equals("eyr")) {
                int year = Integer.parseInt(value);
                if (value.length() != 4 || year < 2020 || year > 2030) {
                    return false;
                }
            } else if (key.equals("hgt")) {
                String val = value.substring(0, value.length() - 2);
                if (value.endsWith("cm")) {
                    int len = Integer.parseInt(val);
                    if (len < 150 || len > 193) {
                        return false;
                    }
                } else if (value.endsWith("in")) {
                    int len = Integer.parseInt(val);
                    if (len < 59 || len > 76) {
                        return false;
                    }
                } else {
                    return false;
                }
            } else if (key.equals("hcl")) {
                if (!isValidHex(value)) {
                    return false;
                }
            } else if (key.equals("ecl")) {
                if (!(value.equals("amb") || value.equals("blu") || value.equals("brn")
                        || value.equals("gry") || value.equals("grn") || value.equals("hzl")
                        || value.equals("oth"))) {
                    return false;
                }
            } else if (key.equals("pid")) {
                if (!isValidPid(value)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static final String HEX_PATTERN = "^#([a-f0-9]{6})$";

    private static final Pattern hexPattern = Pattern.compile(HEX_PATTERN);

    public static boolean isValidHex(final String colorCode) {
        Matcher matcher = hexPattern.matcher(colorCode);
        return matcher.matches();
    }

    private static final String PID_PATTERN = "([0-9]{9})$";

    private static final Pattern pidPattern = Pattern.compile(PID_PATTERN);

    public static boolean isValidPid(final String pid) {
        Matcher matcher = pidPattern.matcher(pid);
        return matcher.matches();
    }

}
