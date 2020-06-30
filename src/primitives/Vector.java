package primitives;

/**
 * class Vector is the basic class representing a vector in a 3D system.
 *
 * @author Omri & Ron
 */
public class Vector {
    /**
     * Point that representing direction of the vector
     */
    Point3D _head;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    /**
     * Getter for _head
     *
     * @return
     */
    public Point3D get_head() {
        // to avoid user's changes of _head (because this is ref)
        return _head;
    }

    /**
     * Constructor
     *
     * @param p point
     */
    public Vector(Point3D p) {
        if (p.equals(Point3D.ZERO))
            throw new IllegalArgumentException("vector cannot be zero");
        this._head = new Point3D(p._x._coord, p._y._coord, p._z._coord);
    }

    /**
     * Copy constructor
     *
     * @param v other vector object
     */
    public Vector(Vector v) {
        _head = new Point3D(v._head.get_x(), v._head.get_y(), v._head.get_z());
    }

    /**
     * Constructor
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @param z the z coordinate
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(new Point3D(x, y, z));
    }

    /**
     * Constructor
     *
     * @param x the x value
     * @param y the y value
     * @param z the z value
     */
    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }

    /**
     * Calculate the value of the dot product between two vectors
     *
     * @param n another vector
     * @return the dot product value
     */
    public double dotProduct(Vector n) {
        return _head._x._coord * n._head._x._coord +
                _head._y._coord * n._head._y._coord +
                _head._z._coord * n._head._z._coord;
    }

    /**
     * Create a new vector by cross product between two vectors
     *
     * @param v another vector
     * @return result of cross product
     */
    public Vector crossProduct(Vector v) {
        return new Vector(
                new Point3D(
                        this._head._y._coord * v._head._z._coord - this._head._z._coord * v._head._y._coord,
                        this._head._z._coord * v._head._x._coord - this._head._x._coord * v._head._z._coord,
                        this._head._x._coord * v._head._y._coord - this._head._y._coord * v._head._x._coord
                )
        );
    }

    /**
     * Calculate the squared length of the vector
     *
     * @return the squared length
     */
    public double lengthSquared() {
        return _head.distanceSquared(Point3D.ZERO);
    }

    /**
     * Calculate the length of the vector
     *
     * @return the length
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Create a new vector by adding a vector to current vector
     *
     * @param v another vector
     * @return the result of adding two vector
     */
    public Vector add(Vector v) {
        return new Vector(new Point3D(
                _head.get_x()._coord + v._head.get_x()._coord,
                _head.get_y()._coord + v._head.get_y()._coord,
                _head.get_z()._coord + v._head.get_z()._coord
        ));
    }

    /**
     * Create a new vector by subtract a vector from the current vector
     *
     * @param v another vector
     * @return the result of subtracting two vectors
     */
    public Vector subtract(Vector v) {
        return new Vector(new Point3D(
                _head.get_x()._coord - v._head.get_x()._coord,
                _head.get_y()._coord - v._head.get_y()._coord,
                _head.get_z()._coord - v._head.get_z()._coord
        ));
    }

    /**
     * Create a new vector by scale the current one
     *
     * @param s scalar to the scale the vector by
     * @return the result of scaling the vector
     */
    public Vector scale(double s) {
        return new Vector(new Point3D(
                _head.get_x()._coord * s,
                _head.get_y()._coord * s,
                _head.get_z()._coord * s
        ));
    }

    /**
     * This function create and return a new vector that is orthogonal to this vector
     *
     * @return the orthogonal vector
     */
    public Vector getOrthogonal() {
        /*if(Util.isZero(_head._x._coord)) {
            return new Vector(0,_head._z._coord*-1,_head._y._coord).normalize();
        }
        return new Vector(_head._y._coord*-1,_head._x._coord,0).normalize();*/
        if (_head._x._coord <= _head._y._coord && _head._x._coord <= _head._z._coord)
            return new Vector(0.0, _head._z._coord * -1, _head._y._coord).normalize();
        else if (_head._y._coord <= _head._x._coord && _head._y._coord <= _head._z._coord)
            return new Vector(_head._z._coord * -1, 0.0, _head._x._coord).normalize();
        else if (_head._x._coord == 0 && _head._y._coord == 0)
            return new Vector(1.0, 1.0, 0.0).normalize();
        else
            return new Vector(_head._y._coord * -1, _head._x._coord, 0.0).normalize();
    }

    @Override
    public String toString() {
        return "" + _head;
    }

    /**
     * Calculate and change the normal of <b>this</b> vector
     *
     * @return the same Vector after normalisation
     */
    public Vector normalize() {
        double len = length();
        _head = new Point3D(
                _head._x._coord / len,
                _head._y._coord / len,
                _head._z._coord / len
        );
        return this;
    }

    /**
     * Calculate the normal of this vector
     *
     * @return the <b>new</b> Vector after normalisation
     */
    public Vector normalized() {
        Vector newVec = new Vector(this);
        return newVec.normalize();
    }
}
