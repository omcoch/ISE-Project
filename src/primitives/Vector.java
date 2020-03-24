package primitives;

import java.util.Objects;

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

    public Point3D get_head() {
        // to avoid user's changes of _head (because this is ref)
        return new Point3D(_head._x._coord, _head._y._coord, _head._z._coord);
    }

    public Vector(Point3D p) {
        if (p.equals(Point3D.ZERO))
            throw new IllegalArgumentException("vector cannot be zero");
        this._head = new Point3D(p._x._coord, p._y._coord, p._z._coord);
    }

    public Vector(Vector v) {
        _head = new Point3D(v._head.get_x(), v._head.get_y(), v._head.get_z());
    }

    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(new Point3D(x, y, z));
    }

    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }

    public double dotProduct(Vector n) {
        return _head._x._coord * n._head._x._coord +
                _head._y._coord * n._head._y._coord +
                _head._z._coord * n._head._z._coord;
    }

    public Vector crossProduct(Vector v) {
        return new Vector(
                new Point3D(
                        this._head._y._coord * v._head._z._coord - this._head._z._coord * v._head._y._coord,
                        this._head._z._coord * v._head._x._coord - this._head._x._coord * v._head._z._coord,
                        this._head._x._coord * v._head._y._coord - this._head._y._coord * v._head._x._coord
                )
        );
    }

    public double lengthSquared() {
        return _head.distanceSquared(Point3D.ZERO);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector add(Vector v) {
        return new Vector(new Point3D(
                _head.get_x()._coord + v._head.get_x()._coord,
                _head.get_y()._coord + v._head.get_y()._coord,
                _head.get_z()._coord + v._head.get_z()._coord
        ));
    }

    public Vector subtract(Vector v) {
        return new Vector(new Point3D(
                _head.get_x()._coord - v._head.get_x()._coord,
                _head.get_y()._coord - v._head.get_y()._coord,
                _head.get_z()._coord - v._head.get_z()._coord
        ));
    }

    public Vector scale(double s) {
        return new Vector(new Point3D(
                _head.get_x()._coord * s,
                _head.get_y()._coord * s,
                _head.get_z()._coord * s
        ));
    }

    @Override
    public String toString() {
        return "" + _head;
    }

    /**
     * Calculate the normal of <b>this</b> vector
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
     * @return the <b>new</b> Vector after normalisation
     */
    public Vector normalized() {
        Vector newVec = new Vector(this);
        return newVec.normalize();
    }
}
