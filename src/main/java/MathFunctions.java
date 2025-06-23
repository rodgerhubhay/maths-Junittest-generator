```java
public class MathFunctions {
    
    /**
     * Adds two integers with overflow check
     * @param a first operand
     * @param b second operand
     * @return sum of a and b
     * @throws ArithmeticException if the result overflows an int
     */
    public static int add(int a, int b) {
        try {
            return Math.addExact(a, b);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Integer overflow in addition");
        }
    }
    
    /**
     * Subtracts two integers with overflow check
     * @param a first operand
     * @param b second operand
     * @return difference between a and b
     * @throws ArithmeticException if the result overflows an int
     */
    public static int subtract(int a, int b) {
        try {
            return Math.subtractExact(a, b);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Integer overflow in subtraction");
        }
    }
    
    /**
     * Multiplies two integers with overflow check
     * @param a first operand
     * @param b second operand
     * @return product of a and b
     * @throws ArithmeticException if the result overflows an int
     */
    public static int multiply(int a, int b) {
        try {
            return Math.multiplyExact(a, b);
        } catch (ArithmeticException e) {
            throw new ArithmeticException("Integer overflow in multiplication");
        }
    }
    
    /**
     * Divides two integers with proper error handling
     * @param a dividend
     * @param b divisor
     * @return result of division as double
     * @throws ArithmeticException if dividing by zero
     * @throws ArithmeticException if division results in integer overflow (MIN_VALUE / -1)
     */
    public static double divide(int a, int b) {
        // Check for division by zero
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        
        return (double) a / b;
    }
    
    /**
     * Integer division with remainder
     * @param a dividend
     * @param b divisor
     * @return result of integer division
     * @throws ArithmeticException if dividing by zero
     */
    public static int intDivide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
    
    /**
     * Modulo operation
     * @param a dividend
     * @param b divisor
     * @return remainder of division
     * @throws ArithmeticException if dividing by zero
     */
    public static int modulo(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero in modulo operation");
        }
        return a % b;
    }
    
    /**
     * Calculates square root with error checking
     * @param value the value to calculate square root of
     * @return square root of the value
     * @throws ArithmeticException if value is negative
     */
    public static double sqrt(double value) {
        if (value < 0) {
            throw new ArithmeticException("Square root of negative number");
        }
        return Math.sqrt(value);
    }
    
    /**
     * Calculates power with error checking
     * @param base the base
     * @param exponent the exponent
     * @return result of base^exponent
     * @throws ArithmeticException if result would overflow or for 0^0
     */
    public static double power(double base, double exponent) {
        if (base == 0 && exponent == 0) {
            throw new ArithmeticException("Indeterminate form: 0^0");
        }

        double result = Math.pow(base, exponent);
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            throw new ArithmeticException("Result is undefined or too large (overflow)");
        }

        return result;
    }
    
    /**
     * Calculates factorial with error checking
     * @param n the number to calculate factorial of
     * @return factorial of n
     * @throws ArithmeticException if n is negative or result would overflow
     */
    public static long factorial(int n) {
        if (n < 0) {
            throw new ArithmeticException("Factorial of negative number");
        }
        
        if (n > 20) {
            throw new ArithmeticException("Factorial overflow for n > 20");
        }

        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
```