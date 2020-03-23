package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface Geometry is the basic interface for all geometric objects
 * who are implementing getNormal method.
 *
 * @author Omri Cochavi
 */
public interface Geometry {
    /**
     * The function calculate the normal to the shape at this point
     *
     * @param p point
     * @return the normal (vector)
     */
    Vector getNormal(Point3D p);
}
