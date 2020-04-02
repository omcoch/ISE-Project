package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube class represents a Tube
 *
 * @author Omri&Ron
 */
public class Tube extends RadialGeometry {
    Ray _ray;


    public Tube(double _radius, Ray _ray) {
        super(_radius);
        this._ray = new Ray(_ray);
    }

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
