package renderer;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import scene.Scene;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(100);
        scene.set_background(new Color(75, 127, 90));
        scene.set_ambientLight(new AmbientLight(new Color(255, 191, 191), 1));

        scene.addGeometries(new Sphere(50, new Point3D(0, 0, 100)));

        scene.addGeometries(
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100)),
                new Triangle(new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100)),
                new Triangle(new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100)));

        ImageWriter imageWriter = new ImageWriter("base render test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.printGrid(50, java.awt.Color.YELLOW);
        render.writeToImage();
    }

    @Test
    public void getClosestPointTest() {
        Scene scene = new Scene("Test closet point");
        scene.set_camera(new Camera(new Point3D(0,-4,0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(4);
        //scene.set_background(new Color(75, 127, 90));
        //scene.set_ambientLight(new AmbientLight(new Color(255, 191, 191), 1));
        scene.addGeometries(new Sphere(5, new Point3D(1, 6, 0)));

        Intersectable geometries = scene.get_geometries();
        List<Point3D> intersectionPoints = geometries.findIntersections(
                new Ray(new Point3D(0,-4,0),new Vector(new Point3D(4,7,2)) )
        );

        System.out.println(intersectionPoints);

        ImageWriter imageWriter = new ImageWriter("test", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene);
        assertEquals(render.getClosestPoint(intersectionPoints),new Point3D(3.406866886276646,1.9620170509841302,1.703433443138323),"wrong point" );

    }
}
