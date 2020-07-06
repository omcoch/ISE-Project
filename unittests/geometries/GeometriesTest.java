package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void findIntersections() {
        Geometries geo = new Geometries(new Plane(new Point3D(0, -4, 0), new Point3D(1, 0, 0), new Point3D(0, 0, 4)),
                new Sphere(1, new Point3D(3, 0, 0)),
                new Polygon(new Point3D(0, -5, 0), new Point3D(6, 0, 0), new Point3D(6, -4, 0), new Point3D(0, -6, 0)),
                new Triangle(new Point3D(0, 5, 0), new Point3D(0, 0, -2), new Point3D(1, 2, 3)));
        // ============ Equivalence Partitions Tests ==============
        // TC01: few shapes but not all intersect
        Ray r1 = new Ray(new Point3D(3, 2, 0), new Vector(-4.5, 0, 0));
        assertEquals(2, geo.findIntersections(r1).size(), "wrong number of points");

        // =============== Boundary Values Tests ==================
        // TC02: empty collection of geometries
        Ray r2 = new Ray(new Point3D(3, 2, 0), new Vector(-4.5, 0, 0));
        assertNull((new Geometries()).findIntersections(r2), "intersection points at empty collection");

        // TC03: all shapes not intersect
        Ray r3 = new Ray(new Point3D(3, 2, 0), new Vector(2, -1, -2));
        assertNull(geo.findIntersections(r3), "wrong number of points");

        // TC04: only one geo intersect
        Ray r4 = new Ray(new Point3D(3, 2, 0), new Vector(-8, -8, 0));
        assertEquals(1, geo.findIntersections(r4).size(), "wrong number of points");

        // TC05: few shapes but not all intersect
        Ray r5 = new Ray(new Point3D(7.3, -4.8, -1.1), new Vector(-9.3, 10.5, 4.3));
        assertEquals(5, geo.findIntersections(r5).size(), "wrong number of points");
    }
}