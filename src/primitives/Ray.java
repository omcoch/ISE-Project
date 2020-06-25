package primitives;

/**
 * Class Ray is the basic class representing a ray in a 3D system.
 *
 * @author Ron & Omri
 */
public class Ray {
    Point3D _p0;
    Vector _dir;
    /**
     * used for moving the point by DELTA
     */
    private static final double DELTA = 0.1;

    /**
     * Getter for the start of the ray
     *
     * @return _p
     */
    public Point3D get_p0() {
        return _p0;
    }

    /**
     * Calculate a point on the ray
     *
     * @param length the length from _p0
     * @return new Point3D
     */
    public Point3D getPoint(double length) {
        return Util.isZero(length) ? new Point3D(_p0) : _p0.add(_dir.scale(length));
    }

    /**
     * Getter for the direction of the ray
     *
     * @return the direction vector
     */
    public Vector get_dir() {
        return _dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) &&
                _dir.equals(ray._dir);
    }

    /**
     * Constructor
     *
     * @param point     start point
     * @param direction direction vector for the ray
     * @param normal    the normal at the starting  point
     */
    public Ray(Point3D point, Vector direction, Vector normal) {
        // head + normal.scale(±DELTA)
        _dir = new Vector(direction).normalized();
        double nv = normal.dotProduct(direction);
        Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
        _p0 = point.add(normalDelta);
    }

    /**
     * Constructor
     *
     * @param p0  start point
     * @param dir direction vector for the ray
     */
    public Ray(Point3D p0, Vector dir) {
        this._p0 = new Point3D(p0._x._coord, p0._y._coord, p0._z._coord);

        this._dir = new Vector(dir.normalized());
    }

    /**
     * Copy constructor
     *
     * @param other
     */
    public Ray(Ray other) {
        this._dir = new Vector(other._dir);
        this._p0 = new Point3D(other._p0);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "_p0=" + _p0 +
                ", _dir=" + _dir +
                '}';
    }
}
