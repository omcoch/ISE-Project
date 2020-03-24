package geometries;

import static primitives.Util.isZero;

/**
 * interface RadialGeometry is the basic interface for all radial geometric objects
 * who are implementing Geometry interface.
 *
 * @author Omri & Ron
 */
public abstract class RadialGeometry implements Geometry {
    double _radius;

    public RadialGeometry(double _radius) {
        if (isZero(_radius) || (_radius < 0.0))
            throw new IllegalArgumentException("radius " + _radius + " is not valid");
        this._radius = _radius;
    }

    public RadialGeometry(RadialGeometry rd) {
        this(rd._radius);
    }

    public double get_radius() {
        return _radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RadialGeometry that = (RadialGeometry) o;

        return isZero(this._radius - that._radius);
    }
}
