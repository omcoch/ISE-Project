package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import geometries.Intersectable.*;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * Class that render a scene to an image
 */
public class Render {
    private ImageWriter _imageWriter;
    private Scene _scene;

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
                    _imageWriter.writePixel(j, i, calcColor(closestPoint).getColor());
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
    private Color calcColor(GeoPoint intersection) {
        Color color = _scene.get_ambientLight().getIntensity()
                .add(intersection.geometry.get_emission());
        Vector v = intersection.point.subtract(_scene.get_camera().getLocation()).normalize();
        Vector n = intersection.geometry.getNormal(intersection.point);
        Material material =intersection.geometry.get_material();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        for (LightSource lightSource : _scene.get_lights()) {
            Vector l = lightSource.getL(intersection.point);
            if (sign(n.dotProduct(l)) == sign(n.dotProduct(v))) {
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
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
        double nl=Util.alignZero(n.dotProduct(l));
        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!
        double minusVR = -Util.alignZero(R.dotProduct(v));
        if (minusVR <= 0) {
            return Color.BLACK; // view from direction opposite to r vector
        }
        return ip.scale(ks * Math.pow(minusVR, p));
    }

    /**
     * Calculate Diffusive component of light reflection.
     *
     * @param kd diffusive component coef
     * @param n
     * @param l
     * @param ip light intensity at the point
     * @return diffusive component of light reflection
     * @author Dan Zilberstein
     * <p>
     * The diffuse component is that dot product n•L that we discussed in class. It approximates light, originally
     * from light source L, reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossy
     * surface is paper. In general, you'll also want this to have a non-gray color value, so this term would in general
     * be a color defined as: [rd,gd,bd](n•L)
     */
    private Color calcDiffusive(double kd, Vector n,Vector l, Color ip) {
        double nl=Util.alignZero(n.dotProduct(l));
        if (nl < 0) nl = -nl;
        return ip.scale(nl * kd);
    }
}
