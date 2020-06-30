package primitives;

/**
 * Represents the material of shape
 */
public class Material {
    private final double _kD; // Diffuse attenuation
    private final double _kS; // Specular attenuation
    private final double _kT; // mekadem shkifut
    private final double _kR; // mekadem hishtakfut
    private final int _nShininess; // Shininess

    /**
     * Full parameters constructor for material
     *
     * @param _kD         Diffuse attenuation
     * @param _kS         Specular attenuation
     * @param _kT         Transparency attenuation
     * @param _kR         Reflection attenuation
     * @param _nShininess Shininess
     */
    public Material(double _kD, double _kS, int _nShininess, double _kT, double _kR) {
        this._kD = _kD;
        this._kS = _kS;
        this._kT = _kT;
        this._kR = _kR;
        this._nShininess = _nShininess;
    }

    /**
     * Constructor for material
     *
     * @param _kD         Diffuse attenuation
     * @param _kS         Specular attenuation
     * @param _nShininess Shininess
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this(_kD, _kS, _nShininess, 0, 0);
    }

    /**
     * Getter for the diffuse attenuation
     *
     * @return the diffuse attenuation
     */
    public double get_kD() {
        return _kD;
    }

    /**
     * Getter for the specular attenuation
     *
     * @return the specular attenuation
     */
    public double get_kS() {
        return _kS;
    }

    /**
     * Getter for the shininess
     *
     * @return the shininess
     */
    public int get_nShininess() {
        return _nShininess;
    }

    /**
     * Getter for the transparency attenuation
     *
     * @return the transparency attenuation
     */
    public double get_kT() {
        return _kT;
    }

    /**
     * Getter for the reflection attenuation
     *
     * @return the reflection attenuation
     */
    public double get_kR() {
        return _kR;
    }
}
