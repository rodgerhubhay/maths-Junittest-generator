```java
public class ComplexMath {

    // Factorial (recursive)
    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Negative numbers not allowed");
        if (n == 0 || n == 1) return 1;
        return n * factorial(n - 1);
    }

    // Power
    public static double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    // Square root
    public static double squareRoot(double number) {
        if (number < 0) throw new IllegalArgumentException("Negative number has no real square root");
        return Math.sqrt(number);
    }

    // Sine (in degrees)
    public static double sine(double degrees) {
        return Math.sin(Math.toRadians(degrees));
    }

    // Cosine (in degrees)
    public static double cosine(double degrees) {
        return Math.cos(Math.toRadians(degrees));
    }

    // Logarithm (base 10)
    public static double log10(double number) {
        if (number <= 0) throw new IllegalArgumentException("Logarithm undefined for zero or negative values");
        return Math.log10(number);
    }

    // Natural logarithm (base e)
    public static double naturalLog(double number) {
        if (number <= 0) throw new IllegalArgumentException("Logarithm undefined for zero or negative values");
        return Math.log(number);
    }
```
