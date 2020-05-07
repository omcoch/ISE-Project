package elements;

import primitives.Color;

/**
 * Represents the ambient lighting of an image
 */
public class AmbientLight {

    private final Color _intensity;

    public AmbientLight(Color _intensity, double ka) {
        this._intensity = _intensity.scale(ka);
    }

    public Color getIntensity() {
        return _intensity;
    }
}
