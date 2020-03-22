package primitives;

import java.util.Objects;

/**
 * Class Ray is the basic class representing a ray in a 3D system.
 *
 * @author Ron & Omri
 */
public class Ray {
    Point3D _p0;
    Vector _dir;

    public Point3D get_p0() {
        return _p0;
    }

    public Vector get_dir() {
        return _dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals( ray._p0) &&
                _dir.equals( ray._dir);
    }

    public Ray(Point3D p0, Vector dir) {
        this._p0 = new Point3D(p0._x._coord,p0._y._coord,p0._z._coord);

        this._dir = new Vector(dir.normalized());
    }

    @Override
    public String toString() {
        return "Ray{" +
                "_p0=" + _p0 +
                ", _dir=" + _dir +
                '}';
    }
}
