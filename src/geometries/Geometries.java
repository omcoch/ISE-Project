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


    Point3D[] _bounds = null;

    /**
     * Constructor for creating bounding box
     *
     * @param min the minimum point of the bounding box
     * @param max the maximum point of the bounding box
     */
    public Geometries(Point3D min, Point3D max) {
        this();
        // check if the min point is closer to the origin than the max point
        //assert (min,minPointBound(min,max));
        _bounds = new Point3D[2];
        _bounds[0] = new Point3D(min);
        _bounds[1] = new Point3D(max);
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
     * @return the bounds array
     */
    @Override
    public Point3D[] getBounds() {
        return _bounds;
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
        if (_bounds != null && !isIntersectBox(ray)) return null;

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
     * find if the ray intersect the bounding box
     *
     * @param ray the ray
     * @return true if the ray intersect else return false
     */
    private boolean isIntersectBox(Ray ray) {
        double tmin = (_bounds[0].get_x().get() - ray.get_p0().get_x().get()) / ray.get_dir().get_head().get_x().get();
        double tmax = (_bounds[1].get_x().get() - ray.get_p0().get_x().get()) / ray.get_dir().get_head().get_x().get();
        double temp;
        if (tmin > tmax) {
            temp = tmin;
            tmin = tmax;
            tmax = temp;
            //Util.swap(tmin, tmax);
        }

        double tymin = (_bounds[0].get_y().get() - ray.get_p0().get_y().get()) / ray.get_dir().get_head().get_y().get();
        double tymax = (_bounds[1].get_y().get() - ray.get_p0().get_y().get()) / ray.get_dir().get_head().get_y().get();

        if (tymin > tymax) {
            temp = tymin;
            tymin = tymax;
            tymax = temp;
            //Util.swap(tymin, tymax);
        }

        if ((tmin > tymax) || (tymin > tmax))
            return false;

        if (tymin > tmin)
            tmin = tymin;

        if (tymax < tmax)
            tmax = tymax;

        double tzmin = (_bounds[0].get_z().get() - ray.get_p0().get_z().get()) / ray.get_dir().get_head().get_z().get();
        double tzmax = (_bounds[1].get_z().get() - ray.get_p0().get_z().get()) / ray.get_dir().get_head().get_z().get();

        if (tzmin > tzmax) {
            temp = tzmin;
            tzmin = tzmax;
            tzmax = temp;
            //Util.swap(tzmin, tzmax);
        }

        if ((tmin > tzmax) || (tzmin > tmax) )
            return false;

        if(tmax<=0)
            return false;
        return true;
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
                Intersectable geo1 = c._geometries.get(i);
                for (int j = i + 1; j < c._geometries.size(); j++) {
                    Intersectable geo2 = c._geometries.get(j);
                    if (!geo1.equals(geo2)) {
                        double distance = distance(geo1, geo2);
                        if (distance < best) {
                            best = distance;
                            left = new Geometries(geo1);
                            right = new Geometries(geo2);
                        }//endif
                    }//endif
                }//end for
            }//end for
            // after finding the two closet geometries (left and right) - binding them into one Geometries object
            left._bounds = left._geometries.get(0).getBounds();
            right._bounds = right._geometries.get(0).getBounds();
            //add the new combined bound to the list
            Geometries c1 = new Geometries(minPointBound(left, right), maxPointBound(left, right));
            c1.add(left, right);
            c._geometries.remove(left._geometries.get(0));
            c._geometries.remove(right._geometries.get(0));
            c.add(c1);
        }//end while
        _geometries = List.of(c._geometries.get(0));
    }

    /**
     * Calculate the maximum point of bound for two Geometries objects
     *
     * @param left one of the Geometries
     * @param right the second Geometries
     * @return the maximum point of the combined Geometries
     */
    private Point3D maxPointBound(Geometries left, Geometries right) {
        if (left._bounds == null)
            return right._bounds[1];
        if (right._bounds == null)
            return left._bounds[1];
        double x = left._bounds[1].get_x().get(), y = left._bounds[1].get_y().get(), z = left._bounds[1].get_z().get();
        Point3D p = right._bounds[1];
        if (p.get_x().get() > x)
            x = p.get_x().get();
        if (p.get_y().get() > y)
            y = p.get_y().get();
        if (p.get_z().get() > z)
            z = p.get_z().get();
        return new Point3D(x, y, z);
    }

    /**
     * Calculate the minimum point of bound for two Geometries objects
     *
     * @param left one of the Geometries
     * @param right the second Geometries
     * @return the minimum point of the combined Geometries
     */
    private Point3D minPointBound(Geometries left, Geometries right) {
        if (left._bounds == null)
            return right._bounds[0];
        if (right._bounds == null)
            return left._bounds[0];
        double x = left._bounds[0].get_x().get(), y = left._bounds[0].get_y().get(), z = left._bounds[0].get_z().get();
        Point3D p = right._bounds[0];
        if (p.get_x().get() < x)
            x = p.get_x().get();
        if (p.get_y().get() < y)
            y = p.get_y().get();
        if (p.get_z().get() < z)
            z = p.get_z().get();
        return new Point3D(x, y, z);
    }

    /**
     * Calculate the distance between two Geometries.
     * Use for find the two closet Geometries in the scene.
     *
     * @param geo1 first Geometries object
     * @param geo2 second Geometries object
     * @return the distance between two Geometries.
     */
    private double distance(Intersectable geo1, Intersectable geo2) {
        if(geo1.getBounds()==null||geo2.getBounds()==null) // in case of infinite geometry
            return Double.POSITIVE_INFINITY-1;
        Point3D max1 = ((Geometries) geo1)._bounds[1], min1 = ((Geometries) geo1)._bounds[0];
        Point3D max2 = ((Geometries) geo2)._bounds[1], min2 = ((Geometries) geo2)._bounds[0];
        // find the center point of each box and calculate the distance between the two centers.
        Point3D pc1 = new Point3D((max1.get_x().get() + min1.get_x().get()) / 2, (max1.get_y().get() + min1.get_y().get()) / 2, (max1.get_z().get() + min1.get_z().get()) / 2);
        Point3D pc2 = new Point3D((max2.get_x().get() + min2.get_x().get()) / 2, (max2.get_y().get() + min2.get_y().get()) / 2, (max2.get_z().get() + min2.get_z().get()) / 2);
        return pc1.distance(pc2);
    }

    /**
     * Create bound boxes for each Geometry separately.
     */
    private void boundGeometries() {
        flatten();
        List<Intersectable> l = new LinkedList<>();
        for (Intersectable i : _geometries) {
            Geometries g = new Geometries(i);
            g._bounds = i.getBounds();
            l.add(g);
        }
        _geometries = l;
    }

    public void flatten() {
        Geometries new_geometries = new Geometries(_geometries.toArray(new Intersectable[_geometries.size()]));
        _geometries.clear();
        flatten(new_geometries);

    }

    /**
     * recursive func to flatten the geometries list
     * @param new_geometries current geometries
     */
    private void flatten(Geometries new_geometries) {
        for (Intersectable intersectable : new_geometries._geometries) {
            if (intersectable instanceof Geometry)
                _geometries.add(intersectable);
            else
                flatten((Geometries) intersectable);
        }
    }
}
