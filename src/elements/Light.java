package elements;

import primitives.Color;
import primitives.Point3D;

public abstract class Light {
    /**
     *
     */
    protected Color _intensity;

    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    public Color getIntensity() {
        return new Color(_intensity);
    }
}