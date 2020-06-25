package geometries;

import primitives.Color;
import primitives.Material;

import static primitives.Util.isZero;

/**
 * RadialGeometry is the basic class for all radial geometric objects
 * who are implementing Geometry interface.
 */
public abstract class RadialGeometry extends Geometry {
    double _radius;

    /**
     * Constructor with all the parameters
     *
     * @param emissionLight the color
     * @param radius        radius of radial-geometry
     * @param material      the material
     */
    public RadialGeometry(Color emissionLight, double radius, Material material) {
        super(emissionLight, material);
        _radius = radius;
    }

    /**
     * Constructor with some default parameters
     *
     * @param emissionLight the color
     * @param radius        the radius of radial-geometry
     */
    public RadialGeometry(Color emissionLight, double radius) {
        super(emissionLight);
        _radius = radius;
    }

    /**
     * Constructor with some default parameters
     *
     * @param radius
     */
    public RadialGeometry(double radius) {
        super();
        _radius = radius;
    }

    /**
     * Copy constructor
     *
     * @param rd another Radial Geometry object
     */
    public RadialGeometry(RadialGeometry rd) {
        this(rd._radius);
    }

    /**
     * Getter for _radius
     *
     * @return the radius
     */
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
