package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Sphere class represents a radial geometric sphere
 *
 * @author Omri&Ron
 */
public class Sphere extends RadialGeometry {
    Point3D _center;

    public Sphere(double radius, Point3D center) {
        super(radius);
        _center = new Point3D(center);
    }

    public Sphere(Sphere other) {
        super(other);
        this._center = new Point3D(other._center);
    }

    @Override
    public Vector getNormal(Point3D p) {
        return new Vector(p.subtract(_center)).normalize();
    }

    public Point3D get_center() {
        return _center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }

    /**
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return values
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
