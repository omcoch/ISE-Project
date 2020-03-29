package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Plane class
 *
 * @author Ron & Omri
 */
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#getNormal()}.
     */
    @Test
    void getNormal() {
        Plane p = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        Vector normal = new Vector(Math.sqrt(1d / 3),Math.sqrt(1d / 3),Math.sqrt(1d / 3));

        // ============ Equivalence Partitions Tests ==============
        // Test for correct normal of a plane
        assertEquals(p.getNormal(), normal, "Error: Bad normal to Plane");
    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
    @Test
    void testGetNormal() {
        Plane p = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        Point3D p0 = new Point3D(1,1,1);
        Vector normal = new Vector(Math.sqrt(1d / 3),Math.sqrt(1d / 3),Math.sqrt(1d / 3));

        // ============ Equivalence Partitions Tests ==============
        // Test for correct normal of a plane
        assertEquals(p.getNormal(p0), normal, "Error: Bad normal to Plane");
    }
}