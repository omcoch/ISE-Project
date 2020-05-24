package geometries;

import primitives.Color;
import primitives.Material;

import static primitives.Util.isZero;

/**
 * interface RadialGeometry is the basic interface for all radial geometric objects
 * who are implementing Geometry interface.
 *
 * @author Omri & Ron
 */
public abstract class RadialGeometry extends Geometry {
    double _radius;

    public RadialGeometry(Color emissionLight, double radius, Material material) {
        super(emissionLight, material);
        _radius=radius;
    }

    public RadialGeometry(Color emissionLight, double radius) {
        super(emissionLight);
        _radius=radius;
    }

    public RadialGeometry(double radius) {
        super();
        _radius=radius;
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
