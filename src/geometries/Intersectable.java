package geometries;

import primitives.*;

import java.util.List;
import java.util.Objects;

/**
 * Intersectable is a common interface for all geometries that are able
 * to intersect from a ray to their entity (Shape)
 */
public interface Intersectable {

    /**
     * Help class that creates a connection between point and geometry
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * Constructor
         *
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.getClass() == geoPoint.geometry.getClass() &&
                    point.equals(geoPoint.point);
        }

    }


    /**
     * The function calculate intersection points of ray with a geometry
     *
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return list of the intersection points, null if not exists
     */
    List<GeoPoint> findIntersections(Ray ray);
}