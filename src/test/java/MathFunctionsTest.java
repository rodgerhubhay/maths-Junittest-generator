```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathFunctionsTest {

    private MathFunctions mathFunctions;

    @BeforeEach
    void setUp() {
        mathFunctions = new MathFunctions();
    }

    @Test
    @DisplayName("Test add with positive numbers")
    void testAddPositive() {
        assertEquals(5, MathFunctions.add(2, 3));
    }

    @Test
    @DisplayName("Test add with negative numbers")
    void testAddNegative() {
        assertEquals(-5, MathFunctions.add(-2, -3));
    }

    @Test
    @DisplayName("Test add with positive and negative numbers")
    void testAddMixed() {
        assertEquals(1, MathFunctions.add(3, -2));
    }

    @Test
    @DisplayName("Test add with zero")
    void testAddZero() {
        assertEquals(5, MathFunctions.add(5, 0));
        assertEquals(5, MathFunctions.add(0, 5));
        assertEquals(0, MathFunctions.add(0, 0));
    }

    @Test
    @DisplayName("Test add with maximum integer value")
    void testAddMaxInt() {
        assertEquals(Integer.MAX_VALUE, MathFunctions.add(Integer.MAX_VALUE, 0));
    }

    @Test
    @DisplayName("Test add with minimum integer value")
    void testAddMinInt() {
        assertEquals(Integer.MIN_VALUE, MathFunctions.add(Integer.MIN_VALUE, 0));
    }

    @Test
    @DisplayName("Test add with overflow")
    void testAddOverflow() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.add(Integer.MAX_VALUE, 1));
    }

    @Test
    @DisplayName("Test add with underflow")
    void testAddUnderflow() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.add(Integer.MIN_VALUE, -1));
    }

    @Test
    @DisplayName("Test subtract with positive numbers")
    void testSubtractPositive() {
        assertEquals(1, MathFunctions.subtract(3, 2));
    }

    @Test
    @DisplayName("Test subtract with negative numbers")
    void testSubtractNegative() {
        assertEquals(-1, MathFunctions.subtract(-3, -2));
    }

    @Test
    @DisplayName("Test subtract with positive and negative numbers")
    void testSubtractMixed() {
        assertEquals(5, MathFunctions.subtract(3, -2));
    }

    @Test
    @DisplayName("Test subtract with zero")
    void testSubtractZero() {
        assertEquals(5, MathFunctions.subtract(5, 0));
        assertEquals(-5, MathFunctions.subtract(0, 5));
        assertEquals(0, MathFunctions.subtract(0, 0));
    }

    @Test
    @DisplayName("Test subtract with maximum integer value")
    void testSubtractMaxInt() {
        assertEquals(Integer.MAX_VALUE, MathFunctions.subtract(Integer.MAX_VALUE, 0));
    }

    @Test
    @DisplayName("Test subtract with minimum integer value")
    void testSubtractMinInt() {
        assertEquals(Integer.MIN_VALUE, MathFunctions.subtract(Integer.MIN_VALUE, 0));
    }

    @Test
    @DisplayName("Test subtract with overflow")
    void testSubtractOverflow() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.subtract(Integer.MIN_VALUE, 1));
    }

    @Test
    @DisplayName("Test subtract with underflow")
    void testSubtractUnderflow() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.subtract(Integer.MAX_VALUE, -1));
    }

    @Test
    @DisplayName("Test multiply with positive numbers")
    void testMultiplyPositive() {
        assertEquals(6, MathFunctions.multiply(2, 3));
    }

    @Test
    @DisplayName("Test multiply with negative numbers")
    void testMultiplyNegative() {
        assertEquals(6, MathFunctions.multiply(-2, -3));
    }

    @Test
    @DisplayName("Test multiply with positive and negative numbers")
    void testMultiplyMixed() {
        assertEquals(-6, MathFunctions.multiply(2, -3));
    }

    @Test
    @DisplayName("Test multiply with zero")
    void testMultiplyZero() {
        assertEquals(0, MathFunctions.multiply(5, 0));
        assertEquals(0, MathFunctions.multiply(0, 5));
        assertEquals(0, MathFunctions.multiply(0, 0));
    }

    @Test
    @DisplayName("Test multiply with maximum integer value")
    void testMultiplyMaxInt() {
        assertEquals(Integer.MAX_VALUE, MathFunctions.multiply(Integer.MAX_VALUE, 1));
    }

    @Test
    @DisplayName("Test multiply with minimum integer value")
    void testMultiplyMinInt() {
        assertEquals(Integer.MIN_VALUE, MathFunctions.multiply(Integer.MIN_VALUE, 1));
    }

    @Test
    @DisplayName("Test multiply with overflow")
    void testMultiplyOverflow() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.multiply(Integer.MAX_VALUE, 2));
    }

    @Test
    @DisplayName("Test multiply with underflow")
    void testMultiplyUnderflow() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.multiply(Integer.MIN_VALUE, 2));
    }

    @Test
    @DisplayName("Test divide with positive numbers")
    void testDividePositive() {
        assertEquals(2.0, MathFunctions.divide(6, 3));
    }

    @Test
    @DisplayName("Test divide with negative numbers")
    void testDivideNegative() {
        assertEquals(2.0, MathFunctions.divide(-6, -3));
    }

    @Test
    @DisplayName("Test divide with positive and negative numbers")
    void testDivideMixed() {
        assertEquals(-2.0, MathFunctions.divide(6, -3));
    }

    @Test
    @DisplayName("Test divide with one")
    void testDivideOne() {
        assertEquals(5.0, MathFunctions.divide(5, 1));
    }

    @Test
    @DisplayName("Test divide with zero")
    void testDivideZero() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.divide(5, 0));
    }

    @Test
    @DisplayName("Test divide with maximum integer value")
    void testDivideMaxInt() {
        assertEquals(Integer.MAX_VALUE, MathFunctions.divide(Integer.MAX_VALUE, 1));
    }

    @Test
    @DisplayName("Test divide with minimum integer value")
    void testDivideMinInt() {
        assertEquals(Integer.MIN_VALUE, MathFunctions.divide(Integer.MIN_VALUE, 1));
    }

    @Test
    @DisplayName("Test divide with integer overflow")
    void testDivideIntegerOverflow() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.divide(Integer.MIN_VALUE, -1));
    }

    @Test
    @DisplayName("Test intDivide with positive numbers")
    void testIntDividePositive() {
        assertEquals(2, MathFunctions.intDivide(6, 3));
    }

    @Test
    @DisplayName("Test intDivide with negative numbers")
    void testIntDivideNegative() {
        assertEquals(2, MathFunctions.intDivide(-6, -3));
    }

    @Test
    @DisplayName("Test intDivide with positive and negative numbers")
    void testIntDivideMixed() {
        assertEquals(-2, MathFunctions.intDivide(6, -3));
    }

    @Test
    @DisplayName("Test intDivide with one")
    void testIntDivideOne() {
        assertEquals(5, MathFunctions.intDivide(5, 1));
    }

    @Test
    @DisplayName("Test intDivide with zero")
    void testIntDivideZero() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.intDivide(5, 0));
    }

    @Test
    @DisplayName("Test intDivide with maximum integer value")
    void testIntDivideMaxInt() {
        assertEquals(Integer.MAX_VALUE, MathFunctions.intDivide(Integer.MAX_VALUE, 1));
    }

    @Test
    @DisplayName("Test intDivide with minimum integer value")
    void testIntDivideMinInt() {
        assertEquals(Integer.MIN_VALUE, MathFunctions.intDivide(Integer.MIN_VALUE, 1));
    }

    @Test
    @DisplayName("Test modulo with positive numbers")
    void testModuloPositive() {
        assertEquals(0, MathFunctions.modulo(6, 3));
    }

    @Test
    @DisplayName("Test modulo with negative numbers")
    void testModuloNegative() {
        assertEquals(0, MathFunctions.modulo(-6, -3));
    }

    @Test
    @DisplayName("Test modulo with positive and negative numbers")
    void testModuloMixed() {
        assertEquals(0, MathFunctions.modulo(6, -3));
    }

    @Test
    @DisplayName("Test modulo with one")
    void testModuloOne() {
        assertEquals(0, MathFunctions.modulo(5, 1));
    }

    @Test
    @DisplayName("Test modulo with zero")
    void testModuloZero() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.modulo(5, 0));
    }

    @Test
    @DisplayName("Test modulo with maximum integer value")
    void testModuloMaxInt() {
        assertEquals(0, MathFunctions.modulo(Integer.MAX_VALUE, 1));
    }

    @Test
    @DisplayName("Test modulo with minimum integer value")
    void testModuloMinInt() {
        assertEquals(0, MathFunctions.modulo(Integer.MIN_VALUE, 1));
    }

    @Test
    @DisplayName("Test sqrt with positive number")
    void testSqrtPositive() {
        assertEquals(2.0, MathFunctions.sqrt(4.0));
    }

    @Test
    @DisplayName("Test sqrt with zero")
    void testSqrtZero() {
        assertEquals(0.0, MathFunctions.sqrt(0.0));
    }

    @Test
    @DisplayName("Test sqrt with negative number")
    void testSqrtNegative() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.sqrt(-1.0));
    }

    @Test
    @DisplayName("Test power with positive base and exponent")
    void testPowerPositive() {
        assertEquals(8.0, MathFunctions.power(2.0, 3.0));
    }

    @Test
    @DisplayName("Test power with negative base and positive exponent")
    void testPowerNegativeBasePositiveExponent() {
        assertEquals(-8.0, MathFunctions.power(-2.0, 3.0));
    }

    @Test
    @DisplayName("Test power with positive base and negative exponent")
    void testPowerPositiveBaseNegativeExponent() {
        assertEquals(0.125, MathFunctions.power(2.0, -3.0));
    }

    @Test
    @DisplayName("Test power with zero base and positive exponent")
    void testPowerZeroBasePositiveExponent() {
        assertEquals(0.0, MathFunctions.power(0.0, 3.0));
    }

    @Test
    @DisplayName("Test power with zero base and negative exponent")
    void testPowerZeroBaseNegativeExponent() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.power(0.0, -3.0));
    }

    @Test
    @DisplayName("Test power with zero base and zero exponent")
    void testPowerZeroBaseZeroExponent() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.power(0.0, 0.0));
    }

    @Test
    @DisplayName("Test power with overflow")
    void testPowerOverflow() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.power(2.0, 1024.0));
    }

    @Test
    @DisplayName("Test power with underflow")
    void testPowerUnderflow() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.power(2.0, -1024.0));
    }

    @Test
    @DisplayName("Test factorial with positive number")
    void testFactorialPositive() {
        assertEquals(120, MathFunctions.factorial(5));
    }

    @Test
    @DisplayName("Test factorial with zero")
    void testFactorialZero() {
        assertEquals(1, MathFunctions.factorial(0));
    }

    @Test
    @DisplayName("Test factorial with one")
    void testFactorialOne() {
        assertEquals(1, MathFunctions.factorial(1));
    }

    @Test
    @DisplayName("Test factorial with negative number")
    void testFactorialNegative() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.factorial(-1));
    }

    @Test
    @DisplayName("Test factorial with overflow")
    void testFactorialOverflow() {
        assertThrows(ArithmeticException.class, () -> MathFunctions.factorial(21));
    }
}
```