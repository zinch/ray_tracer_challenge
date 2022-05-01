package raytracer.core.geometry;

import raytracer.utils.MathUtils;

import java.util.Arrays;

public record Matrix(double[][] values) {
    public static Matrix translation(int x, int y, int z) {
        return new Matrix(new double[][]{
                {1, 0, 0, x},
                {0, 1, 0, y},
                {0, 0, 1, z},
                {0, 0, 0, 1}
        });
    }

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

    public double cofactor(int i, int j) {
        var sign = (i + j) % 2 == 0 ? 1 : -1;
        return sign * minor(i, j);
    }

    public double minor(int i, int j) {
        return submatrix(i, j).determinant();
    }

    public Matrix submatrix(int row, int col) {
        var M = getRowsCount(values);
        var submatrix = new double[M - 1][M - 1];
        for (int i = 0, k = 0; i < M; i++) {
            if (i == row) {
                continue;
            }
            for (int j = 0, v = 0; j < M; j++) {
                if (j == col) {
                    continue;
                }
                submatrix[k][v] = at(i, j);
                v++;
            }
            k++;
        }
        return new Matrix(submatrix);
    }

    public double determinant() {
        var M = getRowsCount(values);
        if (M == 2) {
            return at(0, 0) * at(1, 1) - at(0, 1) * at(1, 0);
        }

        var det = 0.0;
        for (int j = 0; j < M; j++) {
            det += at(0, j) * cofactor(0, j);
        }
        return det;
    }

    public boolean isInvertible() {
        return !MathUtils.areEqual(determinant(), 0);
    }

    public Matrix inverse() {
        if (!isInvertible()) {
            throw new IllegalStateException("Matrix is not invertible!");
        }
        var M = getRowsCount(values);
        var det = determinant();
        var cofactorMatrixValues = new double[M][M];

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                cofactorMatrixValues[j][i] = cofactor(i, j) / det;
            }
        }
        return new Matrix(cofactorMatrixValues);
    }
}
