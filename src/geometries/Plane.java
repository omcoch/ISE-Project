package geometries;

import primitives.*;

import java.util.List;

/**
 * Plane class represents a Plane in scene
 *
 * @author Omri&Ron
 */
public class Plane extends Geometry {
    Point3D _p;
    primitives.Vector _normal;

    /**
     * Constructor with all the possible parameters
     *
     * @param emissionLight emission for plane
     * @param material      material for plane
     * @param p1            first point in the space
     * @param p2            second point in the space
     * @param p3            third point in the space
     */
    public Plane(Color emissionLight, Material material, Point3D p1, Point3D p2, Point3D p3) {
        super(emissionLight, material);

        _p = new Point3D(p1);

        _p = new Point3D(p1.get_x(), p1.get_y(), p1.get_z());
        Vector v1 = p2.subtract(p1), v2 = p3.subtract(p1);
        _normal = v1.crossProduct(v2).normalize();
    }

    /**
     * Constructor which assume some default parameter values
     *
     * @param emission emission for plane
     * @param p1       first point in the space
     * @param p2       second point in the space
     * @param p3       third point in the space
     */
    public Plane(Color emission, Point3D p1, Point3D p2, Point3D p3) {
        this(emission, new Material(0, 0, 0), p1, p2, p3);
    }

    /**
     * Constructor which assume some default parameter values
     *
     * @param p1 first point in the space
     * @param p2 second point in the space
     * @param p3 third point in the space
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        this(Color.BLACK, p1, p2, p3);
    }

    /**
     * Constructor which assume some default parameter values
     *
     * @param emission emission for plane
     * @param material material for plane
     * @param _p       point in the space
     * @param _normal  normal to the plane
     */
    public Plane(Color emission, Material material, Point3D _p, Vector _normal) {
        super(emission, material);
        this._p = _p;
        this._normal = new Vector(_normal);
    }

    /**
     * Constructor which assume some default parameter values
     *
     * @param emission emission for plane
     * @param _p       point in the space
     * @param _normal  normal to the plane
     */
    public Plane(Color emission, Point3D _p, Vector _normal) {
        this(emission, new Material(0, 0, 0), _p, _normal);
    }

    /**
     * Constructor which assume some default parameter values
     *
     * @param _p      point in the space
     * @param _normal normal to the plane
     */
    public Plane(Point3D _p, Vector _normal) {
        this(Color.BLACK, _p, _normal);
    }

    /**
     * Copy constructor
     *
     * @param pl another plane object
     */
    public Plane(Plane pl) {
        this(pl._p, pl._normal);
    }

    /**
     * Getter for _p
     *
     * @return the point _p in the plane
     */
    public Point3D get_p() {
        return _p;
    }

    /**
     * Getter for _normal
     *
     * @return the normal to the plane
     */
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
     *
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return list of the intersection points, null if not exists
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {

        if (this._p.equals(ray.get_p0())) // the ray start inside plane
            return null;

        double nv = this._normal.dotProduct(ray.get_dir());
        if (Util.isZero(nv)) // Ray is parallel to the plane
            return null;

        double t = Util.alignZero(this._normal.dotProduct(this._p.subtract(ray.get_p0())) / nv);
        if (t > 0) {
            GeoPoint p = new GeoPoint(this, ray.getPoint(t));
            return List.of(p);
        }
        return null;
    }
}
