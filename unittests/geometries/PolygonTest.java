
package geometries;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import primitives.*;

import java.util.List;

/**
 * Testing Polygons
 *
 * @author Dan
 */
public class PolygonTest {

    /**
     * Test method for
     * {@link geometries.Polygon#Polygon(primitives.Point3D...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (IllegalArgumentException e) {
        }

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (IllegalArgumentException e) {
        }

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (IllegalArgumentException e) {
        }

        // =============== Boundary Values Tests ==================

        // TC10: Vertix on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertex on a side");
        } catch (IllegalArgumentException e) {
        }

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertices on a side");
        } catch (IllegalArgumentException e) {
        }

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertices on a side");
        } catch (IllegalArgumentException e) {
        }

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                new Point3D(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to triangle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }

    @Test
    public void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        Polygon poly = new Polygon(new Point3D(2, 3, 0), new Point3D(-3, 0, 0), new Point3D(0, -3, 0), new Point3D(4, 0, 0));

        // TC00: ray intersects with the plane
        Ray r0 = new Ray(new Point3D(2, 0, 0), new Vector(new Point3D(-2, -2, 0)));
        assertNull("wrong number of intersections", poly.findIntersections(r0));

        // TC01: ray intersects inside the polygon
        Ray r1 = new Ray(new Point3D(3, 2, -1), new Vector(new Point3D(-4, -2, 1)));
        List<Intersectable.GeoPoint> result = poly.findIntersections(r1);
        assertEquals("wrong intersection point", new Point3D(-1, 0, 0), result.get(0));

        // TC02: ray intersects outside against edge
        Ray r2 = new Ray(new Point3D(-5, -1, 6), new Vector(new Point3D(1, -1, -6)));
        assertNull("wrong intersection point against edge", poly.findIntersections(r2));

        // TC03: ray intersects outside against vertex
        Ray r3 = new Ray(new Point3D(-5, -1, 4), new Vector(new Point3D(0, 1, -4)));
        assertNull("wrong intersection point against vertex", poly.findIntersections(r3));


        // =============== Boundary Values Tests ==================

        //**** Group:the ray begins "before" the plane
        // TC04: ray intersects on edge
        Ray r4 = new Ray(new Point3D(-1, 2, 4), new Vector(new Point3D(0, 0.8, 4)));
        assertNull("wrong intersection point on edge", poly.findIntersections(r4));

        // TC05: ray intersects in vertex
        Ray r5 = new Ray(new Point3D(-5, -1, 4), new Vector(new Point3D(2, 1, -4)));
        assertNull("wrong intersection point in vertex", poly.findIntersections(r5));

        // TC06: ray intersects On edge's continuation
        Ray r6 = new Ray(new Point3D(-5, -1, 4), new Vector(new Point3D(1.5, 1.5, -4)));
        assertNull("wrong intersection point On edge's continuation", poly.findIntersections(r5));
    }
}
