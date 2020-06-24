package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Interface that provides the necessary methods of light sources
 */
public interface LightSource {
    /**
     * Returns the intensity of the light in a given
     *
     * @param p the point
     * @return color of intensity
     */
    public Color getIntensity(Point3D p);

    /**
     * Returns a vector from the light to the point
     *
     * @param p point
     * @return vector from light to point
     */
    public Vector getL(Point3D p);

    /**
     * Returns the distance between the light source to the point
     *
     * @param p the point
     * @return the distance
     */
    public double getDistance(Point3D p);

}
