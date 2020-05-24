package elements;

import primitives.Color;
import primitives.Point3D;

public abstract class Light {

    protected Color _intensity;

    /**
     * Constructor
     * construct the Light with an intensity
     * @param _intensity
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    /**
     * Getter for intensity
     * @return
     */
    public Color getIntensity() {
        return new Color(_intensity);
    }
}