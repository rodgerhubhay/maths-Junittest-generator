```java
package default;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ComplexMathTest {

    @Test
    @DisplayName("Factorial: Test with zero")
    void factorial_zero() {
        assertEquals(1, ComplexMath.factorial(0));
    }

    @Test
    @DisplayName("Factorial: Test with one")
    void factorial_one() {
        assertEquals(1, ComplexMath.factorial(1));
    }

    @Test
    @DisplayName("Factorial: Test with a positive number")
    void factorial_positive() {
        assertEquals(120, ComplexMath.factorial(5));
    }

    @Test
    @DisplayName("Factorial: Test with a larger positive number")
    void factorial_largerPositive() {
        assertEquals(3628800, ComplexMath.factorial(10));
    }

    @Test
    @DisplayName("Factorial: Test with negative number - Should throw IllegalArgumentException")
    void factorial_negative() {
        assertThrows(IllegalArgumentException.class, () -> ComplexMath.factorial(-1));
    }

    @Test
    @DisplayName("Power: Test with positive base and exponent")
    void power_positiveBaseExponent() {
        assertEquals(8.0, ComplexMath.power(2.0, 3.0));
    }

    @Test
    @DisplayName("Power: Test with negative base and positive exponent")
    void power_negativeBasePositiveExponent() {
        assertEquals(-8.0, ComplexMath.power(-2.0, 3.0));
    }

    @Test
    @DisplayName("Power: Test with positive base and negative exponent")
    void power_positiveBaseNegativeExponent() {
        assertEquals(0.25, ComplexMath.power(2.0, -2.0));
    }

    @Test
    @DisplayName("Power: Test with zero base and positive exponent")
    void power_zeroBasePositiveExponent() {
        assertEquals(0.0, ComplexMath.power(0.0, 3.0));
    }

    @Test
    @DisplayName("Power: Test with zero base and negative exponent - Should return Infinity")
    void power_zeroBaseNegativeExponent() {
        assertEquals(Double.POSITIVE_INFINITY, ComplexMath.power(0.0, -2.0));
    }

    @Test
    @DisplayName("Power: Test with base 1 and any exponent")
    void power_baseOne() {
        assertEquals(1.0, ComplexMath.power(1.0, 5.0));
    }

    @Test
    @DisplayName("Power: Test with exponent 0 and any base")
    void power_exponentZero() {
        assertEquals(1.0, ComplexMath.power(5.0, 0.0));
    }

    @Test
    @DisplayName("SquareRoot: Test with positive number")
    void squareRoot_positive() {
        assertEquals(2.0, ComplexMath.squareRoot(4.0));
    }

    @Test
    @DisplayName("SquareRoot: Test with zero")
    void squareRoot_zero() {
        assertEquals(0.0, ComplexMath.squareRoot(0.0));
    }

    @Test
    @DisplayName("SquareRoot: Test with a larger positive number")
    void squareRoot_largerPositive() {
        assertEquals(10.0, ComplexMath.squareRoot(100.0));
    }

    @Test
    @DisplayName("SquareRoot: Test with negative number - Should throw IllegalArgumentException")
    void squareRoot_negative() {
        assertThrows(IllegalArgumentException.class, () -> ComplexMath.squareRoot(-1.0));
    }

    @Test
    @DisplayName("Sine: Test with 0 degrees")
    void sine_zero() {
        assertEquals(0.0, ComplexMath.sine(0.0), 1e-15);
    }

    @Test
    @DisplayName("Sine: Test with 90 degrees")
    void sine_90() {
        assertEquals(1.0, ComplexMath.sine(90.0), 1e-15);
    }

    @Test
    @DisplayName("Sine: Test with 180 degrees")
    void sine_180() {
        assertEquals(0.0, ComplexMath.sine(180.0), 1e-15);
    }

    @Test
    @DisplayName("Sine: Test with 270 degrees")
    void sine_270() {
        assertEquals(-1.0, ComplexMath.sine(270.0), 1e-15);
    }

    @Test
    @DisplayName("Sine: Test with 360 degrees")
    void sine_360() {
        assertEquals(0.0, ComplexMath.sine(360.0), 1e-15);
    }

     @Test
    @DisplayName("Sine: Test with a negative degree value")
    void sine_negativeDegrees() {
        assertEquals(-1.0, ComplexMath.sine(-90.0), 1e-15);
    }

    @Test
    @DisplayName("Cosine: Test with 0 degrees")
    void cosine_zero() {
        assertEquals(1.0, ComplexMath.cosine(0.0), 1e-15);
    }

    @Test
    @DisplayName("Cosine: Test with 90 degrees")
    void cosine_90() {
        assertEquals(0.0, ComplexMath.cosine(90.0), 1e-15);
    }

    @Test
    @DisplayName("Cosine: Test with 180 degrees")
    void cosine_180() {
        assertEquals(-1.0, ComplexMath.cosine(180.0), 1e-15);
    }

    @Test
    @DisplayName("Cosine: Test with 270 degrees")
    void cosine_270() {
        assertEquals(0.0, ComplexMath.cosine(270.0), 1e-15);
    }

    @Test
    @DisplayName("Cosine: Test with 360 degrees")
    void cosine_360() {
        assertEquals(1.0, ComplexMath.cosine(360.0), 1e-15);
    }

    @Test
    @DisplayName("Cosine: Test with a negative degree value")
    void cosine_negativeDegrees() {
        assertEquals(0.0, ComplexMath.cosine(-90.0), 1e-15);
    }


    @Test
    @DisplayName("Log10: Test with positive number")
    void log10_positive() {
        assertEquals(1.0, ComplexMath.log10(10.0), 1e-15);
    }

    @Test
    @DisplayName("Log10: Test with 1")
    void log10_one() {
        assertEquals(0.0, ComplexMath.log10(1.0), 1e-15);
    }

    @Test
    @DisplayName("Log10: Test with a smaller positive number")
    void log10_smallerPositive() {
        assertEquals(-1.0, ComplexMath.log10(0.1), 1e-15);
    }

    @Test
    @DisplayName("Log10: Test with zero - Should throw IllegalArgumentException")
    void log10_zero() {
        assertThrows(IllegalArgumentException.class, () -> ComplexMath.log10(0.0));
    }

    @Test
    @DisplayName("Log10: Test with negative number - Should throw IllegalArgumentException")
    void log10_negative() {
        assertThrows(IllegalArgumentException.class, () -> ComplexMath.log10(-1.0));
    }

    @Test
    @DisplayName("NaturalLog: Test with positive number")
    void naturalLog_positive() {
        assertEquals(1.0, ComplexMath.naturalLog(Math.E), 1e-15);
    }

    @Test
    @DisplayName("NaturalLog: Test with 1")
    void naturalLog_one() {
        assertEquals(0.0, ComplexMath.naturalLog(1.0), 1e-15);
    }

    @Test
    @DisplayName("NaturalLog: Test with a smaller positive number")
    void naturalLog_smallerPositive() {
        assertTrue(ComplexMath.naturalLog(0.1) < 0);
    }

    @Test
    @DisplayName("NaturalLog: Test with zero - Should throw IllegalArgumentException")
    void naturalLog_zero() {
        assertThrows(IllegalArgumentException.class, () -> ComplexMath.naturalLog(0.0));
    }

    @Test
    @DisplayName("NaturalLog: Test with negative number - Should throw IllegalArgumentException")
    void naturalLog_negative() {
        assertThrows(IllegalArgumentException.class, () -> ComplexMath.naturalLog(-1.0));
    }
}
```