package com.aoc;

public class PasswordPolicy {

    int min;
    int max;
    char c;
    String password;
    String txt;

    //parse string of format: min-max c: password into an object
    //1-3 a: abcde
    public PasswordPolicy(String txt) {
        this.txt = txt;
        String[] strs = txt.split("-| |: ");
        this.min = Integer.parseInt(strs[0]);
        this.max = Integer.parseInt(strs[1]);
        this.c = strs[2].charAt(0);
        this.password = strs[3];
    }

    //part 1
    public boolean isValid1() {
        long count = password.chars().filter(ch -> ch == c).count();
        return (count >= min) && (count <= max);
    }

    //part 2
    public boolean isValid2() {
        return (password.charAt(min - 1) == c) != (password.charAt(max - 1) == c);
    }

}
