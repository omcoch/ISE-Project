package primitives;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static primitives.Util.isZero;

/**
 * Class Beam presents a A group (list) of rays around a main ray,
 * expanding the lighting capabilities of the scene
 *
 * @author Ron & Omri
 */
public class Beam {
    public List<Ray> rayList;

    /**
     * Constructor for beam through a rectangle
     * Is used in anti aliasing
     *
     * @param ray        the main ray
     * @param pC         the center of the rectangle
     * @param height     the height of the rectangle
     * @param width      the width of the rectangle
     * @param amountRays the amount of rays to throw
     */
    public Beam(Ray ray, Point3D pC, double height, double width, int amountRays) {
        rayList = new LinkedList<Ray>();
        //adding the main ray to the list
        rayList.add(ray);
        double x, y;
        Ray r;
        for (int i = 1; i < amountRays; i++) {
            do {
                //create random ray in the boundary of the rectangle that doesn't exist in the list already
                y = (Math.random() * (height)) - (height / 2) + ray._p0._y._coord;//random number from -height/2 to height/2
                x = (Math.random() * (width)) - (width / 2) + ray._p0._x._coord;//random number from -width/2 to width/2
                r = constructRay(ray, pC, x, y, new Vector(1, 0, 0), new Vector(0, 1, 0));
            } while (x == 0 && y == 0 || rayList.contains(r));//if x and y equals to 0 the ray is the same as the main ray, this condition is faster than the check if it exists in the list
            rayList.add(r);
        }
    }

    /**
     * Constructor for beam through a circle
     * Is used in soft shadow
     *
     * @param ray        the main ray
     * @param pC         the center of the circle
     * @param radius     the radius of the circle
     * @param amountRays the amount of rays to throw
     */
    public Beam(Ray ray, Point3D pC, double radius, int amountRays) {
        rayList = new LinkedList<Ray>();
        //adding the main ray to the list
        rayList.add(ray);
        if (!Util.isZero(radius)) {
            double x, y;
            Ray r;
            Vector vx = ray.get_dir().getOrthogonal(),
                    vy = vx.crossProduct(ray.get_dir());//two orthogonal vectors
            for (int i = 1; i < amountRays; i++) {
                do {
                    //create random ray in the boundary of the circle that doesn't exist in the list already
                    double cosTeta = ThreadLocalRandom.current().nextDouble(-1.0D, 1.0D);
                    double sinTeta = Math.sqrt(1 - cosTeta * cosTeta); // trigonometric identity
                    double d = ThreadLocalRandom.current().nextDouble(-radius, radius);
                    // Convert polar coordinates to Cartesian ones
                    x = d * cosTeta;
                    y = d * sinTeta;
                    r = constructRay(ray, pC, x, y, vx, vy);
                } while (x == 0 && y == 0 || rayList.contains(r));//if x and y equals to 0 the ray is the same as the central ray, this condition is faster than to check if it exist in the list
                rayList.add(r);
            }
        }
    }


    /**
     * Construct a new ray for beam list
     * by moving the destination of the main ray
     *
     * @param ray the main ray
     * @param pC  the center of the container
     * @param x   the factor of x axis
     * @param y   the factor of y axis
     * @param vx  the x axis
     * @param vy  the y axis
     * @return new ray start at p0 and directed by moving the main ray slightly
     */
    private Ray constructRay(Ray ray, Point3D pC, double x, double y, Vector vx, Vector vy) {
        Point3D point = pC;
        // create a new destination point (for the new vector)
        if (!isZero(x)) point = point.add(vx.scale(x));
        if (!isZero(y)) point = point.add(vy.scale(y));
        return new Ray(ray._p0,
                point.subtract(ray._p0));
    }

}

