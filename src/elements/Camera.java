package elements;

import primitives.*;
import static primitives.Util.*;

/**
 * Represents camera in 3D, its location and simulate the rays out from
 */
public class Camera {
    private Point3D location;
    private Vector Vto;
    private Vector Vup;
    private Vector Vright;

    /**
     * Constructor
     * @param location center point of camera
     * @param vto toward vector
     * @param vup up vector
     */
    public Camera(Point3D location, Vector vto, Vector vup) {
        if (Util.isZero(vto.dotProduct(vup))) {
            this.location = new Point3D(location);
            Vto = vto.normalized();
            Vup = vup.normalized();
            Vright = Vto.crossProduct(Vup);
        } else
            throw new IllegalArgumentException("the vectors need to be orthogonal");
    }

    /**
     * location getter
     *
     * @return the location point
     */
    public Point3D getLocation() {
        return new Point3D(location);
    }

    /**
     * Vto getter
     *
     * @return the Vto vector
     */
    public Vector getVto() {
        return new Vector(Vto);
    }

    /**
     * Vup getter
     *
     * @return the Vup vector
     */
    public Vector getVup() {
        return new Vector(Vup);
    }

    /**
     * Vright getter
     *
     * @return the Vright vector
     */
    public Vector getVright() {
        return new Vector(Vright);
    }

    /**
     * Create Ray that start in the camera's location
     * and go through the given Pixel
     * @param nX number of pixels on X axis
     * @param nY number of pixels on Y axis
     * @param j index for x
     * @param i index for y
     * @param screenDistance distance between the camera and view plane
     * @param screenWidth view plane's width
     * @param screenHeight view plane's height
     * @return Ray the ray from camera through pixel (j,i)
     */
    public Ray constructRayThroughPixel(int nX, int nY,
                                        int j, int i, double screenDistance,
                                        double screenWidth, double screenHeight) {
        if (isZero(screenDistance))
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        Point3D Pc = location.add(Vto.scale(screenDistance));

        double Ry = screenHeight/nY;
        double Rx = screenWidth/nX;

        double yi =  ((i - nY/2d)*Ry + Ry/2d);
        double xj=   ((j - nX/2d)*Rx + Rx/2d);

        Point3D Pij = Pc;

        if (! isZero(xj))
        {
            Pij = Pij.add(Vright.scale(xj));
        }
        if (! isZero(yi))
        {
            Pij = Pij.add(Vup.scale(-yi));
        }

        Vector Vij = Pij.subtract(location);

        return new Ray(location,Vij);
    }

}
