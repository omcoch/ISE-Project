package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface Geometry is the basic interface for all geometric objects
 * who are implementing getNormal method.
 *
 * @author Omri Cochavi & Ron Elkabetz
 */
public abstract class Geometry implements Intersectable {

    protected Color _emission;

    /**
     * Constructor
     * @param emission the color of emission
     */
    public Geometry(Color emission) {
        this._emission = emission;
    }

    /**
     * Default constructor
     * set the color of emission with black
     */
    public Geometry() {
        this._emission = Color.BLACK;
    }

    /**
     * The function calculate the normal to the shape at this point
     *
     * @param p point
     * @return the normal (vector)
     */
    public abstract Vector getNormal(Point3D p);

    /**
     * Gets the _emission color of the geometry
     * @return the emission color
     */
    public Color get_emission() {
        return _emission;
    }
}
