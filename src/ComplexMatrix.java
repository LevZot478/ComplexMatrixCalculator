
public class ComplexMatrix {
    private static final double EPS = 1e-10;

    private final ComplexNumber[][] data;
    private final int rows;
    private final int cols;

    public ComplexMatrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Invalid matrix size");
        }

        this.rows = rows;
        this.cols = cols;
        this.data = new ComplexNumber[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = new ComplexNumber(0, 0);
            }
        }
    }

    public void set(int row, int col, ComplexNumber value) {
        if (value == null) {
            throw new IllegalArgumentException("Null element");
        }
        data[row][col] = value;
    }

    public ComplexNumber get(int row, int col) {
        return data[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public ComplexMatrix add(ComplexMatrix other) {
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException(
                    "Matrix sizes must match"
            );
        }

        ComplexMatrix result = new ComplexMatrix(rows, cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, data[i][j].add(other.get(i, j)));
            }
        }

        return result;
    }

    public ComplexMatrix multiply(ComplexMatrix other) {
        if (cols != other.rows) {
            throw new IllegalArgumentException(
                    "Incompatible matrix sizes"
            );
        }

        ComplexMatrix result =
                new ComplexMatrix(rows, other.cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                ComplexNumber sum = new ComplexNumber(0, 0);

                for (int k = 0; k < cols; k++) {
                    sum = sum.add(
                            data[i][k].multiply(other.get(k, j))
                    );
                }

                result.set(i, j, sum);
            }
        }

        return result;
    }

    public ComplexMatrix transpose() {
        ComplexMatrix result = new ComplexMatrix(cols, rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(j, i, data[i][j]);
            }
        }

        return result;
    }

    public ComplexNumber determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException(
                    "Matrix must be square"
            );
        }

        ComplexNumber[][] temp = copyData();
        ComplexNumber det = new ComplexNumber(1, 0);
        int sign = 1;

        for (int i = 0; i < rows; i++) {
            int pivot = i;

            for (int j = i + 1; j < rows; j++) {
                if (temp[j][i].abs() > temp[pivot][i].abs()) {
                    pivot = j;
                }
            }

            if (temp[pivot][i].abs() < EPS) {
                return new ComplexNumber(0, 0);
            }

            if (pivot != i) {
                ComplexNumber[] swap = temp[i];
                temp[i] = temp[pivot];
                temp[pivot] = swap;
                sign *= -1;
            }

            ComplexNumber diagonal = temp[i][i];
            det = det.multiply(diagonal);

            for (int j = i + 1; j < rows; j++) {
                ComplexNumber factor =
                        temp[j][i].divide(diagonal);

                for (int k = i + 1; k < rows; k++) {
                    temp[j][k] = temp[j][k].subtract(
                            factor.multiply(temp[i][k])
                    );
                }

                temp[j][i] = new ComplexNumber(0, 0);
            }
        }

        if (sign == -1) {
            det = det.multiply(new ComplexNumber(-1, 0));
        }

        return det;
    }

    public ComplexMatrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException(
                    "Matrix must be square"
            );
        }

        int n = rows;
        ComplexNumber[][] a = copyData();
        ComplexNumber[][] inv = new ComplexNumber[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inv[i][j] = new ComplexNumber(
                        i == j ? 1 : 0, 0
                );
            }
        }

        for (int i = 0; i < n; i++) {
            int pivot = i;

            for (int j = i + 1; j < n; j++) {
                if (a[j][i].abs() > a[pivot][i].abs()) {
                    pivot = j;
                }
            }

            if (a[pivot][i].abs() < EPS) {
                throw new ArithmeticException(
                        "Matrix is singular"
                );
            }

            ComplexNumber[] swap = a[i];
            a[i] = a[pivot];
            a[pivot] = swap;

            swap = inv[i];
            inv[i] = inv[pivot];
            inv[pivot] = swap;

            ComplexNumber diagonal = a[i][i];

            for (int j = 0; j < n; j++) {
                a[i][j] = a[i][j].divide(diagonal);
                inv[i][j] = inv[i][j].divide(diagonal);
            }

            for (int k = 0; k < n; k++) {
                if (k == i) continue;

                ComplexNumber factor = a[k][i];

                for (int j = 0; j < n; j++) {
                    a[k][j] = a[k][j].subtract(
                            factor.multiply(a[i][j])
                    );

                    inv[k][j] = inv[k][j].subtract(
                            factor.multiply(inv[i][j])
                    );
                }
            }
        }

        ComplexMatrix result = new ComplexMatrix(n, n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.set(i, j, inv[i][j]);
            }
        }

        return result;
    }

    public ComplexMatrix divide(ComplexMatrix other) {
        return multiply(other.inverse());
    }

    private ComplexNumber[][] copyData() {
        ComplexNumber[][] copy =
                new ComplexNumber[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                copy[i][j] = data[i][j];
            }
        }

        return copy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rows; i++) {
            sb.append("[ ");

            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]).append("  ");
            }

            sb.append("]\n");
        }

        return sb.toString();
    }
}
