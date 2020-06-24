package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a group of geometry components,
 * implements the composite design pattern
 */
public class Geometries implements Intersectable {
    private List<Intersectable> _geometries;

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
