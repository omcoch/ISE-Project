package renderer;

import elements.Camera;
import geometries.Intersectable;
import geometries.Intersectable.*;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
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
        Color color = _scene.get_ambientLight().getIntensity();
        color = color.add(intersection.geometry.get_emission());
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
}
