package com.aoc;

public class Cube {
    public int x, y, z, w;

    public Cube(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Cube cube = (Cube) o;

        if (x != cube.x) {
            return false;
        }
        if (y != cube.y) {
            return false;
        }
        if (z != cube.z) {
            return false;
        }
        return w == cube.w;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        result = 31 * result + w;
        return result;
    }
}
