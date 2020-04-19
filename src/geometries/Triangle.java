package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Triangle class represents a triangle
 *
 * @author Omri&Ron
 */
public class Triangle extends Polygon {
    /**
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return values
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }

    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "_vertices=" + _vertices +
                ", _plane=" + _plane +
                '}';
    }
}
