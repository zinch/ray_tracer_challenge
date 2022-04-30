package raytracer.core.geometry;

import raytracer.utils.MathUtils;

import java.util.Arrays;

public record Matrix(double[][] values) {
    public Matrix(double[][] values) {
        validate(values);
        var M = getRowsCount(values);
        var N = getColumnsCount(values);
        this.values = new double[N][M];
        for (int i = 0; i < N; i++) {
            this.values[i] = Arrays.copyOf(values[i], N);
        }
    }

    private void validate(double[][] values) {
        var M = getRowsCount(values);
        if (M == 0) {
            throw new IllegalArgumentException("Must provide a square matrix!");
        }
        var N = getColumnsCount(values);
        if (N != M) {
            throw new IllegalArgumentException("Must provide a square matrix!");
        }
        for (int i = 1; i < M; i++) {
            if (values[i].length != N) {
                throw new IllegalArgumentException("Must provide a square matrix!");
            }
        }
    }

    public double at(int i, int j) {
        return values[i][j];
    }

    public Matrix times(Matrix m) {
        var N = getRowsCount(values);
        if (N != getRowsCount(m.values)) {
            throw new IllegalArgumentException("Cannot multiply matrices of different dimensions!");
        }
        var result = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    result[i][j] += at(i, k) * m.at(k, j);
                }
            }
        }
        return new Matrix(result);
    }

    public Point times(Point p) {
        double x = 0;
        double y = 0;
        double z = 0;
        var M = getRowsCount(values);
        if (M != 4) {
            throw new IllegalArgumentException("Can only multiply tuples with 4x4 matrices!");
        }
        double[] tuple = {p.x, p.y, p.z, p.w};
        for (int j = 0; j < M; j++) {
            x += at(0, j) * tuple[j];
            y += at(1, j) * tuple[j];
            z += at(2, j) * tuple[j];
        }
        return new Point(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix m = (Matrix) o;
        var M = getRowsCount(values);
        var N = getColumnsCount(values);
        if (M != getRowsCount(m.values) || N != getColumnsCount(m.values)) {
            return false;
        }
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (!MathUtils.areEqual(at(i, j), m.at(i, j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        var M = getRowsCount(values);
        var N = getColumnsCount(values);

        var newLine = System.getProperty("line.separator");
        var sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (j > 0) {
                    sb.append(", ");
                }
                sb.append(at(i, j));
            }
            if (i != M - 1) {
                sb.append(newLine);
            }
        }
        return sb.toString();
    }

    private int getRowsCount(double[][] values) {
        return values.length;
    }

    private int getColumnsCount(double[][] values) {
        return values[0].length;
    }

    public Matrix transpose() {
        var M = getRowsCount(values);
        var transposed = new double[M][M];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                transposed[i][j] = at(j, i);
            }
        }
        return new Matrix(transposed);
    }
}
