package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    /**
     * Test method for {@link geometries.Tube#findIntersections(Ray)}.
     */
    @Test
    void findIntersections() {
        Tube t = new Tube(2, new Ray(new Point3D(-3,0,0), new Vector(new Point3D(0,0,1))));

        // ============ Equivalence Partitions Tests ==============

        //Test for 0 intersection points
        assertNull(t.findIntersections(new Ray(new Point3D(0,-2,0),new Vector(0,1,0))),"wrong number of intersection");

        //Test for 1 intersection points
        Ray r1=new Ray(new Point3D(-3,1,1),new Vector(new Point3D(0,-3,0)));
        List<Intersectable.GeoPoint> result = t.findIntersections(r1);
        assertEquals(1, result.size(), "wrong number of intersection points");
        assertEquals(new Point3D(-3,-2,1),result.get(0),"wrong intersection point");

        //Test for 2 intersection points
        Ray r2=new Ray(new Point3D(-3,3,1),new Vector(new Point3D(0,-5,0)));
        Point3D p1 = new Point3D(-3,2,1), p2=new Point3D(-3,-2,1);
        result = t.findIntersections(r2);
        assertEquals(2, result.size(), "wrong number of intersection points");
        if (result.get(0).point.get_y().get() < result.get(1).point.get_y().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses tube");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line parallel to the tube's ray
        // Test for ray is the same ray of the tube
        assertNull(t.findIntersections(t._ray), "Line through tube's ray");

        // Test for ray inside the tube
        assertNull(t.findIntersections(new Ray(new Point3D(-4,0,1), new Vector(0,0,1))),
                "Line parallel tube's ray");

        // **** Group: Ray's line is tangent to the tube (all tests 0 points)
        // Test for ray parallel to the tube and starts at tangent point
        assertNull(t.findIntersections(new Ray(new Point3D(-5,0,1), new Vector(0,0,1))),
                "Line tangent tube");

        //Test for tangent ray at one point
        assertNull(t.findIntersections(new Ray(new Point3D(-5,2,2), new Vector(0,-2,0))),
                "Ray tangent tube");

        // **** Group: Ray's start at the tangent to the tube
        //Test for ray not intersect the tube
        assertNull(t.findIntersections(new Ray(new Point3D(-5,0,2), new Vector(0,-2,0))),
                "Ray tangent tube");

        //Test for ray intersect the tube
        Ray r3=new Ray(new Point3D(-5,0,2),new Vector(4,0,0));
        result=t.findIntersections(r3);
        assertEquals(1,result.size(),"wrong number of points");
        assertEquals(new Point3D(-1,0,2),result.get(0),
                "Ray tangent tube");

        // התחלנו לעשות את הבדיקות אבל מפאת קוצר הזמן לא סיימנו
    }
}