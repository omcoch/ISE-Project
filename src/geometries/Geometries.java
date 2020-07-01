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
    Point3D[] _bounds = null;

    /**
     * Constructor for creating bounding box
     *
     * @param min the minimum point of the bounding box
     * @param max the maximum point of the bounding box
     */
    public Geometries(Point3D min, Point3D max) {
        this();
        _bounds = new Point3D[2];
        // check if the min point is closer to the origin than the max point
        //assert (min,minPointBound(min,max));
        if(min.get_x().get()<=max.get_x().get()&&
        min.get_y().get()<=max.get_y().get()&&
        min.get_z().get()<=max.get_z().get()) {
            _bounds[0] = new Point3D(min);
            _bounds[1] = new Point3D(max);
        }
        else{
            _bounds[1] = new Point3D(min);
            _bounds[0] = new Point3D(max);
        }
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
     * find if the ray intersect the bounding box.
     * Computing the intersection of a ray with a AABB.
     * a straight line defined by using the following analytical equation: y = mx + b => m=(y-b)/x = t
     *
     * @param ray the ray
     * @return true if the ray intersect else return false
     */
    private boolean isIntersectBox(Ray ray) {

        Point3D startRay=ray.get_p0(), direction=ray.get_dir().get_head();
        double start_x=startRay.get_x().get(),start_y=startRay.get_y().get(),start_z=startRay.get_z().get(),
               dir_x=direction.get_x().get(),dir_y=direction.get_y().get(),dir_z=direction.get_z().get();

        double tmin,tmax,temp;
        if(dir_x!=0) {
            tmin = (_bounds[0].get_x().get() - start_x) / dir_x;
            tmax = (_bounds[1].get_x().get() - start_x) / dir_x;
            if (tmin > tmax) {
                temp = tmin;
                tmin = tmax;
                tmax = temp;
                //Util.swap(tmin, tmax);
            }
        }
        else{
            tmin=Double.POSITIVE_INFINITY;
            tmax=Double.POSITIVE_INFINITY;
        }

        double tymin,tymax;
        if(dir_y!=0) {
            tymin= (_bounds[0].get_y().get() - start_y) / dir_y;
            tymax = (_bounds[1].get_y().get() - start_y) / dir_y;
            if (tymin > tymax) {
                temp = tymin;
                tymin = tymax;
                tymax = temp;
                //Util.swap(tymin, tymax);
            }
        }
        else{
            tymin=Double.POSITIVE_INFINITY;
            tymax=Double.POSITIVE_INFINITY;
        }

        if ((tmin > tymax) || (tymin > tmax))
            return false;

        if (tymin > tmin)
            tmin = tymin;

        if (tymax < tmax)
            tmax = tymax;

        double tzmin,tzmax;
        if(dir_z!=0) {
            tzmin= (_bounds[0].get_z().get() - start_z) / dir_z;
            tzmax= (_bounds[1].get_z().get() - start_z) / dir_z;

            if (tzmin > tzmax) {
                temp = tzmin;
                tzmin = tzmax;
                tzmax = temp;
                //Util.swap(tzmin, tzmax);
            }
        }
        else{
            tzmin=Double.POSITIVE_INFINITY;
            tzmax=Double.POSITIVE_INFINITY;
        }

        if ((tmin > tzmax) || (tzmin > tmax) )
            return false;

        tmax = Math.min(tzmax,tmax);
        tmin = Math.max(tzmin,tmin);

        if (tmax < tmin) return false;

        return true;
        /*Point3D start = ray.get_p0();

        double start_X = start.get_x().get();
        double start_Y = start.get_y().get();
        double start_Z = start.get_z().get();

        Point3D direction = ray.get_dir().get_head();

        double direction_X = direction.get_x().get();
        double direction_Y = direction.get_y().get();
        double direction_Z = direction.get_z().get();

        double max_t_for_X;
        double min_t_for_X;

        //If the direction_X is negative then the _min_X give the maximal value
        if (direction_X < 0) {
            max_t_for_X = (_bounds[0].get_x().get() - start_X) / direction_X;
            // Check if the Intersectble is behind the camera
            if (max_t_for_X <= 0) return false;
            min_t_for_X = (_bounds[1].get_x().get() - start_X) / direction_X;
        }
        else if (direction_X > 0) {
            max_t_for_X = (_bounds[1].get_x().get() - start_X) / direction_X;
            if (max_t_for_X <= 0) return false;
            min_t_for_X = (_bounds[0].get_x().get() - start_X) / direction_X;
        }
        else {
            if (start_X >= _bounds[1].get_x().get() || start_X <= _bounds[0].get_x().get())
                return false;
            else{
                max_t_for_X = Double.POSITIVE_INFINITY;
                min_t_for_X = Double.NEGATIVE_INFINITY;
            }
        }

        double max_t_for_Y;
        double min_t_for_Y;

        if (direction_Y < 0) {
            max_t_for_Y = (_bounds[0].get_y().get() - start_Y) / direction_Y;
            if (max_t_for_Y <= 0) return false;
            min_t_for_Y = (_bounds[1].get_y().get() - start_Y) / direction_Y;
        }
        else if (direction_Y > 0) {
            max_t_for_Y = (_bounds[1].get_y().get() - start_Y) / direction_Y;
            if (max_t_for_Y <= 0) return false;
            min_t_for_Y = (_bounds[0].get_y().get() - start_Y) / direction_Y;
        }
        else {
            if (start_Y >= _bounds[1].get_y().get() || start_Y <= _bounds[0].get_y().get())
                return false;
            else{
                max_t_for_Y = Double.POSITIVE_INFINITY;
                min_t_for_Y = Double.NEGATIVE_INFINITY;
            }
        }

        //Check the maximal and the minimal value for t
        double temp_max = Math.min(max_t_for_Y,max_t_for_X);
        double temp_min = Math.max(min_t_for_Y,min_t_for_X);
        temp_min = Math.max(temp_min,0);

        if (temp_max < temp_min) return false;

        double max_t_for_Z;
        double min_t_for_Z;

        if (direction_Z < 0) {
            max_t_for_Z = (_bounds[0].get_z().get() - start_Z) / direction_Z;
            if (max_t_for_Z <= 0) return false;
            min_t_for_Z = (_bounds[1].get_z().get() - start_Z) / direction_Z;
        }
        else if (direction_Z > 0) {
            max_t_for_Z = (_bounds[1].get_z().get()) / direction_Z;
            if (max_t_for_Z <= 0) return false;
            min_t_for_Z = (_bounds[0].get_z().get() - start_Z) / direction_Z;
        }
        else {
            if (start_Z >= _bounds[1].get_z().get() || start_Z <= _bounds[0].get_z().get())
                return false;
            else{
                max_t_for_Z = Double.POSITIVE_INFINITY;
                min_t_for_Z = Double.NEGATIVE_INFINITY;
            }
        }

        temp_max = Math.min(max_t_for_Z,temp_max);
        temp_min = Math.max(min_t_for_Z,temp_min);

        if (temp_max < temp_min) return false;

        return true;*/
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
