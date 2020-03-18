package geometries;
import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {
    Point3D _p;
    primitives.Vector _normal;

    public Plane(Point3D _p, Vector _normal) {
        this._p = _p;
        this._normal = _normal;
    }

    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _p=new Point3D(p1.get_x(),p1.get_y(),p1.get_z());
        _normal=new Vector(Point3D.ZERO); //TODO:זה אסור לפי ההוראות
    }


    @Override
    public Vector getNormal(Point3D p) {
        return _normal;
    }

    public Vector getNormal() {
        return getNormal(_p)
    }
}
