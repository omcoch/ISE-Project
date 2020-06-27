package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a group of geometry components,
 * implements the composite design pattern
 */
public class Geometries implements Intersectable {
    private List<Intersectable> _geometries;

    Point3D[] _bounds=null;

    /**
     * Constructor for creating bounding box
     * @param min
     * @param max
     */
    public Geometries(Point3D min, Point3D max) {
        this();
        // check if the min point is closer to the origin than the max point
        assert (min.distance(Point3D.ZERO) < max.distance(Point3D.ZERO));
        _bounds=new Point3D[2];
        _bounds[0] = new Point3D(min);
        _bounds[1] = new Point3D(max);
    }

    /**
     * Default Constructor
     */
    public Geometries() {
        this._geometries = new LinkedList();
    }

    /**
     * Constructor
     *
     * @param geometries collection of geometries
     */
    public Geometries(Intersectable... geometries) {
        this._geometries = new LinkedList();
        this._geometries.addAll(List.of(geometries));
    }

    /**
     * Add geometries into the list
     *
     * @param geometries collection of geometries
     */
    public void add(Intersectable... geometries) {
        this._geometries.addAll(List.of(geometries));
    }


    /**
     * Calculate intersection points of ray with the geometries
     *
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return list of the intersection points, null if not exists
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        // if the bounding box feature is not activated or
        // the ray doesn't intersect the box => No further calculation is needed
        if(_bounds != null && !isIntersectBox(ray)) return null;

        List<GeoPoint> intersections = null;
        for (Intersectable geo : _geometries) {
            List<GeoPoint> tempIntersections = geo.findIntersections(ray);
            if (tempIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<GeoPoint>();
                intersections.addAll(tempIntersections);
            }
        }
        return intersections;
    }

    /**
     * find if the ray intersect the bounding box
     * @param ray the ray
     * @return true if the ray intersect else return false
     */
    private boolean isIntersectBox(Ray ray){
        double tmin = (_bounds[0].get_x().get() - ray.get_p0().get_x().get()) / ray.get_dir().get_head().get_x().get();
        double tmax = (_bounds[1].get_x().get() - ray.get_p0().get_x().get()) / ray.get_dir().get_head().get_x().get();

        if (tmin > tmax) Util.swap(tmin, tmax);

        double tymin = (_bounds[0].get_y().get() - ray.get_p0().get_y().get()) / ray.get_dir().get_head().get_y().get();
        double tymax = (_bounds[1].get_y().get() - ray.get_p0().get_y().get()) / ray.get_dir().get_head().get_y().get();

        if (tymin > tymax) Util.swap(tymin, tymax);

        if ((tmin > tymax) || (tymin > tmax))
            return false;

        if (tymin > tmin)
            tmin = tymin;

        if (tymax < tmax)
            tmax = tymax;

        double tzmin = (_bounds[0].get_z().get() - ray.get_p0().get_z().get()) / ray.get_dir().get_head().get_z().get();
        double tzmax = (_bounds[1].get_z().get() - ray.get_p0().get_z().get()) / ray.get_dir().get_head().get_z().get();

        if (tzmin > tzmax) Util.swap(tzmin, tzmax);

        if ((tmin > tzmax) || (tzmin > tmax))
            return false;

        if (tzmin > tmin)
            tmin = tzmin;

        if (tzmax < tmax)
            tmax = tzmax;

        return true;
    }
}
