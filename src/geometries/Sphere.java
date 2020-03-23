package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Sphere class represents a radial geometric sphere
 *
 * @author Omri&Ron
 */
public class Sphere extends RadialGeometry  {
    Point3D _center;

    public Sphere(double radius, Point3D center) {
        super(radius);
        _center = new Point3D(center);
    }

    @Override
    public Vector getNormal(Point3D p) {
        return null;
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
}
