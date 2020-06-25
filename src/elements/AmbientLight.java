package elements;

import primitives.Color;

/**
 * Represents the ambient lighting of an image
 */
public class AmbientLight extends Light {

    /**
     * Constructor
     *
     * @param _intensity the intensity of the light
     * @param ka         the mekadem hanhata
     */
    public AmbientLight(Color _intensity, double ka) {
        super(_intensity);
        this._intensity = _intensity.scale(ka);
    }

}
