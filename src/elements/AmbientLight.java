package elements;

import primitives.Color;

/**
 * Represents the ambient lighting of an image
 */
public class AmbientLight {

    private final Color _intensity;

    /**
     * Constructor
     * @param _intensity the intensity of the light
     * @param ka the mekadem hanhata
     */
    public AmbientLight(Color _intensity, double ka) {
        this._intensity = _intensity.scale(ka);
    }

    /**
     *  Returns the ambient light intensity
     * @return the object's intensity
     */
    public Color getIntensity() {
        return _intensity;
    }
}
