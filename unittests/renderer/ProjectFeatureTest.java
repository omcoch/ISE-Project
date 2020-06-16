package renderer;
import org.junit.Test;
import elements.AmbientLight;
import elements.Camera;
import geometries.Sphere;
import geometries.Triangle;

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
        Render render = new Render(imageWriter, scene).setAmountOfRays(50).setMultithreading(3);

        render.renderImage();
        render.writeToImage();
    }
}
