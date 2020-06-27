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


}
