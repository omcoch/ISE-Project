package elements;

import geometries.*;
import geometries.Intersectable.*;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Integrations with Camera Class
 *
 * @author Ron & Omri
 */
public class IntegrationTest {

    /**
     * Test method for
     * Integrations of sphere with camera
     */
    @Test
    public void testIntegrationWithSphere() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        List<GeoPoint> r;

        // TC01: r=1
        Sphere s1 = new Sphere(1, new Point3D(0, 0, 3));
        Ray r1 = camera.constructRayThroughPixel(3, 3, 0, 0, 1, 3, 3,1).rayList.get(0),
                r2 = camera.constructRayThroughPixel(3, 3, 0, 1, 1, 3, 3,1).rayList.get(0),
                r3 = camera.constructRayThroughPixel(3, 3, 0, 2, 1, 3, 3,1).rayList.get(0),
                r4 = camera.constructRayThroughPixel(3, 3, 1, 0, 1, 3, 3,1).rayList.get(0),
                r5 = camera.constructRayThroughPixel(3, 3, 1, 1, 1, 3, 3,1).rayList.get(0),
                r6 = camera.constructRayThroughPixel(3, 3, 1, 2, 1, 3, 3,1).rayList.get(0),
                r7 = camera.constructRayThroughPixel(3, 3, 2, 0, 1, 3, 3,1).rayList.get(0),
                r8 = camera.constructRayThroughPixel(3, 3, 2, 1, 1, 3, 3,1).rayList.get(0),
                r9 = camera.constructRayThroughPixel(3, 3, 2, 2, 1, 3, 3,1).rayList.get(0);
        int count = 0;
        count += (r = s1.findIntersections(r1)) != null ? r.size() : 0;
        count += (r = s1.findIntersections(r2)) != null ? r.size() : 0;
        count += (r = s1.findIntersections(r3)) != null ? r.size() : 0;
        count += (r = s1.findIntersections(r4)) != null ? r.size() : 0;
        count += (r = s1.findIntersections(r5)) != null ? r.size() : 0;
        count += (r = s1.findIntersections(r6)) != null ? r.size() : 0;
        count += (r = s1.findIntersections(r7)) != null ? r.size() : 0;
        count += (r = s1.findIntersections(r8)) != null ? r.size() : 0;
        count += (r = s1.findIntersections(r9)) != null ? r.size() : 0;
        assertEquals(2, count, "wrong number of intersection points");

        //TC02: r=2.5
        camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        Sphere s2 = new Sphere(2.5, new Point3D(0, 0, 2.5));
        r1 = camera.constructRayThroughPixel(3, 3, 0, 0, 1, 3, 3,1).rayList.get(0);
        r2 = camera.constructRayThroughPixel(3, 3, 0, 1, 1, 3, 3,1).rayList.get(0);
        r3 = camera.constructRayThroughPixel(3, 3, 0, 2, 1, 3, 3,1).rayList.get(0);
        r4 = camera.constructRayThroughPixel(3, 3, 1, 0, 1, 3, 3,1).rayList.get(0);
        r5 = camera.constructRayThroughPixel(3, 3, 1, 1, 1, 3, 3,1).rayList.get(0);
        r6 = camera.constructRayThroughPixel(3, 3, 1, 2, 1, 3, 3,1).rayList.get(0);
        r7 = camera.constructRayThroughPixel(3, 3, 2, 0, 1, 3, 3,1).rayList.get(0);
        r8 = camera.constructRayThroughPixel(3, 3, 2, 1, 1, 3, 3,1).rayList.get(0);
        r9 = camera.constructRayThroughPixel(3, 3, 2, 2, 1, 3, 3,1).rayList.get(0);
        count = 0;
        count += (r = s2.findIntersections(r1)) != null ? r.size() : 0;
        count += (r = s2.findIntersections(r2)) != null ? r.size() : 0;
        count += (r = s2.findIntersections(r3)) != null ? r.size() : 0;
        count += (r = s2.findIntersections(r4)) != null ? r.size() : 0;
        count += (r = s2.findIntersections(r5)) != null ? r.size() : 0;
        count += (r = s2.findIntersections(r6)) != null ? r.size() : 0;
        count += (r = s2.findIntersections(r7)) != null ? r.size() : 0;
        count += (r = s2.findIntersections(r8)) != null ? r.size() : 0;
        count += (r = s2.findIntersections(r9)) != null ? r.size() : 0;
        assertEquals(18, count, "wrong number of intersection points");

        //TC03: r=2
        Sphere s3 = new Sphere(2, new Point3D(0, 0, 2));
        r1 = camera.constructRayThroughPixel(3, 3, 0, 0, 1, 3, 3,1).rayList.get(0);
        r2 = camera.constructRayThroughPixel(3, 3, 0, 1, 1, 3, 3,1).rayList.get(0);
        r3 = camera.constructRayThroughPixel(3, 3, 0, 2, 1, 3, 3,1).rayList.get(0);
        r4 = camera.constructRayThroughPixel(3, 3, 1, 0, 1, 3, 3,1).rayList.get(0);
        r5 = camera.constructRayThroughPixel(3, 3, 1, 1, 1, 3, 3,1).rayList.get(0);
        r6 = camera.constructRayThroughPixel(3, 3, 1, 2, 1, 3, 3,1).rayList.get(0);
        r7 = camera.constructRayThroughPixel(3, 3, 2, 0, 1, 3, 3,1).rayList.get(0);
        r8 = camera.constructRayThroughPixel(3, 3, 2, 1, 1, 3, 3,1).rayList.get(0);
        r9 = camera.constructRayThroughPixel(3, 3, 2, 2, 1, 3, 3,1).rayList.get(0);
        count = 0;
        count += (r = s3.findIntersections(r1)) != null ? r.size() : 0;
        count += (r = s3.findIntersections(r2)) != null ? r.size() : 0;
        count += (r = s3.findIntersections(r3)) != null ? r.size() : 0;
        count += (r = s3.findIntersections(r4)) != null ? r.size() : 0;
        count += (r = s3.findIntersections(r5)) != null ? r.size() : 0;
        count += (r = s3.findIntersections(r6)) != null ? r.size() : 0;
        count += (r = s3.findIntersections(r7)) != null ? r.size() : 0;
        count += (r = s3.findIntersections(r8)) != null ? r.size() : 0;
        count += (r = s3.findIntersections(r9)) != null ? r.size() : 0;
        assertEquals(10, count, "wrong number of intersection points");

        //TC04: r=4
        camera = new Camera(new Point3D(0, 0, -1), new Vector(0, 0, 1), new Vector(0, -1, 0));
        Sphere s4 = new Sphere(4, new Point3D(0, 0, 0));
        r1 = camera.constructRayThroughPixel(3, 3, 0, 0, 1, 3, 3,1).rayList.get(0);
        r2 = camera.constructRayThroughPixel(3, 3, 0, 1, 1, 3, 3,1).rayList.get(0);
        r3 = camera.constructRayThroughPixel(3, 3, 0, 2, 1, 3, 3,1).rayList.get(0);
        r4 = camera.constructRayThroughPixel(3, 3, 1, 0, 1, 3, 3,1).rayList.get(0);
        r5 = camera.constructRayThroughPixel(3, 3, 1, 1, 1, 3, 3,1).rayList.get(0);
        r6 = camera.constructRayThroughPixel(3, 3, 1, 2, 1, 3, 3,1).rayList.get(0);
        r7 = camera.constructRayThroughPixel(3, 3, 2, 0, 1, 3, 3,1).rayList.get(0);
        r8 = camera.constructRayThroughPixel(3, 3, 2, 1, 1, 3, 3,1).rayList.get(0);
        r9 = camera.constructRayThroughPixel(3, 3, 2, 2, 1, 3, 3,1).rayList.get(0);
        count = 0;
        count += (r = s4.findIntersections(r1)) != null ? r.size() : 0;
        count += (r = s4.findIntersections(r2)) != null ? r.size() : 0;
        count += (r = s4.findIntersections(r3)) != null ? r.size() : 0;
        count += (r = s4.findIntersections(r4)) != null ? r.size() : 0;
        count += (r = s4.findIntersections(r5)) != null ? r.size() : 0;
        count += (r = s4.findIntersections(r6)) != null ? r.size() : 0;
        count += (r = s4.findIntersections(r7)) != null ? r.size() : 0;
        count += (r = s4.findIntersections(r8)) != null ? r.size() : 0;
        count += (r = s4.findIntersections(r9)) != null ? r.size() : 0;
        assertEquals(9, count, "wrong number of intersection points");

        //TC05: r=0.5
        camera = new Camera(new Point3D(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0));
        Sphere s5 = new Sphere(0.5, new Point3D(0, 0, -1));
        r1 = camera.constructRayThroughPixel(3, 3, 0, 0, 1, 3, 3,1).rayList.get(0);
        r2 = camera.constructRayThroughPixel(3, 3, 0, 1, 1, 3, 3,1).rayList.get(0);
        r3 = camera.constructRayThroughPixel(3, 3, 0, 2, 1, 3, 3,1).rayList.get(0);
        r4 = camera.constructRayThroughPixel(3, 3, 1, 0, 1, 3, 3,1).rayList.get(0);
        r5 = camera.constructRayThroughPixel(3, 3, 1, 1, 1, 3, 3,1).rayList.get(0);
        r6 = camera.constructRayThroughPixel(3, 3, 1, 2, 1, 3, 3,1).rayList.get(0);
        r7 = camera.constructRayThroughPixel(3, 3, 2, 0, 1, 3, 3,1).rayList.get(0);
        r8 = camera.constructRayThroughPixel(3, 3, 2, 1, 1, 3, 3,1).rayList.get(0);
        r9 = camera.constructRayThroughPixel(3, 3, 2, 2, 1, 3, 3,1).rayList.get(0);
        count = 0;
        count += (r = s5.findIntersections(r1)) != null ? r.size() : 0;
        count += (r = s5.findIntersections(r2)) != null ? r.size() : 0;
        count += (r = s5.findIntersections(r3)) != null ? r.size() : 0;
        count += (r = s5.findIntersections(r4)) != null ? r.size() : 0;
        count += (r = s5.findIntersections(r5)) != null ? r.size() : 0;
        count += (r = s5.findIntersections(r6)) != null ? r.size() : 0;
        count += (r = s5.findIntersections(r7)) != null ? r.size() : 0;
        count += (r = s5.findIntersections(r8)) != null ? r.size() : 0;
        count += (r = s5.findIntersections(r9)) != null ? r.size() : 0;
        assertEquals(0, count, "wrong number of intersection points");
    }

    /**
     * Test method for
     * Integrations of plane with camera
     */
    @Test
    public void testIntegrationWithPlane() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        List<GeoPoint> res;
        int count = 0;

        //TC01: parallel
        Plane p1 = new Plane(new Point3D(0, 0, 3), camera.getVto());

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                res = p1.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3,1).rayList.get(0));
                if (res != null)
                    count += res.size();
            }
        }

        assertEquals(9, count, "wrong number of intersection points");

        //TC02: Slightly tilted (9 points)
        Plane p2 = new Plane(new Point3D(0, 0, 5), new Vector(0, -1, 2));
        count = 0;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                res = p2.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3,1).rayList.get(0));
                if (res != null)
                    count += res.size();
            }
        }

        assertEquals(9, count, "wrong number of intersection points");

        //TC02: Hard tilted (6 points)
        Plane p3 = new Plane(new Point3D(0, 0, 3), new Point3D(0, 1, 4), new Point3D(1, -1, 3));
        count = 0;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                res = p3.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3,1).rayList.get(0));
                if (res != null)
                    count += res.size();
            }
        }

        assertEquals(6, count, "wrong number of intersection points");
    }

    /**
     * Test method for
     * Integrations of triangle with camera
     */
    @Test
    public void testIntegrationWithTriangle() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        List<GeoPoint> res;
        int count = 0;

        //TC01: small
        Triangle t1 = new Triangle(new Point3D(0, -1, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                res = t1.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3,1).rayList.get(0));
                if (res != null)
                    count += res.size();
            }
        }

        assertEquals(1, count, "wrong number of intersection points");

        //TC02: long
        Triangle t2 = new Triangle(new Point3D(0, -20, 2), new Point3D(1, 1, 2), new Point3D(-1, 1, 2));
        count=0;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                res = t2.findIntersections(camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3,1).rayList.get(0));
                if (res != null)
                    count += res.size();
            }
        }

        assertEquals(2, count, "wrong number of intersection points");
    }

}
