package geometries;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a group of geometry components,
 * implements the composite design pattern
 */
public class Geometries implements Intersectable {
    private List<Intersectable> _geometries;

    //an array of 2 points that are the bounds of the box
    double[] _minbounds = null;
    double[] _maxbounds = null;

    /**
     * Constructor for creating bounding box
     *
     * @param min the minimum point of the bounding box
     * @param max the maximum point of the bounding box
     */
    public Geometries(Point3D min, Point3D max) {
        this();
        _minbounds = new double[3];
        _maxbounds = new double[3];
        // check if the min point is closer to the origin than the max point
        //assert (min,minPointBound(min,max));
        _minbounds[0] = Math.min(min.get_x().get(), max.get_x().get());
        _minbounds[1] = Math.min(min.get_y().get(), max.get_y().get());
        _minbounds[2] = Math.min(min.get_z().get(), max.get_z().get());
        _maxbounds[0] = Math.max(min.get_x().get(), max.get_x().get());
        _maxbounds[1] = Math.max(min.get_y().get(), max.get_y().get());
        _maxbounds[2] = Math.max(min.get_z().get(), max.get_z().get());
    }

    /**
     * Default Constructor
     */
    public Geometries() {
        this._geometries = new LinkedList();
    }

    /**
     * Constructor
     *
     * @param geometries collection of geometries
     */
    public Geometries(Intersectable... geometries) {
        this._geometries = new LinkedList();
        this._geometries.addAll(List.of(geometries));
    }

    /**
     * Add geometries into the list
     *
     * @param geometries collection of geometries
     */
    public void add(Intersectable... geometries) {
        this._geometries.addAll(List.of(geometries));
    }

    /**
     * getter for bounds
     *
     * @return the bounds array
     */
    @Override
    public Point3D[] getBounds() {
        return new Point3D[]{new Point3D(_minbounds[0], _minbounds[1], _minbounds[2])
                , new Point3D(_maxbounds[0], _maxbounds[1], _maxbounds[2])};
    }

    /**
     * Calculate intersection points of ray with the geometries
     *
     * @param ray ray pointing toward a Geometry
     * @return List<Point3D> return list of the intersection points, null if not exists
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray) {
        // if the bounding box feature is not activated or
        // the ray doesn't intersect the box => No further calculation is needed
        if (_minbounds != null && _maxbounds != null && !isIntersectBox(ray)) return null;

        List<GeoPoint> intersections = null;
        for (Intersectable geo : _geometries) {
            List<GeoPoint> tempIntersections = geo.findIntersections(ray);
            if (tempIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(tempIntersections);
            }
        }
        return intersections;
    }

    /**
     * find if the ray intersect the bounding box.
     * Computing the intersection of a ray with a AABB.
     * a straight line defined by using the following analytical equation: y = mx + b => m=(y-b)/x = t
     * credit for Michael Shahor
     *
     * @param ray the ray
     * @return true if the ray intersect else return false
     */
    private boolean isIntersectBox(Ray ray) {
        Point3D head = ray.get_p0();
        Point3D dir = ray.get_dir().get_head();

        double[] dirRay = {dir.get_x().get(), dir.get_y().get(), dir.get_z().get()},
                tailRay = {head.get_x().get(), head.get_y().get(), head.get_z().get()};

        double tmin = Double.NEGATIVE_INFINITY, tmax = Double.POSITIVE_INFINITY;
        for (int i = 0; i < 3; i++) {
            if (dirRay[i] != 0.0) {
                double t1 = (_minbounds[i] - tailRay[i]) / dirRay[i];
                double t2 = (_maxbounds[i] - tailRay[i]) / dirRay[i];

                tmin = Math.max(tmin, Math.min(t1, t2));
                tmax = Math.min(tmax, Math.max(t1, t2));
                if (tmax <= 0)
                    return false;
            } else if (tailRay[i] < _minbounds[i] || tailRay[i] > _maxbounds[i] || tmax < 0.0)
                return false;
        }

        return (tmax >= tmin);
    }

    /**
     * Construct BVH like binary-tree by using Geometries class as a composite design pattern.
     * Create bound volume for each two close geometries by binding them into one Geometries.
     */
    public void constructBVH() {
        boundGeometries(); // build a box for each geometry
        Geometries c = this;
        double best;
        while (c._geometries.size() > 1) {
            Geometries left = null, right = null;
            best = Double.POSITIVE_INFINITY;
            for (int i = 0; i < c._geometries.size(); i++) {
                Geometries geo1 = (Geometries) c._geometries.get(i);
                for (int j = i + 1; j < c._geometries.size(); j++) {
                    Geometries geo2 = (Geometries) c._geometries.get(j);
                    if (!geo1.equals(geo2)) {
                        double distance = geo1.distance(geo2);
                        if (distance < best) {
                            best = distance;
                            left = geo1;
                            right = geo2;
                        }//endif
                    }//endif
                }//end for
            }//end for
            // after finding the two closet geometries (left and right) - binding them into one Geometries object
            //left._bounds = left._geometries.get(0).getBounds();
            //right._bounds = right._geometries.get(0).getBounds();
            //add the new combined bound to the list
            Geometries c1 = new Geometries(minPointBound(left, right), maxPointBound(left, right));
            c1.add(left, right);
            c._geometries.remove(left);
            c._geometries.remove(right);
            c.add(c1);
        }//end while
        _geometries = List.of(c._geometries.get(0));
    }

    /**
     * Calculate the maximum point of bound for two Geometries objects
     *
     * @param left  one of the Geometries
     * @param right the second Geometries
     * @return the maximum point of the combined Geometries
     */
    private Point3D maxPointBound(Geometries left, Geometries right) {
        if (left._maxbounds == null)
            return new Point3D(right._maxbounds[0], right._maxbounds[1], right._maxbounds[2]);
        if (right._maxbounds == null)
            return new Point3D(left._maxbounds[0], left._maxbounds[1], left._maxbounds[2]);
        double x = Math.max(left._maxbounds[0], right._maxbounds[0]),
                y = Math.max(left._maxbounds[1], right._maxbounds[1]),
                z = Math.max(left._maxbounds[2], right._maxbounds[2]);
        return new Point3D(x, y, z);
    }

    /**
     * Calculate the minimum point of bound for two Geometries objects
     *
     * @param left  one of the Geometries
     * @param right the second Geometries
     * @return the minimum point of the combined Geometries
     */
    private Point3D minPointBound(Geometries left, Geometries right) {
        if (left._minbounds == null)
            return new Point3D(right._minbounds[0], right._minbounds[1], right._minbounds[2]);
        if (right._minbounds == null)
            return new Point3D(left._minbounds[0], left._minbounds[1], left._minbounds[2]);
        double x = Math.min(left._minbounds[0], right._minbounds[0]),
                y = Math.min(left._minbounds[1], right._minbounds[1]),
                z = Math.min(left._minbounds[2], right._minbounds[2]);
        return new Point3D(x, y, z);
    }

    /**
     * Calculate the distance between two Geometries.
     * Use for find the two closet Geometries in the scene.
     *
     * this- first Geometries object
     * @param geo2 second Geometries object
     * @return the distance between two Geometries.
     */
    private double distance(Intersectable geo2) {
        double[] min2 = ((Geometries) geo2)._minbounds, max2 = ((Geometries) geo2)._maxbounds;
        if (this._minbounds == null || this._maxbounds == null || min2 == null || max2 == null) // in case of infinite geometry
            return Double.POSITIVE_INFINITY - 1;
        // find the center point of each box and calculate the distance between the two centers.
        Point3D pc1 = new Point3D((this._maxbounds[0] + this._minbounds[0]) / 2, (this._maxbounds[1] + this._minbounds[1]) / 2, (this._maxbounds[2] + this._minbounds[2]) / 2);
        Point3D pc2 = new Point3D((max2[0] + min2[0]) / 2, (max2[1] + min2[1]) / 2, (max2[2] + min2[2]) / 2);
        return pc1.distance(pc2);
    }

    /**
     * Create bound boxes for each Geometry separately.
     */
    private void boundGeometries() {
        Point3D[] p;
        List<Intersectable> l = new LinkedList<>();
        for (Intersectable i : _geometries) {
            Geometries g = new Geometries(i);
            p = i.getBounds();
            g._minbounds = new double[]{p[0].get_x().get(), p[0].get_y().get(), p[0].get_z().get()};
            g._maxbounds = new double[]{p[1].get_x().get(), p[1].get_y().get(), p[1].get_z().get()};
            l.add(g);
        }
        _geometries = l;
    }
}
