package renderer;

import geometries.Plane;
import geometries.Polygon;
import org.junit.Test;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0), 50,
                        new Point3D(0, 0, 50)),
                new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), 25, new Point3D(0, 0, 50)));

        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), 1,
                0.0004, 0.0000006, new Vector(-1, 1, 2)));

        ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(10000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.addGeometries(
                new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0.5, 0), 400, new Point3D(-950, 900, 1000)),
                new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20), 200, new Point3D(-950, 900, 1000)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

        scene.addLights(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, 750, 150),
                1, 0.00001, 0.000005, new Vector(-1, 1, 4)));

        ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
     * producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
                        30, new Point3D(60, -50, 50)));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(60, -50, 0), 1, 4E-5, 2E-7, new Vector(0, 0, 1)));

        ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Our test for section 10:
     * Produce a picture of a two triangles lighted by one spot light and one point light
     * with 2 partially transparent Spheres producing partial shadow.
     * one of the triangles reflects the spheres.
     **/
    @Test
    public void OurTest() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60, 0, 0.5), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
                        30, new Point3D(60, -50, 50)),
                new Sphere(new Color(java.awt.Color.red), new Material(0.2, 0.2, 30, 0.6, 0), // )
                        30, new Point3D(-60, -50, 50))
        );

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                        new Point3D(60, -50, 0), 1, 0.005, 0.000005, new Vector(0, 0, 1)),

                new PointLight(new Color(700, 400, 400), //
                        new Point3D(-60, -20, 0), 1, 0.005, 0.00005)
        );

        ImageWriter imageWriter = new ImageWriter("ourTest", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Our test for recitation 7, bonus:
     * The picture contains 4 spheres and some lights inside a box which its walls are mirrors
     * so we get a complex scene.
     **/
    @Test
    public void OurBonusTest() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(new Color(0, 10, 10));
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Polygon(new Color(0, 0, 0), new Material(0.2, 0.2, 30),
                        new Point3D(-200, -150, 150), new Point3D(200, -150, 150), new Point3D(200, 200, 150), new Point3D(-200, 200, 150)), //
                new Sphere(new Color(55, 123, 22), new Material(0.6, 0.6, 90, 0.4, 0), // )
                        20, new Point3D(75, 0, 100)),
                new Sphere(new Color(java.awt.Color.orange), new Material(0.2, 0.2, 30, 0.8, 0), // )
                        25, new Point3D(0, 0, 100)),
                new Sphere(new Color(java.awt.Color.MAGENTA), new Material(0.2, 0.2, 30),
                        5, new Point3D(75, 0, 100)),
                new Sphere(new Color(java.awt.Color.red), new Material(0.2, 0.2, 30), // )
                        15, new Point3D(-30, 55, 100)),
                new Sphere(new Color(java.awt.Color.blue), new Material(0.2, 0.2, 30), // )
                        10, new Point3D(-40, -70, 100)),
                //Mirrors
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1),//
                        new Point3D(-120, -120, 50), new Point3D(-120, 0, 150), new Point3D(0, -120, 150)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1),//
                        new Point3D(120, -120, 50), new Point3D(120, 0, 150), new Point3D(0, -120, 150)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1),//
                        new Point3D(-120, 120, 50), new Point3D(-120, 0, 150), new Point3D(0, 120, 150)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1),//
                        new Point3D(120, 120, 50), new Point3D(120, 0, 150), new Point3D(0, 120, 150))
        );
        scene.addLights(
                //Sunset effect
                // the higher rgb values are here to balance the attenuation so we get bigger halo
                new SpotLight(new Color(714, 303, 165), //
                        new Point3D(0, 125, 100), 1, 4E-5, 2E-7, new Vector(0, 0, 1)),
                new SpotLight(new Color(714, 303, 165), //
                        new Point3D(0, -125, 100), 1, 4E-5, 2E-7, new Vector(0, 0, 1)),
                new SpotLight(new Color(714, 303, 165), //
                        new Point3D(125, 0, 100), 1, 4E-5, 2E-7, new Vector(0, 0, 1)),
                new SpotLight(new Color(714, 303, 165), //
                        new Point3D(-125, 0, 100), 1, 4E-5, 2E-7, new Vector(0, 0, 1)),

                // out from the RED sphere
                new SpotLight(new Color(700, 400, 400),
                        new Point3D(-50, 75, 90), 1, 0.005, 0.000007, new Vector(1, -1, 0)),
                // out from the SUN(orange) sphere to RED sphere
                new SpotLight(new Color(700, 400, 400), //
                        new Point3D(0, 0, 100), 1, 0.005, 0.00007, new Vector(-1, 1, 0)),
                // out from the SUN(orange) sphere to GREEN sphere
                new SpotLight(new Color(700, 400, 400), //
                        new Point3D(-10, -10, 100), 1, 0.005, 0.00007, new Vector(-1, -1, 0)),
                //toward the SUN(orange) sphere
                new SpotLight(new Color(700, 400, 400),
                        new Point3D(0, 0, 60), 1, 0.04, 0.0007, new Vector(0, 0, 1))

        );

        ImageWriter imageWriter = new ImageWriter("ourBonusTest2", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setAmountOfRays(50);

        render.renderImage();
        render.writeToImage();
    }

}
