package renderer;

import org.junit.Test;

import elements.*;
import geometries.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import primitives.*;
import scene.Scene;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {

    private Scene _scene = null;
    private ImageWriter _imageWriter = null;

    /**
     * Produce a scene with basic 3D model and render it into a jpeg image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() {

 /*     Scene scene = new Scene("Test scene");
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
*/

        // initialize the scene and imageWriter, the class variables to the values from the xml file
        GetFromXML("unittests/renderer/data.xml");
        Render render = new Render(_imageWriter, _scene);
        render.renderImage();
        render.printGrid(50, java.awt.Color.YELLOW);
        render.writeToImage();
    }

    /*  @Test
      public void getClosestPointTest() {
          Scene scene = new Scene("Test closet point");
          scene.set_camera(new Camera(new Point3D(0,-4,0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
          scene.set_distance(4);

          //T1: in case the point is not the first value in the list of intersections
          scene.addGeometries(new Sphere(5, new Point3D(1, 6, 0)));

          Intersectable geometries = scene.get_geometries();
          List<Point3D> intersectionPoints = geometries.findIntersections(
                  new Ray(new Point3D(0,-4,0),new Vector(new Point3D(4,7,2)) )
          );

          ImageWriter imageWriter = new ImageWriter("test", 500, 500, 500, 500);
          Render render = new Render(imageWriter, scene);
          assertEquals(new Point3D(3.406866886276646,1.9620170509841302,1.703433443138323),render.getClosestPoint(intersectionPoints),"wrong point" );

          //T2: in case the point is the first value in the list of intersections
          scene = new Scene("Test closet point 2");
          scene.set_camera(new Camera(new Point3D(0,-4,0), new Vector(0, 0, 1), new Vector(0, -1, 0)));
          scene.set_distance(4);
          scene.addGeometries(new Triangle(new Point3D(0,0,3),new Point3D(-3,0,0),new Point3D(2,0,0)),
                  new Triangle(new Point3D(-2,2,4),new Point3D(3,3,0),new Point3D(-2,0,0)));

          geometries = scene.get_geometries();
          intersectionPoints = geometries.findIntersections(
                  new Ray(new Point3D(0,-4,0),new Vector(new Point3D(0,4,1)) )
          );

          imageWriter = new ImageWriter("test", 500, 500, 500, 500);
          render = new Render(imageWriter, scene);

          assertEquals(new Point3D(0,0,1), render.getClosestPoint(intersectionPoints),"wrong point" );

      }
  */

    /**
     * Handle the xml file which contains data for Scene and ImageWriter objects
     * and inserts the data into these class variables
     * @param filename the xml file to upload
     */
    private void GetFromXML(String filename) {
        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            Node scene = doc.getElementsByTagName("Scene").item(0);
            if (scene.getNodeType() == Node.ELEMENT_NODE) {
                Element sceneElement = (Element) scene;
                // set scene name
                Element eCurrent = (Element) sceneElement.getElementsByTagName("name").item(0);
                _scene = new Scene(eCurrent.getTextContent());
                // set camera
                eCurrent = (Element) sceneElement.getElementsByTagName("Camera").item(0);
                _scene.set_camera(new Camera(
                        RenderXml.createPointFromElement(eCurrent.getElementsByTagName("Point").item(0)),
                        new Vector(RenderXml.createPointFromElement(eCurrent.getElementsByTagName("Vector").item(0))),
                        new Vector(RenderXml.createPointFromElement(eCurrent.getElementsByTagName("Vector").item(1)))));
                // set distance
                eCurrent = (Element) sceneElement.getElementsByTagName("distance").item(0);
                _scene.set_distance(Double.parseDouble(eCurrent.getTextContent()));
                // set background
                eCurrent = (Element) sceneElement.getElementsByTagName("Background").item(0);
                _scene.set_background(RenderXml.createColorFromElement(eCurrent.getElementsByTagName("Color").item(0)));
                // set ambientLight
                eCurrent = (Element) sceneElement.getElementsByTagName("AmbientLight").item(0);
                _scene.set_ambientLight(new AmbientLight(
                        RenderXml.createColorFromElement(eCurrent.getElementsByTagName("Color").item(0)),
                        Double.parseDouble(eCurrent.getElementsByTagName("ka").item(0).getTextContent())));
                // set the geometries
                _scene.addGeometries(RenderXml.createListOfGeometries(sceneElement.getElementsByTagName("Geometries").item(0).getChildNodes()));
            }

            // set imageWriter
            Node ir = doc.getElementsByTagName("ImageWriter").item(0);
            if (ir.getNodeType() == Node.ELEMENT_NODE) {
                Element irCurrent = (Element) ir;
                String name = irCurrent.getElementsByTagName("name").item(0).getTextContent();
                double width = Double.parseDouble(irCurrent.getElementsByTagName("width").item(0).getTextContent());
                double height = Double.parseDouble(irCurrent.getElementsByTagName("height").item(0).getTextContent());
                int nX = Integer.parseInt(irCurrent.getElementsByTagName("nX").item(0).getTextContent());
                int nY = Integer.parseInt(irCurrent.getElementsByTagName("nY").item(0).getTextContent());

                _imageWriter = new ImageWriter(name, width, height, nX, nY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void basicRenderMultiColorTest() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(100);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2));

        scene.addGeometries(new Sphere(50, new Point3D(0, 0, 100)));

        scene.addGeometries(
                new Triangle(new Color(java.awt.Color.BLUE),
                        new Point3D(100, 0, 100), new Point3D(0, 100, 100), new Point3D(100, 100, 100)),      // lower right
                new Triangle(
                        new Point3D(100, 0, 100), new Point3D(0, -100, 100), new Point3D(100, -100, 100)),    // upper right
                new Triangle(new Color(java.awt.Color.RED),
                        new Point3D(-100, 0, 100), new Point3D(0, 100, 100), new Point3D(-100, 100, 100)),    // lower left
                new Triangle(new Color(java.awt.Color.GREEN),
                        new Point3D(-100, 0, 100), new Point3D(0, -100, 100), new Point3D(-100, -100, 100))); // upper left

        ImageWriter imageWriter = new ImageWriter("color render test2", 500, 500, 500, 500);
        Render render = new Render(imageWriter, scene).setAmountOfRays(1).setMultithreading(3);

        render.renderImage();
        render.printGrid(50, java.awt.Color.WHITE);
        render.writeToImage();
    }

}
