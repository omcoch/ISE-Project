package geometries;

/**
 * interface RadialGeometry is the basic interface for all radial geometric objects
 * who are implementing Geometry interface.
 *
 * @author Omri & Ron
 */
public abstract class RadialGeometry implements Geometry {
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
