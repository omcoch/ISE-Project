package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Tube class
 *
 * @author Ron & Omri
 */
class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // Test for correct normal of a tube
        Tube t = new Tube(2, new Ray(new Point3D(0, 0, 1), new Vector(0, 0, -1)));
        assertEquals(new Vector(1, 0, 0), t.getNormal(new Point3D(2, 0, 0)), "The normal was incorrect");
    }

    /**
     * Test method for {@link geometries.Tube#Tube(double, Ray)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // Test for 0 or less radius size
        try {
            Tube s = new Tube(0, new Ray(Point3D.ZERO, new Vector(1, 2, 3)));
            fail("Constructed a Tube with radius 0");
        } catch (IllegalArgumentException e) { }

        // =============== Boundary Values Tests ==================
        // Test for epsilon radius size
        try {
            Tube s = new Tube(0.0000000000001, new Ray(Point3D.ZERO, new Vector(1, 2, 3)));
            fail("Constructed a Tube with radius 0");
        } catch (IllegalArgumentException e) { }
    }
}