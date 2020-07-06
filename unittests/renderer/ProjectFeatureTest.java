package renderer;

import elements.*;
import elements.SpotLight;
import geometries.*;
import geometries.Polygon;
import org.junit.Test;
import elements.AmbientLight;
import elements.Camera;

import primitives.*;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.awt.*;
import java.awt.Color;


public class ProjectFeatureTest {

    @Test
    public void SuperSamplingTest() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(new primitives.Color(Color.white));
        scene.set_ambientLight(new AmbientLight(new primitives.Color(java.awt.Color.WHITE), 0.2));

        scene.addGeometries(new Sphere(new primitives.Color(Color.black), 250, new Point3D(0, 0, 1000)));


        ImageWriter imageWriter = new ImageWriter("Super Sampling Test", 1000, 1000, 500, 500);
        Render render = new Render(imageWriter, scene)
                .setAmountOfRaysForAntiAliasing(200)
                .setMultithreading(3)
                .setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void SoftShadowTest() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(primitives.Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new primitives.Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Polygon(new primitives.Color(0, 0, 0), new Material(0.2, 0.2, 30),
                        new Point3D(-120, -120, 150), new Point3D(120, -120, 150), new Point3D(120, 120, 150), new Point3D(-120, 120, 150)), //
                new Sphere(new primitives.Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30),
                        30, new Point3D(0, 0, 115)),//
                //cube:
                new Polygon(new primitives.Color(Color.red), new Material(0.2, 0.2, 30),
                        new Point3D(-80, -80, 150), new Point3D(-50, -80, 150), new Point3D(-50, -80, 120), new Point3D(-80, -80, 120)),//
                new Polygon(new primitives.Color(Color.red), new Material(0.2, 0.2, 30),
                        new Point3D(-50, -80, 150), new Point3D(-50, -50, 150), new Point3D(-50, -50, 120), new Point3D(-50, -80, 120)),//
                new Polygon(new primitives.Color(Color.red), new Material(0.2, 0.2, 30),
                        new Point3D(-80, -50, 150), new Point3D(-50, -50, 150), new Point3D(-50, -50, 120), new Point3D(-80, -50, 120)),//
                new Polygon(new primitives.Color(Color.red), new Material(0.2, 0.2, 30),
                        new Point3D(-80, -80, 150), new Point3D(-80, -50, 150), new Point3D(-80, -50, 120), new Point3D(-80, -80, 120)),//
                new Polygon(new primitives.Color(Color.red), new Material(0.2, 0.2, 30),
                        new Point3D(-80, -80, 120), new Point3D(-50, -80, 120), new Point3D(-50, -50, 120), new Point3D(-80, -50, 120)));


        scene.addLights(new PointLight(new primitives.Color(700, 400, 400), //
                new Point3D(0, -80, 100), 1, 4E-4, 2E-5));


        ImageWriter imageWriter = new ImageWriter("Soft Shadow Test2", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene)
                //.setAmountOfRaysForAntiAliasing(50)
                //.setAmountOfRaysForSoftShadow(100)
                .setMultithreading(3)
                .setDebugPrint()
                //.setRadiusOfLightSource(10)
                .enableBVH();

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void SoftShadowTest2() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(-1100, -1100, -900), new Vector(1, 1, 1), new Vector(0, 1, -1)));
        scene.set_distance(1000);
        scene.set_background(primitives.Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new primitives.Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Polygon(new primitives.Color(0, 0, 0), new Material(0.2, 0.2, 30),
                        new Point3D(-120, -120, 150), new Point3D(120, -120, 150), new Point3D(120, 120, 150), new Point3D(-120, 120, 150)), //
                new Sphere(new primitives.Color(Color.green), new Material(0.5, 0.5, 30),
                        30, new Point3D(0, 0, 120)),//
                //cube:
                new Polygon(new primitives.Color(Color.red), new Material(0.2, 0.2, 30),
                        new Point3D(-80, -80, 150), new Point3D(-50, -80, 150), new Point3D(-50, -80, 120), new Point3D(-80, -80, 120)),//
                new Polygon(new primitives.Color(Color.red), new Material(0.2, 0.2, 30),
                        new Point3D(-50, -80, 150), new Point3D(-50, -50, 150), new Point3D(-50, -50, 120), new Point3D(-50, -80, 120)),//
                new Polygon(new primitives.Color(Color.red), new Material(0.2, 0.2, 30),
                        new Point3D(-80, -50, 150), new Point3D(-50, -50, 150), new Point3D(-50, -50, 120), new Point3D(-80, -50, 120)),//
                new Polygon(new primitives.Color(Color.red), new Material(0.2, 0.2, 30),
                        new Point3D(-80, -80, 150), new Point3D(-80, -50, 150), new Point3D(-80, -50, 120), new Point3D(-80, -80, 120)),//
                new Polygon(new primitives.Color(Color.red), new Material(0.2, 0.2, 30),
                        new Point3D(-80, -80, 120), new Point3D(-50, -80, 120), new Point3D(-50, -50, 120), new Point3D(-80, -50, 120)));


        scene.addLights(/*new PointLight(new primitives.Color(700, 400, 400), //
                new Point3D(0, -80, 100), 1, 4E-4, 2E-5),*/
                new PointLight(new primitives.Color(700, 400, 400),
                        new Point3D(-120, -120, 130), 1, 4E-4, 2E-5),
                new PointLight(new primitives.Color(700, 400, 400),
                        new Point3D(120, 120, 130), 1, 4E-4, 2E-5));


        ImageWriter imageWriter = new ImageWriter("Soft Shadow Test3", 200, 200, 1000, 1000);
        Render render = new Render(imageWriter, scene)
                .setMultithreading(3)
                .setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void HousesScene() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, 1, 0)));
        scene.set_distance(1000);
        scene.set_background(primitives.Color.BLACK);
        scene.set_ambientLight(new AmbientLight(primitives.Color.BLACK, 0));

        scene.addGeometries(
                new Polygon(new primitives.Color(0, 50, 0), new Material(0.1, 0.1, 0),
                        new Point3D(-200, -200, -200), new Point3D(200, -200, -200), new Point3D(350, -200, 400), new Point3D(-350, -200, 400)),
                //moon
                new Sphere(new primitives.Color(15, 10, 10), new Material(0.1, 0.1, 10, 0.8, 0),
                        40, new Point3D(350, 350, 700)),
                //stars
                new Sphere(new primitives.Color(30, 30, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        2, new Point3D(-100, 250, 600)),
                new Sphere(new primitives.Color(50, 30, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        3, new Point3D(100, 350, 500)),
                new Sphere(new primitives.Color(30, 20, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        2, new Point3D(-200, 150, 700)),
                new Sphere(new primitives.Color(40, 25, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        4, new Point3D(-150, 250, 500)),
                new Sphere(new primitives.Color(40, 35, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        2, new Point3D(50, 250, 650)),
                new Sphere(new primitives.Color(30, 30, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        2, new Point3D(-300, 250, 800)),
                new Sphere(new primitives.Color(50, 30, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        2, new Point3D(200, 150, 400)),
                new Sphere(new primitives.Color(30, 20, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        2, new Point3D(-150, 250, 750)),
                new Sphere(new primitives.Color(40, 25, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        4, new Point3D(-200, 300, 600)),
                new Sphere(new primitives.Color(30, 40, 20), new Material(0.1, 0.2, 50, 0.8, 0),
                        3, new Point3D(150, 250, 650)),
                new Sphere(new primitives.Color(50, 50, 10), new Material(0.2, 0.3, 50, 0.8, 0),
                        2, new Point3D(350, 200, 750)),
                new Sphere(new primitives.Color(40, 45, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        4, new Point3D(-270, 300, 600)),
                new Sphere(new primitives.Color(30, 40, 20), new Material(0.1, 0.2, 50, 0.8, 0),
                        3, new Point3D(250, 250, 650)),
                new Sphere(new primitives.Color(30, 25, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        4, new Point3D(-20, 50, 400)),
                new Sphere(new primitives.Color(20, 10, 20), new Material(0.1, 0.2, 50, 0.8, 0),
                        3, new Point3D(170, 170, 450)),
                new Sphere(new primitives.Color(20, 20, 10), new Material(0.2, 0.3, 50, 0.8, 0),
                        2, new Point3D(100, 90, 350)),
                new Sphere(new primitives.Color(30, 25, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        4, new Point3D(-180, 80, 400)),
                new Sphere(new primitives.Color(20, 10, 20), new Material(0.1, 0.2, 50, 0.8, 0),
                        3, new Point3D(160, 100, 350)),
                new Sphere(new primitives.Color(30, 25, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        4, new Point3D(-20, -50, 600)),
                new Sphere(new primitives.Color(20, 10, 20), new Material(0.1, 0.2, 50, 0.8, 0),
                        3, new Point3D(170, 100, 750)),
                new Sphere(new primitives.Color(20, 20, 10), new Material(0.2, 0.3, 50, 0.8, 0),
                        2, new Point3D(100, 40, 850)),
                new Sphere(new primitives.Color(30, 25, 10), new Material(0.1, 0.2, 50, 0.8, 0),
                        4, new Point3D(-180, 60, 500)),
                new Sphere(new primitives.Color(20, 10, 20), new Material(0.1, 0.2, 50, 0.8, 0),
                        3, new Point3D(160, 10, 750)),


                //house
                new Polygon(new primitives.Color(20, 20, 20), new Material(0.2, 0.1, 10),
                        new Point3D(-150, -200, 250), new Point3D(-150, -70, 250), new Point3D(-150, -70, 100), new Point3D(-150, -200, 100)),
                new Polygon(new primitives.Color(20, 20, 20), new Material(0.2, 0.1, 10),
                        new Point3D(-150, -200, 250), new Point3D(-150, -70, 250), new Point3D(-250, -70, 250), new Point3D(-250, -200, 250)),
                new Polygon(new primitives.Color(20, 20, 20), new Material(0.2, 0.1, 10),
                        new Point3D(-250, -200, 250), new Point3D(-250, -70, 250), new Point3D(-250, -70, 100), new Point3D(-250, -200, 100)),
                new Polygon(new primitives.Color(20, 20, 20), new Material(0.2, 0.1, 10),
                        new Point3D(-250, -200, 100), new Point3D(-250, -70, 100), new Point3D(-150, -70, 100), new Point3D(-150, -200, 100)),
                new Polygon(new primitives.Color(20, 20, 20), new Material(0.2, 0.1, 10),
                        new Point3D(-150, -70, 250), new Point3D(-250, -70, 250), new Point3D(-250, -70, 100), new Point3D(-150, -70, 100)),
                //pyramids
                new Triangle(new primitives.Color(25, 22, 10), new Material(0.1, 0.1, 10, 0, 0.5),
                        new Point3D(150, -250, 250), new Point3D(75, -100, 200), new Point3D(100, -250, 150)),
                new Triangle(new primitives.Color(25, 22, 10), new Material(0.1, 0.1, 10, 0, 0.5),
                        new Point3D(100, -250, 150), new Point3D(75, -100, 200), new Point3D(50, -250, 250)),

                new Triangle(new primitives.Color(25, 22, 10), new Material(0.1, 0.1, 10, 0, 0.5),
                        new Point3D(250, -250, 300), new Point3D(175, -100, 250), new Point3D(200, -250, 200)),
                new Triangle(new primitives.Color(25, 22, 10), new Material(0.1, 0.1, 10, 0, 0.5),
                        new Point3D(200, -250, 200), new Point3D(175, -100, 250), new Point3D(150, -250, 300))

        );
        scene.addLights(
                //moon
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(350, 350, 700), 1, 4E-5, 2E-7),
                //stars
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(-100, 250, 600), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(100, 350, 500), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(-200, 150, 700), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(-150, 250, 500), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(50, 250, 650), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(-300, 250, 800), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(100, 350, 500), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(200, 150, 400), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(-150, 250, 750), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(-200, 300, 600), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(150, 250, 650), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(350, 200, 750), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(-270, 300, 600), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(250, 250, 650), 1, 0.01, 0.0001),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(-20, 50, 400), 1, 0.03, 0.0005),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(170, 170, 450), 1, 0.03, 0.0005),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(100, 90, 350), 1, 0.03, 0.0005),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(-180, 80, 400), 1, 0.03, 0.0005),
                new PointLight(new primitives.Color(1000, 1000, 1000),
                        new Point3D(160, 100, 350), 1, 0.03, 0.0005),

                //spot on building
                new SpotLight(new primitives.Color(1000, 1000, 100),
                        new Point3D(-151, -70, 99), 1, 0.01, 0.0001, new Vector(0, -1, 0)),
                new SpotLight(new primitives.Color(1000, 1000, 100),
                        new Point3D(-251, -70, 99), 1, 0.01, 0.0001, new Vector(0, -1, 0)),
                new SpotLight(new primitives.Color(1000, 1000, 100),
                        new Point3D(-149, -70, 251), 1, 0.01, 0.0001, new Vector(0, -1, 0))

        );
        ImageWriter imageWriter = new ImageWriter("HousesScene", 500, 500, 1000, 1000);
        Render render = new Render(imageWriter, scene)
                .setDebugPrint()
                .setMultithreading(3)
                .setAmountOfRaysForAntiAliasing(50)
                .setAmountOfRaysForSoftShadow(100)
                .enableBVH()
                .setRadiusOfLightSource(40);

        render.renderImage();
        render.writeToImage();
    }
}
