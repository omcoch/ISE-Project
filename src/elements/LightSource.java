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
     *
     * @param p
     * @return
     */
    public Vector getL(Point3D p);

}
