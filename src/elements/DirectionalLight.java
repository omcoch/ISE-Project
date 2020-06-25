package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * Represents directional light in a certain direction
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector _direction;

    /**
     * constructor of directional light
     *
     * @param _intensity the intensity of the light
     * @param _direction the direction of the light
     */
    public DirectionalLight(Color _intensity, Vector _direction) {
        super(_intensity);
        this._direction = _direction.normalize();
    }

    /**
     * Returns intensity color of a point
     *
     * @param p the point
     * @return color of intensity of point p
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    /**
     * Return a vector from the light to the point
     *
     * @param p point
     * @return vector from light to point p
     */
    @Override
    public Vector getL(Point3D p) {
        return new Vector(_direction);
    }

    /**
     * Return the distance between the light source and a specific point
     *
     * @param p the point
     * @return the distance between the light source to the point p
     */
    @Override
    public double getDistance(Point3D p) {
        // Directional light has no fixed position
        return Double.POSITIVE_INFINITY;
    }


}
