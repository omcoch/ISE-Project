package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Cylinder represents a cylinder
 *
 * @author Omri&Ron
 */
public class Cylinder extends RadialGeometry {
    double _height;

    public Cylinder(double _radius, double _height) {
        super(_radius);
        this._height = _height;
    }

    public Cylinder(RadialGeometry rd, double _height) {
        super(rd);
        this._height = _height;
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
