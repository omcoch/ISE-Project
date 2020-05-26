package primitives;

/**
 * Represents the material of shape
 */
public class Material {
    private double _kD;
    private double _kS;
    private int _nShininess;

    /**
     * constructor for material
     * @param _kD Diffuse attenuation
     * @param _kS Specular attenuation
     * @param _nShininess shininess
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }

    /**
     * getter for the diffuse attenuation
     * @return the diffuse attenuation
     */
    public double get_kD() {
        return _kD;
    }

    /**
     * getter for the specular attenuation
     * @return the specular attenuation
     */
    public double get_kS() {
        return _kS;
    }

    /**
     * getter for the shininess
     * @return the shininess
     */
    public int get_nShininess() {
        return _nShininess;
    }
}
