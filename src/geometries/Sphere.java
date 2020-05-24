package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Sphere class represents a radial geometric sphere
 *
 * @author Omri&Ron
 */
public class Sphere extends RadialGeometry {
    Point3D _center;

    public Sphere(Color emissionLight, Material material, double radius, Point3D center) {
        super(emissionLight, radius, material);
        this._center = new Point3D(center);
    }

    public Sphere(Color emissionLight, double radius, Point3D center) {
        this(emissionLight,new Material(0, 0, 0),radius,center);
    }

    public Sphere(double radius, Point3D center) {
        this(Color.BLACK,radius,center);
    }


    @Override
    public Vector getNormal(Point3D p) {
        return new Vector(p.subtract(_center)).normalize();
    }

    public Point3D get_center() {
        return _center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }

    /**
     * Calculate intersection of ray with the sphere
     *
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return list of the intersection points, null if not exists
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        double tm, th, t1, t2, d;
        if (this._center.equals(ray.get_p0())) {//in case of ray start at center
            th = this._radius;
            t1 = th;
            t2 = -th;
        } else {
            Vector u = this._center.subtract(ray.get_p0());
            tm = Util.alignZero(ray.get_dir().dotProduct(u));
            d = Math.sqrt(u.lengthSquared() - Util.simpleSquare(tm));
            if (d > this._radius)
                return null;
            th = Math.sqrt(Util.simpleSquare(this._radius) - Util.simpleSquare(d));
            t1 = tm + th;
            t2 = tm - th;
        }
        if (t2 != t1) {
            if (t1 > 0 && t2 > 0) { // the ray starts before the sphere
                return List.of(new GeoPoint(this,ray.getPoint(t1)),
                        new GeoPoint(this,ray.getPoint(t2)));
            }
            // the ray starts inside the sphere (2 options for t, but chose  only the positive)
            if (t1 > 0) {
                return List.of(new GeoPoint(this,ray.getPoint(t1)));
            }
            if (t2 > 0) {
                return List.of(new GeoPoint(this,ray.getPoint(t2)));
            }
        }
        return null;
    }
}
