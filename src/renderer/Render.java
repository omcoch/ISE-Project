package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import geometries.Intersectable.*;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * Class that render a scene to an image
 */
public class Render {
    private ImageWriter _imageWriter;
    private Scene _scene;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


    /**
     * Constructor
     * @param imageWriter
     * @param scene
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        _imageWriter = imageWriter;
        _scene = scene;
    }

    /**
     * Create image buffer according to the geometries that are in the scene.
     */
    public void renderImage() {

        Camera camera = _scene.get_camera();
        Intersectable geometries = _scene.get_geometries();
        java.awt.Color background = _scene.get_background().getColor();
        double distance = _scene.get_distance();
        int Nx = _imageWriter.getNx(); //columns
        int Ny = _imageWriter.getNy(); //rows
        int width = (int) _imageWriter.getWidth();
        int height = (int) _imageWriter.getHeight();

        //pixels grid - i is pixel row number and j is pixel column number
        for (int i = 0; i < Ny; ++i) {
            for (int j = 0; j < Nx; ++j) {
                // construct a ray for each pixel:
                Ray ray = camera.constructRayThroughPixel(Nx, Ny, j, i, distance, width, height);

                // find the intersection points for each geometry with the ray:
                List<Intersectable.GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                if (intersectionPoints == null) {
                    // paints blank pixels with the background color
                    _imageWriter.writePixel(j, i, background);
                }
                else { // paint the pixel that the geometry is passing through it
                    GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(j, i, calcColor(closestPoint,ray).getColor());
                }
            }
        }
    }


    /**
     * Returns the point that is closest to the camera on a ray
     * @param intersectionPoints
     * @return the closet point
     */
    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints) {
        GeoPoint result = null;
        double min = Double.MAX_VALUE;

        Point3D p0 = this._scene.get_camera().getLocation();

        for (GeoPoint geo : intersectionPoints) {
            Point3D pt = geo.point;
            double distance = p0.distance(pt);
            if (distance < min) {
                min = distance;
                result = geo;
            }
        }
        return result;
    }

    /**
     *  Calculate the color of a point
     * @param intersection the lighting point
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection,Ray ray) {
        Color color=calcColor(intersection,ray,MAX_CALC_COLOR_LEVEL,1);
        color=color.add(_scene.get_ambientLight().getIntensity());
        return color;
    }

    private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
        Color color =geoPoint.geometry.get_emission();
        Vector v = geoPoint.point.subtract(_scene.get_camera().getLocation()).normalize();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Material material =geoPoint.geometry.get_material();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        for (LightSource lightSource : _scene.get_lights()) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl=alignZero(n.dotProduct(l)),nv=alignZero(n.dotProduct(v));
            if (sign(nl) == sign(nv) && nv!=0 && nl!=0) {
                double ktr= transparency(lightSource, l, n, geoPoint);
                if (ktr * k > MIN_CALC_COLOR_K) {
                Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                color = color.add(calcDiffusive(kd, n, l, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        if(level==1) return Color.BLACK;
        double kr = geoPoint.geometry.get_material().get_kR(), kkr = k * kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(n, geoPoint.point, inRay);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay,
                        level - 1, kkr).scale(kr));
        }
        double kt = geoPoint.geometry.get_material().get_kT(), kkt = k * kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(geoPoint.point, inRay,n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay,
                        level-1, kkt).scale(kt));
        }
        return color;
    }



    /**
     * Printing the grid with a fixed interval between lines.
     *
     * @param interval The interval between the lines.
     */
    public void printGrid(int interval, java.awt.Color color) {
        double rows = this._imageWriter.getNy();
        double columns = _imageWriter.getNx();

        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                if (column % interval == 0 || row % interval == 0) {
                    _imageWriter.writePixel(column, row, color);
                }
            }
        }
    }

    /**
     * Do the image writing
     */
    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * return the sign of the number
     * @param val number
     * @return sign of the number
     */
    private boolean sign(double val) {
        return (val > 0d);
    }

    /**
     * Calculate Specular component of light reflection.
     *
     * @param ks         specular component coef
     * @param l          direction from light to point
     * @param n          normal to surface at the point
     * @param v          direction from point of view to point
     * @param nShininess shininess level
     * @param ip         light intensity at the point
     * @return specular component light effect at the point

     * <p>
     * Finally, the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
     * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * the surface.
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color ip) {
        double p = nShininess;
        double nl= alignZero(n.dotProduct(l));
        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -alignZero(R.dotProduct(v));
        if (minusVR <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        return ip.scale(ks * Math.pow(minusVR, p));
    }

    /**
     * Calculate Diffusive component of light reflection.
     *
     * @param kd diffusive component coef
     * @param n normal to surface at the point
     * @param l direction from light to point
     * @param ip light intensity at the point
     * @return diffusive component of light reflection
     * @author Dan Zilberstein
     * <p>
     * The diffuse component is that dot product n•L. It approximates light, originally
     * from light source L, reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossy
     * surface is paper. In general, you'll also want this to have a non-gray color value, so this term would in general
     * be a color defined as: [rd,gd,bd](n•L)
     */
    private Color calcDiffusive(double kd, Vector n,Vector l, Color ip) {
        double nl= alignZero(n.dotProduct(l));
        if (nl < 0) nl = -nl;
        return ip.scale(nl * kd);
    }

    /**
     *
     * @param l
     * @param n
     * @param gpoint
     * @return
     */
    private boolean unshaded(LightSource light,Vector l, Vector n, GeoPoint gpoint){
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gpoint.point, lightDirection,n);
        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(gpoint.point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gpoint.point.distance(gp.point)-lightDistance) <= 0
                && gp.geometry.get_material().get_kT()==0)
                return false;
        }
        return true;
    }

    /**
     *
     * @param ls
     * @param l
     * @param n
     * @param geopoint
     * @return
     */
    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geopoint){
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);
        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(lightRay);
        if (intersections==null) return 1.0;
        double lightDistance = ls.getDistance(geopoint.point);
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
                ktr *= gp.geometry.get_material().get_kT();
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }

    /**
     *
     * @param n
     * @param p
     * @param inRay
     * @return 𝒗 −𝟐∙(𝒗∙𝒏)∙𝒏
     */
    Ray constructReflectedRay (Vector n,Point3D p, Ray inRay) {
        Vector v=inRay.get_dir();
        double nv=n.dotProduct(v);
        if(nv==0)
            return null;
        return new Ray(p,v.subtract(n.scale(2*nv)),n);
    }

    /**
     *
     * @param point
     * @param inRay
     * @param n
     * @return
     */
    private Ray constructRefractedRay(Point3D point, Ray inRay,Vector n) {
        return new Ray(point, inRay.get_dir(), n);
    }

    /**
     * Find intersections of a ray with the scene geometries and get the
     * intersection point that is closest to the ray head. If there are no
     * intersections, null will be returned.
     *
     * @param ray intersecting the scene
     * @return the closest point
     */
    private GeoPoint findClosestIntersection(Ray ray) {

        if (ray == null) {
            return null;
        }

        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.get_p0();

        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(ray);
        if (intersections == null)
            return null;

        for (GeoPoint geoPoint : intersections) {
            double distance = ray_p0.distance(geoPoint.point);
            if (distance < closestDistance) {
                closestPoint = geoPoint;
                closestDistance = distance;
            }
        }
        return closestPoint;
    }
}
