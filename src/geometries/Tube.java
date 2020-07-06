package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Tube class represents a Tube in a scene
 *
 * @author Omri & Ron
 */
public class Tube extends RadialGeometry {
    Ray _ray; // The central axis of the tube

    @Override
    public Point3D[] getBounds() {
        return null;
    }

    /**
     * Calculate intersection of ray with the tube
     *
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return list of the intersection points, null if not exists
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return null;
    }

    /**
     * Constructor
     *
     * @param _radius the radius
     * @param _ray    the direction of the tube
     */
    public Tube(double _radius, Ray _ray) {
        super(_radius);
        this._ray = new Ray(_ray);
    }

    /**
     * Copy Constructor
     *
     * @param other other tube
     */
    public Tube(Tube other) {
        super(other);
        this._ray = new Ray(other._ray);
    }

    /**
     * The function calculate the normal to the shape at this point
     *
     * @param p point
     * @return the normal (vector)
     */
    @Override
    public Vector getNormal(Point3D p) {
        double t = _ray.get_dir().dotProduct(p.subtract(_ray.get_p0()));
        Point3D center = _ray.get_p0().add(_ray.get_dir().scale(t));
        return new Vector(p.subtract(center)).normalize();
    }

    /**
     * Getter for _ray
     *
     * @return the direction of the tube
     */
    public Ray get_ray() {
        return _ray;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "_ray=" + _ray +
                ", _radius=" + _radius +
                '}';
    }
}
