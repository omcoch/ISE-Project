package geometries;

import primitives.*;

import java.util.List;

/**
 * Intersectable is a common interface for all geometries that are able
 * to intersect from a ray to their entity (Shape)
 */
public interface Intersectable {
    /**
     * The function calculate intersection points of ray with a geometry
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return list of the intersection points, null if not exists
     */
    List<Point3D> findIntersections(Ray ray);
}