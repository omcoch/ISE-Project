package renderer;
import elements.PointLight;
import elements.SpotLight;
import geometries.Polygon;
import org.junit.Test;
import elements.AmbientLight;
import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;

import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

import java.awt.*;


public class ProjectFeatureTest {

    @Test
    public void SuperSamplingTest(){
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(new primitives.Color(Color.white));
        scene.set_ambientLight(new AmbientLight(new primitives.Color(java.awt.Color.WHITE), 0.2));

        scene.addGeometries(new Sphere(new primitives.Color(Color.black),165, new Point3D(0, 0, 1000)));


        ImageWriter imageWriter = new ImageWriter("Super Sampling Test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene).setAmountOfRaysForAntiAliasing(50).setMultithreading(3);

        render.renderImage();
        render.writeToImage();
    }
    @Test
    public void SoftShadowTest(){
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
                new Polygon(new primitives.Color(Color.red),new Material(0.2,0.2,30),
                        new Point3D(-80,-80,150),new Point3D(-50,-80,150),new Point3D(-50,-80,120),new Point3D(-80,-80,120)),//
                new Polygon(new primitives.Color(Color.red),new Material(0.2,0.2,30),
                        new Point3D(-50,-80,150),new Point3D(-50,-50,150),new Point3D(-50,-50,120),new Point3D(-50,-80,120)),//
                new Polygon(new primitives.Color(Color.red),new Material(0.2,0.2,30),
                        new Point3D(-80,-50,150),new Point3D(-50,-50,150),new Point3D(-50,-50,120),new Point3D(-80,-50,120)),//
                new Polygon(new primitives.Color(Color.red),new Material(0.2,0.2,30),
                        new Point3D(-80,-80,150),new Point3D(-80,-50,150),new Point3D(-80,-50,120),new Point3D(-80,-80,120)),//
                new Polygon(new primitives.Color(Color.red),new Material(0.2,0.2,30),
                        new Point3D(-80,-80,120),new Point3D(-50,-80,120),new Point3D(-50,-50,120),new Point3D(-80,-50,120)) );


        scene.addLights(new PointLight(new primitives.Color(700, 400, 400), //
                new Point3D(0, -80, 100), 1, 4E-4, 2E-5));


        ImageWriter imageWriter = new ImageWriter("Soft Shadow Test", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene).setAmountOfRaysForSoftShadow(300).setMultithreading(3).setDebugPrint().setRadiusOfLightSource(10);

        render.renderImage();
        render.writeToImage();
    }
}
