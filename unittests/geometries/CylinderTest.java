package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    Cylinder c = new Cylinder(2, new Ray(new Point3D(0, 0, 2), new Vector(0, 0, -1)), 4);


    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // Test for correct normal of the casing
        assertEquals(new Vector(1, 0, 0), c.getNormal(new Point3D(2, 0, 0)), "The normal was incorrect");

        //Test for correct normal of one base
        assertEquals(c._ray.get_dir(), c.getNormal(new Point3D(1, 1, 2)), "The normal was incorrect");

        //Test for correct normal of other base
        assertEquals(c._ray.get_dir(), c.getNormal(new Point3D(-1, -1, -2)), "The normal was incorrect");


        // =============== Boundary Values Tests ==================
        // Test for correct normal on the border of casing and base1
        assertEquals(c._ray.get_dir(), c.getNormal(new Point3D(2, 0, 2)), "The normal was incorrect");

        // Test for correct normal on the border of casing and base2
        assertEquals(c._ray.get_dir(), c.getNormal(new Point3D(0, 2, -2)), "The normal was incorrect");

    }

    @Test
    void findIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //
    }
}