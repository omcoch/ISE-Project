package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * Geometry is the basic (abstract) class for all geometric objects
 * who are implementing getNormal method.
 *
 * @author Omri Cochavi & Ron Elkabetz
 */
public abstract class Geometry implements Intersectable {

    protected Color _emission;
    protected Material _material;

    /**
     * Constructor
     * @param _emission the color of emission
     * @param _material the material of the object
     */
    public Geometry(Color _emission, Material _material) {
        this._emission = _emission;
        this._material = _material;
    }

    /**
     * Constructor
     * @param emission the color of emission
     */
    public Geometry(Color emission) {
        this(emission,new Material(0, 0, 0));
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

    /**
     * Gets the _material of the geometry
     * @return the material
     */
    public Material get_material() {
        return _material;
    }
}
