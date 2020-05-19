package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
        Polygon tri = new Triangle(new Point3D(-3,0,0), new Point3D(0,0,2), new Point3D(-6, 0, 0));

        // TC00: ray intersects with the plane
        Ray r0 = new Ray(new Point3D(-1,-2,0), new Vector(new Point3D(0,1, 0)));
        assertNull("wrong number of intersections", tri.findIntersections(r0));

        // TC01: ray intersects inside the triangle
        Ray r1 = new Ray(new Point3D(-1,-2,0), new Vector(new Point3D(-1,2, 1)));
        List<Intersectable.GeoPoint> result = tri.findIntersections(r1);
        assertEquals("wrong intersection point",new Point3D(-2,0,1),result.get(0));

        // TC02: ray intersects outside against edge
        Ray r2 = new Ray(new Point3D(-1,-2,0), new Vector(new Point3D(0,2,1)));
        assertNull("wrong intersection point against edge",tri.findIntersections(r2));

        // TC03: ray intersects outside against vertex
        Ray r3 = new Ray(new Point3D(-1,-2,0), new Vector(new Point3D(1.8,1.98,2.5)));
        assertNull("wrong intersection point against vertex",tri.findIntersections(r3));


        // =============== Boundary Values Tests ==================

        //**** Group:the ray begins "before" the plane
        // TC04: ray intersects on edge
        Ray r4 = new Ray(new Point3D(-1,-2,0), new Vector(new Point3D(-3,2,0)));
        assertNull("wrong intersection point on edge",tri.findIntersections(r4));

        // TC05: ray intersects in vertex
        Ray r5 = new Ray(new Point3D(-1,-2,0), new Vector(new Point3D(-5,2,0)));
        assertNull("wrong intersection point in vertex",tri.findIntersections(r5));

        // TC06: ray intersects On edge's continuation
        Ray r6 = new Ray(new Point3D(-1,-2,0), new Vector(new Point3D(-1,2,0)));
        assertNull("wrong intersection point On edge's continuation",tri.findIntersections(r5));

    }

}