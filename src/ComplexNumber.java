
public class ComplexNumber {
    private final double real;
    private final double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(
                real + other.real,
                imaginary + other.imaginary
        );
    }

    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(
                real - other.real,
                imaginary - other.imaginary
        );
    }

    public ComplexNumber multiply(ComplexNumber other) {
        return new ComplexNumber(
                real * other.real - imaginary * other.imaginary,
                real * other.imaginary + imaginary * other.real
        );
    }

    public ComplexNumber divide(ComplexNumber other) {
        double denominator =
                other.real * other.real +
                        other.imaginary * other.imaginary;

        if (denominator == 0) {
            throw new ArithmeticException("Division by zero");
        }

        return new ComplexNumber(
                (real * other.real + imaginary * other.imaginary) / denominator,
                (imaginary * other.real - real * other.imaginary) / denominator
        );
    }

    public double abs() {
        return Math.hypot(real, imaginary);
    }

    @Override
    public String toString() {
        return real + (imaginary < 0 ? " - " : " + ")
                + Math.abs(imaginary) + "i";
    }
}
