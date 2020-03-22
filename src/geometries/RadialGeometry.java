package geometries;

/**
 * interface Geometry is the basic interface for all geometric objects
 * who are implementing getNormal method.
 *
 * @author Omri & Ron
 */
public abstract class RadialGeometry {
    double _radius;


    public RadialGeometry(double _radius) {
        this._radius = _radius;
    }

    public RadialGeometry(RadialGeometry rd) {
        this(rd._radius);
    }

    public double get_radius() {
        return _radius;
    }
}
