package primitives;

/**
 * Represents the material of shape
 */
public class Material {
    private double _kD;
    private double _kS;
    private double _kT; // mekadem shkifut
    private double _kR; // mekadem hishtakfut
    private int _nShininess;

    /**
     *
     * full constructor for material
     * @param _kD Diffuse attenuation
     * @param _kS Specular attenuation
     * @param _kT Transparency attenuation
     * @param _kR Reflection attenuation
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
     * constructor for material
     * @param _kD Diffuse attenuation
     * @param _kS Specular attenuation
     * @param _nShininess Shininess
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this(_kD,_kS,_nShininess,0,0);
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

    /**
     * getter for the transparency attenuation
     * @return the transparency attenuation
     */
    public double get_kT() {
        return _kT;
    }

    /**
     * getter for the reflection attenuation
     * @return the reflection attenuation
     */
    public double get_kR() {
        return _kR;
    }
}
