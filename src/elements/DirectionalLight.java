package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
   private Vector _direction;

    /**
     * constructor of directional light
     * @param _intensity intensity of the light
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
     * @return color of intensity
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    /**
     * return a vector from the light to the point
     * @param p point
     * @return vector from light to point
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }
}
