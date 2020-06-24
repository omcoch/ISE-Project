package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Cylinder class, represents a cylinder in the scene
 *
 * @author Omri&Ron
 */
public class Cylinder extends Tube {
    double _height;

    /**
     * Constructor for cylinder
     *
     * @param _radius radius length
     * @param _ray
     * @param _height
     */
    public Cylinder(double _radius, Ray _ray, double _height) {
        super(_radius, _ray);
        this._height = _height;
    }

    /**
     * Copy constructor
     *
     * @param other another cylinder object
     */
    public Cylinder(Cylinder other) {
        super(other);
        this._height = other._height;
    }

    /**
     * The function calculate the normal to the shape at this point
     *
     * @param p point
     * @return the normal (vector)
     */
    @Override
    public Vector getNormal(Point3D p) {
        // base1 and base2 are the button and upper bases of the cylinder.
        Plane base1 = new Plane(_ray.get_p0(), _ray.get_dir()),
                //p0+normal(v)*height
                base2 = new Plane(_ray.get_p0().add(_ray.get_dir().normalized().scale(_height)), _ray.get_dir());

        if (base1.isPointInPlane(p) || base2.isPointInPlane(p)) // the point is on one of the bases
            return _ray.get_dir().normalized();

        return super.getNormal(p); // the point is on the casing
    }

    /**
     * Getter for _height
     *
     * @return
     */
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

    /**
     * Calculate intersection of ray with cylinder
     *
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return list of the intersection points
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }
}
