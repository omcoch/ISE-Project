package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 *
 * @author Ron & Omri
 */
class VectorTest {
    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test for orthogonal vectors
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");

        // Tests for correct value
        assertEquals(v1.dotProduct(v2), -28, 0.00001, "ERROR: dotProduct() wrong value");

    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void crossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // Test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {
        }
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void lengthSquared() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // Test for correct value
        assertEquals(v1.lengthSquared(), 14, "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void length() {
        Vector v1 = new Vector(0, 3, 4);

        // ============ Equivalence Partitions Tests ==============
        // Test for correct value
        assertEquals(v1.length(), 5, "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void add() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 3, 4);
        Vector result = new Vector(3, 5, 7);

        // ============ Equivalence Partitions Tests ==============
        // Test for correct value
        assertEquals(v1.add(v2), result, "ERROR: add() wrong value");

        // =============== Boundary Values Tests ==================
        // Test zero vector from add of two vectors
        Vector v3 = new Vector(-1,-2,-3);
        try {
            v1.add(v3);
            fail("add() for opposite vectors does not throw an exception of zero vector");
        } catch (Exception e) {
        }
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    void subtract() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 3, 4);
        Vector result = new Vector(-1, -1, -1);

        // ============ Equivalence Partitions Tests ==============
        // Test for correct value
        assertEquals(v1.subtract(v2), result, "ERROR: subtract() wrong value");

        // =============== Boundary Values Tests ==================
        // Test zero vector from subtract of two vectors
        try {
            v1.subtract(v1);
            fail("subtract() for the same vector does not throw an exception of zero vector");
        } catch (Exception e) {
        }
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void scale() {
        Vector v1 = new Vector(1,2,3);
        Vector result = new Vector(2,4,6);
        double scale = 2;

        // ============ Equivalence Partitions Tests ==============
        // Test for correct value
        assertEquals(v1.scale(scale), result, "ERROR: scale() wrong value");

        // =============== Boundary Values Tests ==================
        // Test zero vector from scale of vector
        try {
            v1.scale(0);
            fail("scale(0) of the vector does not throw an exception of zero vector");
        } catch (Exception e) {
        }

        //Test for scale by 1
        assertEquals(v1.scale(1), v1, "ERROR: scale(1) wrong value");
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void normalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vNormalize = v.normalize();

        // ============ Equivalence Partitions Tests ==============
        // Test for correct value
        assertTrue(isZero(vNormalize.length() - 1),"ERROR: normalize() result is not a unit vector");

        // =============== Boundary Values Tests ==================
        // Test modification of the current vector
        assertTrue(v != vNormalize,"ERROR: normalize() function creates a new vector");
    }

    /**
     * Test method for {@link primitives.Vector#normalized()}.
     */
    @Test
    void normalized() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalized();

        // ============ Equivalence Partitions Tests ==============
        // Test for correct value
        assertTrue(isZero(u.length() - 1),"ERROR: normalized() result is not a unit vector");

        // =============== Boundary Values Tests ==================
        // Test creation of a new vector
        assertTrue (u == v, "ERROR: normalizated() function does not create a new vector");
    }
}