package primitives;

public class Material {
    private double _kD;
    private double _kS;
    private int _nShininess;

    /**
     * constructor for material
     * @param _kD
     * @param _kS
     * @param _nShininess
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }

    /**
     *
     * @return
     */
    public double get_kD() {
        return _kD;
    }

    /**
     *
     * @return
     */
    public double get_kS() {
        return _kS;
    }

    /**
     *
     * @return
     */
    public int get_nShininess() {
        return _nShininess;
    }
}
