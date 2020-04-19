package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 *
 * @author Ron & Omri
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Triangle tr = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        Vector the_normal = tr.getNormal(new Point3D(0, 0, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        Vector expected = new Vector(sqrt3, sqrt3, sqrt3);

        // Test for correct normal of triangle
        assertTrue(expected.equals(the_normal) || expected.equals(the_normal.scale(-1)),
                "Bad normal to triangle");
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============

    }

}