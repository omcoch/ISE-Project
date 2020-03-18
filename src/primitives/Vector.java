package primitives;

import java.util.Objects;

/**
 * class Vector
 *
 * @author Omri & Ron
 */
public class Vector {
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
        return new Point3D(_head._x._coord,_head._y._coord,_head._z._coord);
    }

    public Vector(Point3D p) {
        this._head = new Point3D(p._x._coord,p._y._coord,p._z._coord);;
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
                        this._head._z._coord * v._head._x._coord - this._head._x._coord * v._head._z    ._coord,
                        this._head._x._coord * v._head._y._coord - this._head._y._coord * v._head._x._coord
                )
        );
    }
}
