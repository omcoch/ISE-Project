package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;
import java.io.PipedInputStream;

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
        Vector normal = new Vector(Math.sqrt(1d / 3), Math.sqrt(1d / 3), Math.sqrt(1d / 3));

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
        Point3D p0 = new Point3D(1, 1, 1);
        Vector normal = new Vector(Math.sqrt(1d / 3), Math.sqrt(1d / 3), Math.sqrt(1d / 3));

        // ============ Equivalence Partitions Tests ==============
        // Test for correct normal of a plane
        assertEquals(p.getNormal(p0), normal, "Error: Bad normal to Plane");
    }

    /**
     * Test method for {@link geometries.Plane#isPointInPlane(primitives.Point3D)}.
     */
    @Test
    void isPointInPlane() {
        Plane pl = new Plane(new Point3D(-1, 0, 0), new Point3D(0, 0, 1), new Point3D(4, 2, 0));

        // ============ Equivalence Partitions Tests ==============
        // Test for point out of the plane
        Point3D pt1 = new Point3D(0, 0, 0);
        assertFalse(pl.isPointInPlane(pt1), "Error at isPointInPlane function");

        // Test for point in the plane
        Point3D pt2 = new Point3D(0, 0.4, 0);
        assertTrue(pl.isPointInPlane(pt2), "Error at isPointInPlane function");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        Plane plane = new Plane(new Point3D(-6, 0, 0), new Point3D(0, 0, 3), new Point3D(-1, 0, 0));

        //TC01: Ray intersects the plane
        Ray r1 = new Ray(new Point3D(0, -1, 4), new Vector(new Point3D(-0.5, 1, -0.5)));
        List<Point3D> result = plane.findIntersections(r1);
        Point3D p1 = new Point3D(-0.5, 0, 3.5);
        assertEquals(1, result.size(), "wrong number of points");
        assertEquals(p1, result.get(0), "wrong point");

        //TC02: Ray doesn't intersects the plane
        Ray r2 = new Ray(new Point3D(0, -1, 4), new Vector(new Point3D(0, -4, -0.5)));
        List<Point3D> result2 = plane.findIntersections(r2);
        assertEquals(null, result2, "ray start after the plane");

        // =============== Boundary Values Tests ==================

        //**** Group:Ray is parallel to the plane
        //TC03: the ray included in the plane
        Ray r3=new Ray(new Point3D(1,0,0),new Vector(new Point3D(0,0,1)));
        assertEquals(null, plane.findIntersections(r3), "return intersection on included ray in plane");

        //TC04: the ray not included in the plane
        Ray r4=new Ray(new Point3D(-2,2,0),new Vector(new Point3D(0,0,1)));
        assertEquals(null, plane.findIntersections(r4), "return intersection on not included ray in plane");

        //**** Group:Ray is orthogonal to the plane
        //TC05:p0 before plane
        Ray r5=new Ray(new Point3D(2,2,0),new Vector(new Point3D(0,-2,0)));
        List<Point3D> result5 = plane.findIntersections(r5);
        Point3D p5 = new Point3D(2, 0, 0);
        assertEquals(1, result5.size(), "wrong number of points");
        assertEquals(p5, result5.get(0), "wrong point");

        //TC06:p0 in plane
        Ray r6=new Ray(new Point3D(2,0,0),new Vector(new Point3D(0,-2,0)));
        assertEquals(null, plane.findIntersections(r6), "return intersection on ray start in plane");

        //TC07:p0 after plane
        Ray r7=new Ray(new Point3D(2,-2,0),new Vector(new Point3D(0,-2,0)));
        assertEquals(null, plane.findIntersections(r7), "return intersection on ray start after plane");

        ////**** Group:Ray is neither orthogonal nor parallel
        //TC08:p0 in plane
        Ray r8=new Ray(new Point3D(2,0,0),new Vector(new Point3D(0,1,0)));
        assertEquals(null, plane.findIntersections(r8), "return intersection on ray start in plane");

        //TC09:p0 is the same point which appears as reference point in the plane
        Ray r9=new Ray(plane.get_p(),new Vector(new Point3D(0,1,0)));
        assertEquals(null, plane.findIntersections(r9), "return intersection on ray start in plane");
    }
}