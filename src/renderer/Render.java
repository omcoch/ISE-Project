package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
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
    // parameters for determine how much reflection and refraction rays we will make
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    // parameters for multithreading
    private int _threads = 1;
    private final int SPARE_THREADS = 2;
    private boolean _print = false;
    //the amount of rays in the beam for anti aliasing
    private int amountOfRaysForAntiAliasing = 1;
    //the amount of rays in the beam for soft shadow
    private int amountOfRaysForSoftShadow = 1;
    //the radius of the point/spot light source, we using it when we calculate the shadow rays
    private double radiusOfLightSource = 1;
    // determine if BVH feature is on
    private boolean BVH = false;

    /**
     * Pixel is an internal helper class whose objects are associated with a Render object that
     * they are generated in scope of. It is used for multithreading in the Renderer and for follow up
     * its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each thread.
     *
     * @author Dan
     */
    private class Pixel {
        private long _maxRows = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percents = 0;
        private long _nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            _maxRows = maxRows;
            _maxCols = maxCols;
            _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++_counter;
            if (col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    if (_print) System.out.println(_percents);
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (_counter == _nextCounter) {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percents = nextP(target);
            if (percents > 0)
                if (Render.this._print) System.out.printf("\r %02d%%", percents);
            if (percents >= 0)
                return true;
            if (Render.this._print) System.out.printf("\r %02d%%", 100);
            return false;
        }
    }

    /**
     * Constructor
     *
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
        if (BVH) {
            _scene.constructBVH();
        }
        final Camera camera = _scene.get_camera();
        final Intersectable geometries = _scene.get_geometries();
        final java.awt.Color background = _scene.get_background().getColor();
        final double distance = _scene.get_distance();
        final int Nx = _imageWriter.getNx(); //columns
        final int Ny = _imageWriter.getNy(); //rows
        final int width = (int) _imageWriter.getWidth();
        final int height = (int) _imageWriter.getHeight();

        final Pixel thePixel = new Pixel(Ny, Nx);

        // Generate threads
        Thread[] threads = new Thread[_threads];
        //pixels grid - i is pixel row number and j is pixel column number
        for (int i = _threads - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel)) {
                    // construct a beam for each pixel:
                    Beam rays = constructBeamThroughPixel(camera, Nx, Ny, pixel.col, pixel.row, distance, width, height);
                    Color color = Color.BLACK;
                    for (Ray ray : rays.rayList) {
                        // find the intersection points for each geometry with the ray:
                        List<Intersectable.GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                        if (intersectionPoints == null) {
                            // add background color
                            color = color.add(new Color(background));
                        } else { // add the color of the geometry is passing through it
                            GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                            color = color.add(calcColor(closestPoint, ray));
                        }
                    }
                    //calculate the average color of the beam (using for anti aliasing)
                    color = color.scale(1d / rays.rayList.size());
                    //paint the pixel with the color
                    _imageWriter.writePixel(pixel.col, pixel.row, color.getColor());
                }
            });
        }

        // Start threads
        for (Thread thread : threads) thread.start();

        // Wait for all threads to finish
        for (Thread thread : threads)
            try {
                thread.join();
            } catch (Exception e) {
            }
        if (_print) System.out.printf("\r100%%\n");
    }

    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading patameter must be 0 or higher");
        if (threads != 0)
            _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            if (cores <= 2)
                _threads = 1;
            else
                _threads = cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        _print = true;
        return this;
    }

    /**
     * Returns the point that is closest to the camera on a ray
     *
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
     * Calculate the color of a point
     *
     * @param intersection the lighting point
     * @return the color of the point
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        Color color = calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, 1);
        color = color.add(_scene.get_ambientLight().getIntensity());
        return color;
    }

    /**
     * Recursive function that calculate the color of a point
     *
     * @param geoPoint the point
     * @param inRay    ray that intersect the point
     * @param level    the level of the recursion
     * @param k        the attenuation
     * @return the color ot the point
     */
    private Color calcColor(GeoPoint geoPoint, Ray inRay, int level, double k) {
        Color color = geoPoint.geometry.get_emission();
        Vector v = geoPoint.point.subtract(_scene.get_camera().getLocation()).normalize();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Material material = geoPoint.geometry.get_material();
        int nShininess = material.get_nShininess();
        double kd = material.get_kD();
        double ks = material.get_kS();
        for (LightSource lightSource : _scene.get_lights()) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l)), nv = alignZero(n.dotProduct(v));
            if (sign(nl) == sign(nv) && nv != 0 && nl != 0) {
                //calculate the attenuation of the shadow
                double ktr = transparency(lightSource, l, n, geoPoint);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, n, l, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        if (level == 1) return Color.BLACK;
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
            Ray refractedRay = constructRefractedRay(geoPoint.point, inRay, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay,
                        level - 1, kkt).scale(kt));
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
     *
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
     * the Phong model has a provision for a highlight, or specular, component, which reflects light in a
     * shiny way. This is defined by [rs,gs,bs](-V.R)^p, where R is the mirror reflection direction vector we discussed
     * in class (and also used for ray tracing), and where p is a specular power. The higher the value of p, the shinier
     * the surface. [credit for eliezer's github RIP]
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color ip) {
        double p = nShininess;
        double nl = alignZero(n.dotProduct(l));
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
     * @param n  normal to surface at the point
     * @param l  direction from light to point
     * @param ip light intensity at the point
     * @return diffusive component of light reflection
     * @author Dan Zilberstein
     * <p>
     * The diffuse component is that dot product n•L. It approximates light, originally
     * from light source L, reflecting from a surface which is diffuse, or non-glossy. One example of a non-glossy
     * surface is paper. In general, you'll also want this to have a non-gray color value, so this term would in general
     * be a color defined as: [rd,gd,bd](n•L)
     */
    private Color calcDiffusive(double kd, Vector n, Vector l, Color ip) {
        double nl = alignZero(n.dotProduct(l));
        if (nl < 0) nl = -nl;
        return ip.scale(nl * kd);
    }

    /**
     * Determine if there is no shadow on the point
     * Not in use currently
     *
     * @param l      light vector
     * @param n      normal
     * @param gpoint point
     * @return true if there is no shadow
     */
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint gpoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gpoint.point, lightDirection, n);
        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(gpoint.point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gpoint.point.distance(gp.point) - lightDistance) <= 0
                    && gp.geometry.get_material().get_kT() == 0)
                return false;
        }
        return true;
    }

    /**
     * Calculate the transparency of the shadow in a point
     *
     * @param ls       light source
     * @param l        vector from the light to the point
     * @param n        normal
     * @param geopoint the point we want to calculate the shadow
     * @return the transparency of the shadow in a point
     */
    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        double ktrAll = 0.0, ktrMain;
        Ray lightRay = new Ray(geopoint.point, lightDirection, n);
        ktrMain = getKtr(ls, geopoint, lightRay);
        if(amountOfRaysForSoftShadow>1) {
            Beam beam = new Beam(lightRay,//the main ray
                    geopoint.point.add(lightDirection.scale(ls.getDistance(geopoint.point))),//the location of the light
                    radiusOfLightSource,//the radius of the light source
                    amountOfRaysForSoftShadow);//amount of shadow rays to create
            for (int i = 1; i < beam.rayList.size(); i++) {
                ktrAll += getKtr(ls, geopoint, beam.rayList.get(i));
            }
            ktrMain=(ktrAll + ktrMain) / beam.rayList.size();
        }
        return ktrMain;
    }

    /**
     * return the value of the attenuation of the shadow
     *
     * @param ls       the light source
     * @param geopoint the point we want to calculate the shadow in
     * @param lightRay the light ray
     * @return attenuation of the shadow in the given point
     */
    private double getKtr(LightSource ls, GeoPoint geopoint, Ray lightRay) {
        double ktr = 1;
        List<GeoPoint> intersections = _scene.get_geometries().findIntersections(lightRay);
        if (intersections != null) {
            double lightDistance = ls.getDistance(geopoint.point);
            for (GeoPoint gp : intersections) {
                if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
                    ktr *= gp.geometry.get_material().get_kT();
                    if (ktr < MIN_CALC_COLOR_K) ktr = 0.0;
                }
            }
        }
        return ktr;
    }

    /**
     * Construct a reflected ray from the point
     *
     * @param n     normal
     * @param p     point
     * @param inRay the ray that intersect the point
     * @return 𝒗 −𝟐∙(𝒗∙𝒏)∙𝒏
     */
    Ray constructReflectedRay(Vector n, Point3D p, Ray inRay) {
        Vector v = inRay.get_dir();
        double nv = n.dotProduct(v);
        if (nv == 0)
            return null;
        return new Ray(p, v.subtract(n.scale(2 * nv)), n);
    }

    /**
     * Construct a refracted ray from the point
     *
     * @param point the point
     * @param inRay the ray that intersect the point
     * @param n     normal
     * @return refracted ray from the point
     */
    private Ray constructRefractedRay(Point3D point, Ray inRay, Vector n) {
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

    /**
     * set the amount of ray to create in beam for anti aliasing,1 or less to disable
     *
     * @param amountOfRays the amount of ray
     * @return this render
     */
    public Render setAmountOfRaysForAntiAliasing(int amountOfRays) {
        if (amountOfRays < 1)
            amountOfRays = 1;
        this.amountOfRaysForAntiAliasing = amountOfRays;
        return this;
    }

    /**
     * construct beam of rays through pixel
     *
     * @param camera         the camera of scene
     * @param nX             number of pixels in x axis
     * @param nY             number of pixels in y axis
     * @param j              the pixel column
     * @param i              the pixel row
     * @param screenDistance the distance of the view plane from the camera
     * @param screenWidth    the width of the screen
     * @param screenHeight   the height of the screen
     * @return beam of rays that go through pixel
     */
    private Beam constructBeamThroughPixel(Camera camera,
                                           int nX, int nY,
                                           int j, int i, double screenDistance,
                                           double screenWidth, double screenHeight) {
        //the main ray that go through the center of the pixel
        Ray ray = camera.constructRayThroughPixel(nX, nY, j, i, screenDistance, screenWidth, screenHeight);
        //the center of the pixel
        Point3D pc = ray.get_p0().add(ray.get_dir().scale(screenDistance));
        return new Beam(ray,
                pc,
                screenHeight / nY,//the height of the pixel
                screenWidth / nX,//the width of the pixel
                amountOfRaysForAntiAliasing);
    }

    /**
     * set the amount of ray to create in the beam for the soft shadow,1 or less to disable
     *
     * @param amountOfRaysForSoftShadow the amount of rays
     */
    public Render setAmountOfRaysForSoftShadow(int amountOfRaysForSoftShadow) {
        if (amountOfRaysForSoftShadow < 1)
            amountOfRaysForSoftShadow = 1;
        this.amountOfRaysForSoftShadow = amountOfRaysForSoftShadow;
        return this;
    }

    /**
     * set the radius of the lights sources in the scene
     *
     * @param radiusOfLights the radius
     */
    public Render setRadiusOfLightSource(double radiusOfLights) {
        this.radiusOfLightSource = radiusOfLights;
        return this;
    }

    /**
     * Set Bounding Volume Hierarchy on
     *
     * @return the Render object itself
     */
    public Render enableBVH() {
        this.BVH = true;
        return this;
    }
}
