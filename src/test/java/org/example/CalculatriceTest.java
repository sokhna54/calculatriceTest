package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatriceTest {
    Calculatrice calc = new Calculatrice();
    @Test
    void testAddition() {
        assertEquals(10, calc.addition(7, 3));
    }

    @Test
    void testSoustraction() {
        assertEquals(4, calc.soustraction(9, 5));
    }

    @Test
    void testMultiplication() {
        assertEquals(20, calc.multiplication(4, 5));
    }

    @Test
    void testDivision() {
        assertEquals(5, calc.division(10, 2));
    }

    @Test
    void testDivisionParZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            calc.division(10, 0);
        });
    }
}