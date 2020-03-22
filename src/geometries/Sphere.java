package geometries;

import primitives.Point3D;

public class Sphere extends RadialGeometry  {
    Point3D _center;

    public Sphere(double radius, Point3D center) {
        super(radius);
        _center = new Point3D(center);
    }
}
