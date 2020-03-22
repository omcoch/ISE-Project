package geometries;

import primitives.Ray;

public class Tube extends RadialGeometry {
    Ray _ray;

    public Tube(double _radius, Ray _ray) {
        super(_radius);
        this._ray = _ray;
    }

    public Tube(RadialGeometry rd, Ray _ray) {
        super(rd);
        this._ray = _ray;
    }
}
