package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import primitives.*;

import java.util.List;
import java.util.Objects;

/**
 * Plane class represents a Plane
 *
 * @author Omri&Ron
 */
public class Plane implements Geometry {
    Point3D _p;
    primitives.Vector _normal;

    public Plane(Point3D _p, Vector _normal) {
        this._p = new Point3D(_p);
        this._normal = new Vector(_normal);
    }

    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _p = new Point3D(p1.get_x(), p1.get_y(), p1.get_z());
        Vector v1 = p2.subtract(p1), v2 = p3.subtract(p1);
        _normal = v1.crossProduct(v2).normalize();
    }

    public Plane(Plane pl) {
        this(pl._p, pl._normal);
    }

    public Point3D get_p() {
        return _p;
    }

    public Vector get_normal() {
        return _normal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return plane._p.equals(_p) &&
                plane._normal.equals(_normal);
    }

    @Override
    public Vector getNormal(Point3D p) {
        return _normal;
    }

    public Vector getNormal() {
        return getNormal(_p);
    }

    /**
     * the function checks if a point is in the plane
     *
     * @param p a point
     * @return true if the point is in the plane else false
     */
    public boolean isPointInPlane(Point3D p) {
        // Plane equation: aX + bY + cZ + d = 0
        double a = _normal.get_head().get_x().get(),
                b = _normal.get_head().get_y().get(),
                c = _normal.get_head().get_z().get(),
                d = -a * _p.get_x().get() - b * _p.get_y().get() - c * _p.get_z().get();

        // Placing the point in the plane equation
        return (a * p.get_x().get() + b * p.get_y().get() + c * p.get_z().get() + d) == 0;
    }

    /**
     * Calculate intersection points of ray with the plane
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return list of the intersection points, null if not exists
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        if (this._p.equals(ray.get_p0())) // the ray start inside plane
            return null;

        double nv = this._normal.dotProduct(ray.get_dir());
        if(Util.isZero(nv)) // Ray is parallel to the plane
            return null;

        double t = Util.alignZero(this._normal.dotProduct(this._p.subtract(ray.get_p0())) / nv);
        if (t > 0) {
            Point3D p = ray.getPoint(t);
            return List.of(p);
        }
        return null;
    }
}
