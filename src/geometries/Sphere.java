package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Sphere class represents a radial geometric sphere
 *
 * @author Omri&Ron
 */
public class Sphere extends RadialGeometry {
    Point3D _center;

    public Sphere(double radius, Point3D center) {
        super(radius);
        _center = new Point3D(center);
    }

    public Sphere(Sphere other) {
        super(other);
        this._center = new Point3D(other._center);
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
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return values
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        double tm,th,t1,t2,d;
        if(this._center.equals(ray.get_p0())) {//in case of ray start at center
            th=this._radius;
            t1=th;
            t2=-th;
        }
        else{
            Vector u = this._center.subtract(ray.get_p0());
            tm = Util.alignZero(ray.get_dir().dotProduct(u));
            d = Math.sqrt(u.lengthSquared() - Util.simpleSquare(tm));
            if (d > this._radius)
                return null;
            th = Math.sqrt(Util.simpleSquare(this._radius) - Util.simpleSquare(d));
            t1 = tm + th;
            t2 = tm - th;
        }
        if(t2!=t1) {
            if (t1 > 0&&t2 > 0){
                return List.of(ray.getPoint(t1),
                        ray.getPoint(t2));
            }
            if (t1 > 0) {
                return List.of(ray.getPoint(t1));
            }
            if (t2 > 0) {
                return List.of(ray.getPoint(t2));
            }
        }
        return null;
    }
}
