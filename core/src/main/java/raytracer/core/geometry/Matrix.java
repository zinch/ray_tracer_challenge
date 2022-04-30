package raytracer.core.geometry;

import java.util.Arrays;

public record Matrix(double[][] values) {
    public Matrix(double[][] values) {
        var M = values.length;
        var N = values[0].length;
        if (N != M) {
            throw new IllegalArgumentException("Must provide a square matrix!");
        }
        this.values = new double[N][M];
        for (int i = 0; i < N; i++) {
            this.values[i] = Arrays.copyOf(values[i], N);
        }
    }

    public double at(int i, int j) {
        return values[i][j];
    }
}
