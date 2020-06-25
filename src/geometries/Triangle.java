package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * Triangle class represents a triangle in the scene
 *
 * @author Omri&Ron
 */
public class Triangle extends Polygon {
    /**
     * Calculate intersection of ray with the Triangle using the polygon's function
     *
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return list of the intersection points, null if not exists
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }

    /**
     * constructor of triangle
     *
     * @param emissionLight the color of the triangle
     * @param material      the material of the triangle
     * @param p1            point
     * @param p2            point
     * @param p3            point
     */
    public Triangle(Color emissionLight, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(emissionLight, material, p1, p2, p3);
    }

    /**
     * constructor of triangle
     *
     * @param emissionLight the color of the triangle
     * @param p1            point
     * @param p2            point
     * @param p3            point
     */
    public Triangle(Color emissionLight, Point3D p1, Point3D p2, Point3D p3) {
        super(emissionLight, p1, p2, p3);
    }

    /**
     * constructor of triangle
     *
     * @param p1 point
     * @param p2 point
     * @param p3 point
     */
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
