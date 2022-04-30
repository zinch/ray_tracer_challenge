package raytracer.core.geometry;

import java.util.Arrays;

public final class Matrix {
    private final double[][] values;

    public Matrix(double[][] values) {
        var M = values.length;
        var N = values[0].length;
        this.values = new double[N][M];
        for (int i = 0; i < N; i++) {
            this.values[i] = Arrays.copyOf(values[i], N);
        }
    }

    public double at(int i, int j) {
        return values[i][j];
    }
}
