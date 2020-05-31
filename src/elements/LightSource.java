package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 *
 */
public interface LightSource {
    /**
     * Returns intensity color of a point
     * @param p the point
     * @return color of intensity
     */
    public Color getIntensity(Point3D p);

    /**
     * return a vector from the light to the point
     * @param p point
     * @return vector from light to point
     */
    public Vector getL(Point3D p);

    /**
     * return the distance between the light source to the point
     * @param p the point
     * @return distance between the light source to the point
     */
    public double getDistance(Point3D p);

}
