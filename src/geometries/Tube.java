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
        this._ray =new Ray(other._ray);
    }

    /**
     * The function calculate the normal to the shape at this point
     *
     * @param p point
     * @return the normal (vector)
     */
    @Override
    public Vector getNormal(Point3D p) {
        return null;
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
