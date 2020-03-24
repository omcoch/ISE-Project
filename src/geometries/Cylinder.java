package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder represents a cylinder
 *
 * @author Omri&Ron
 */
public class Cylinder extends Tube {
    double _height;


    public Cylinder(double _radius, Ray _ray, double _height) {
        super(_radius, _ray);
        this._height = _height;
    }
    public Cylinder(Cylinder other){
        super(other);
        this._height=other._height;
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

    public double get_height() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "_height=" + _height +
                ", _radius=" + _radius +
                '}';
    }
}
