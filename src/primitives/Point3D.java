package primitives;

import static primitives.Util.simpleSquare;

/**
 * Class Point3D is the basic class representing a point in a 3D system.
 *
 * @author Ron Elkabetz & Omri Cochavi
 */
public class Point3D {
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    public final static Point3D ZERO = new Point3D(0.0, 0.0, 0.0);


    public Coordinate get_x() {
        return _x; // if _x will be changed while programming, there will be no problem
    }

    public Coordinate get_y() {
        return _y; // if _ will be changed while programming, there will be no problem
    }

    public Coordinate get_z() {
        return _z; // if _z will be changed while programming, there will be no problem
    }

    /**
     * Constructor
     *
     * @param _x coordinate on the X axis
     * @param _y coordinate on the Y axis
     * @param _z coordinate on the Z axis
     */
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = _x;
        this._y = _y;
        this._z = _z;
    }

    public Point3D(double _x, double _y, double _z) {
        this(new Coordinate(_x), new Coordinate(_y), new Coordinate(_z));
    }

    public Point3D(Point3D p) {
        this(new Coordinate(p._x), new Coordinate(p._y), new Coordinate(p._z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) &&
                _y.equals(point3D._y) &&
                _z.equals(point3D._z);
    }

    /**
     * Create vector between two points
     *
     * @param p a point
     * @return a vector starts at p point and go to the current point
     */
    public Vector subtract(Point3D p) {
        return new Vector(new Point3D(
                this._x._coord - p._x._coord,
                this._y._coord - p._y._coord,
                this._z._coord - p._z._coord));
    }

    /**
     * Create a point by moving the current point by vector
     *
     * @param v some vector
     * @return the new point
     */
    public Point3D add(Vector v) {
        return new Point3D(
                v._head._x._coord + _x._coord,
                v._head._y._coord + _y._coord,
                v._head._z._coord + _z._coord
        );
    }

    /**
     * Calculate the Squared distance between two point
     *
     * @param p a point
     * @return the distance Squared between the current point and the given p point
     */
    public double distanceSquared(Point3D p) {
        return simpleSquare(p._x._coord - _x._coord) +
                simpleSquare(p._y._coord - _y._coord) +
                simpleSquare(p._z._coord - _z._coord);
    }

    /**
     * Calculate the distance between two point
     *
     * @param p a point
     * @return the distance between the current point and the given p point
     */
    public double distance(Point3D p) {
        return Math.sqrt(distanceSquared(p));
    }

    @Override
    public String toString() {
        return "(" +
                _x + "," +
                _y + "," +
                _z +
                ')';
    }
}
