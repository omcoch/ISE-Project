package primitives;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.sqrt;
import static primitives.Util.isZero;

public class Beam {
    public List<Ray> rayList;

    /**
     * constructor for beam through rectangle
     * @param ray the main ray
     * @param pC center of the rectangle
     * @param height the height of the rectangle
     * @param width the width of the rectangle
     * @param amountRays the amount of rays int the beam
     */
    public Beam(Ray ray,Point3D pC, double height, double width,int amountRays) {
        rayList= new LinkedList<Ray>();
        //adding the main ray to the list
        rayList.add(ray);
        double x,y;
        Ray r;
        for (int i = 1; i < amountRays; i++) {
            do {//create random ray in the boundary of the rectangle that doesn't exist in the list already
                y = (Math.random() * (height)) - (height / 2)+ray._p0._y._coord;//random number from -height/2 to height/2
                x = (Math.random() * (width)) - (width / 2)+ray._p0._x._coord;//random number from -width/2 to width/2
                r = constructRay(ray,pC, x, y,new Vector(1,0,0),new Vector(0,1,0),amountRays);
            } while (x == 0 && y == 0 || rayList.contains(r));//if x and y equals to 0 the ray is the same as the central ray, this condition is faster than to check if it exist in the list
            rayList.add(r);
        }
    }

    /**
     *
     * @param ray
     * @param pC
     * @param radius
     * @param amountRays
     */
    public Beam(Ray ray,Point3D pC,double radius,int amountRays) {
        rayList= new LinkedList<Ray>();
        //adding the main ray to the list
        rayList.add(ray);
        double x,y;
        Ray r;
        Point3D p=pC.add(new Vector(1,0,0).scale(radius));
        Vector vx=p.subtract(pC).normalized(),vy=new Vector(vx.get_head().get_y()._coord*-1,vx.get_head().get_x()._coord,0);
        for (int i = 1; i < amountRays; i++){
            do {//create random ray in the boundary of the rectangle that doesn't exist in the list already
                double cosTeta = Math.random()*2-1;
                double sinTeta = sqrt(1 - cosTeta*cosTeta); // זהות בסיסית בטריגו
                double d = Math.random()*2*radius-radius;
                // Convert polar coordinates to Cartesian ones
                x = d*cosTeta;
                y = d*sinTeta;
                r=constructRay(ray,pC,x,y,vx,vy,amountRays);
            } while (x == 0 && y == 0 || rayList.contains(r));//if x and y equals to 0 the ray is the same as the central ray, this condition is faster than to check if it exist in the list
            rayList.add(r);
        }
    }

    /**
     *
     * @param ray
     * @param pC
     * @param x
     * @param y
     * @param vx
     * @param vy
     * @param amountRays
     * @return
     */
    private Ray constructRay(Ray ray,Point3D pC, double x, double y,Vector vx,Vector vy,int amountRays) {
        Point3D point=pC;
        Vector v = ray.get_dir();
        if (!isZero(x)) point = point.add(vx.scale(x));
        if (!isZero(y)) point = point.add(vy.scale(y));
        return new Ray(ray._p0,
                point.subtract(ray._p0)
        );
    }

}

