
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== COMPLEX MATRIX CALCULATOR ===");
            System.out.println("1. Add matrices");
            System.out.println("2. Multiply matrices");
            System.out.println("3. Divide matrices");
            System.out.println("4. Transpose matrix");
            System.out.println("5. Calculate determinant");
            System.out.println("0. Exit");
            System.out.print("Choose operation: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                if (choice == 0) {
                    System.out.println("Goodbye!");
                    break;
                }

                if (choice < 1 || choice > 5) {
                    System.out.println("Unknown operation.");
                    continue;
                }

                System.out.println("\nEnter matrix A:");
                ComplexMatrix a = readMatrix();

                switch (choice) {
                    case 1:
                        System.out.println("\nEnter matrix B:");
                        ComplexMatrix b = readMatrix();
                        System.out.println("\nA + B:");
                        System.out.println(a.add(b));
                        break;

                    case 2:
                        System.out.println("\nEnter matrix B:");
                        b = readMatrix();
                        System.out.println("\nA * B:");
                        System.out.println(a.multiply(b));
                        break;

                    case 3:
                        System.out.println("\nEnter matrix B:");
                        b = readMatrix();
                        System.out.println("\nA / B:");
                        System.out.println(a.divide(b));
                        break;

                    case 4:
                        System.out.println("\nTranspose of A:");
                        System.out.println(a.transpose());
                        break;

                    case 5:
                        System.out.println("\nDeterminant of A:");
                        System.out.println(a.determinant());
                        break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static ComplexMatrix readMatrix() {
        System.out.print("Rows: ");
        int rows = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Columns: ");
        int cols = Integer.parseInt(scanner.nextLine().trim());

        ComplexMatrix matrix = new ComplexMatrix(rows, cols);

        System.out.println("Enter each element as two numbers: real imaginary");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Element [" + (i + 1) + "][" + (j + 1) + "]: ");

                String[] parts = scanner.nextLine().trim().split("\\s+");

                if (parts.length != 2) {
                    throw new IllegalArgumentException(
                            "Enter exactly two numbers per element"
                    );
                }

                double real = Double.parseDouble(parts[0]);
                double imaginary = Double.parseDouble(parts[1]);

                matrix.set(i, j, new ComplexNumber(real, imaginary));
            }
        }

        return matrix;
    }
}
