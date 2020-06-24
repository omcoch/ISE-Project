package elements;

import primitives.Color;
import primitives.Point3D;

/**
 * Represents a light, the abstract base class for all light implementations.
 */
public abstract class Light {

    protected Color _intensity;

    /**
     * Constructor, construct the Light with an intensity
     *
     * @param _intensity the intensity of the light
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    /**
     * Getter for intensity
     * @return color
     */
    public Color getIntensity() {
        return new Color(_intensity);
    }
}