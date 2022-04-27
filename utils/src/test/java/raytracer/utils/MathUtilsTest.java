package raytracer.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {
    @Test
    void doubles_with_difference_within_epsilon_are_equal() {
        double x = 1.23;
        double y = x - MathUtils.EPSILON / 2;
        assertNotEquals(x, y);
        assertNotEquals(0, Double.compare(x, y));
        assertTrue(MathUtils.areEqual(x, y));
    }

    @Test
    void doubles_with_difference_greater_than_epsilon_are_not_equal() {
        double x = 1.23;
        double y = x + 2 + MathUtils.EPSILON;
        assertNotEquals(x, y);
        assertNotEquals(0, Double.compare(x, y));
        assertFalse(MathUtils.areEqual(x, y));
    }
}