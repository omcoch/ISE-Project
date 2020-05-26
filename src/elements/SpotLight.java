package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

public class SpotLight extends PointLight {
    private Vector _direction;

    /**
     * constructor of the spot light
     * @param _intensity intensity of the light
     * @param _position position of the light
     * @param _kC Constant attenuation
     * @param _kL Linear attenuation
     * @param _kQ Quadratic attenuation
     * @param _direction the direction of the light
     */
    public SpotLight(Color _intensity, Point3D _position, double _kC, double _kL, double _kQ, Vector _direction) {
        super(_intensity, _position, _kC, _kL, _kQ);
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
        double angle = _direction.dotProduct(getL(p));

        if (Util.isZero(angle))
            return Color.BLACK;

        return super.getIntensity(p).scale(Math.max(0, angle));
    }
}
