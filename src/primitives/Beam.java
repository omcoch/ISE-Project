package primitives;

import java.util.LinkedList;
import java.util.List;

public class Beam {
    public List<Ray> rayList;


    public Beam(Ray ray,Point3D pC, double height, double width,int amountRays) {
        if (amountRays == 1)
            rayList = List.of(ray);
        else {
            rayList = constructBeamAroundRay(ray,pC, height, width,amountRays);
        }
    }

    private List<Ray> constructBeamAroundRay(Ray ray,Point3D pC, double height, double width,int amountRays) {
        List<Ray> rays = new LinkedList<Ray>();
        rays.add(ray);
        Ray r;
        double x, y;
        Vector v = ray.get_dir();
        for (int i = 1; i < amountRays; i++) {
            do {//create random ray in the boundary of the rectangle that doesn't exist in the list already
                y = (Math.random() * (height)) - (height / 2)+ray._p0._y._coord;//random number from -height/2 to height/2
                x = (Math.random() * (width)) - (width / 2)+ray._p0._x._coord;//random number from -width/2 to width/2
                r = new Ray(new Point3D(x,y,ray._p0._z._coord),
                        pC.subtract(ray._p0)//moving the direction of the ray by x and y
                );
            } while (x == 0 && y == 0 || rays.contains(r));//if x and y equals to 0 the ray is the same as the central ray, this condition is faster than to check if it exist in the list
            rays.add(r);
        }
        return rays;
    }
}
