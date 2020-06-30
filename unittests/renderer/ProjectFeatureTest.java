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
    public void SuperSamplingTest(){
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(new primitives.Color(Color.white));
        scene.set_ambientLight(new AmbientLight(new primitives.Color(java.awt.Color.WHITE), 0.2));

        scene.addGeometries(new Sphere(new primitives.Color(Color.black),250, new Point3D(0, 0, 1000)));


        ImageWriter imageWriter = new ImageWriter("Super Sampling Test", 1000, 1000, 1000, 1000);
        Render render = new Render(imageWriter, scene).setAmountOfRaysForAntiAliasing(200).setMultithreading(3).setDebugPrint();

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
                        new Point3D(-80,-80,120),new Point3D(-50,-80,120),new Point3D(-50,-50,120),new Point3D(-80,-50,120)));


        scene.addLights(new PointLight(new primitives.Color(700, 400, 400), //
                new Point3D(0, -80, 100), 1, 4E-4, 2E-5));


        ImageWriter imageWriter = new ImageWriter("Soft Shadow Test", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene)
                .setAmountOfRaysForAntiAliasing(50)
                .setAmountOfRaysForSoftShadow(300)
                .setMultithreading(3)
                .setDebugPrint()
                .setRadiusOfLightSource(10)
                .enableBVH();

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void SoftShadowTest2(){
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
                new Polygon(new primitives.Color(Color.red),new Material(0.2,0.2,30),
                        new Point3D(-80,-80,150),new Point3D(-50,-80,150),new Point3D(-50,-80,120),new Point3D(-80,-80,120)),//
                new Polygon(new primitives.Color(Color.red),new Material(0.2,0.2,30),
                        new Point3D(-50,-80,150),new Point3D(-50,-50,150),new Point3D(-50,-50,120),new Point3D(-50,-80,120)),//
                new Polygon(new primitives.Color(Color.red),new Material(0.2,0.2,30),
                        new Point3D(-80,-50,150),new Point3D(-50,-50,150),new Point3D(-50,-50,120),new Point3D(-80,-50,120)),//
                new Polygon(new primitives.Color(Color.red),new Material(0.2,0.2,30),
                        new Point3D(-80,-80,150),new Point3D(-80,-50,150),new Point3D(-80,-50,120),new Point3D(-80,-80,120)),//
                new Polygon(new primitives.Color(Color.red),new Material(0.2,0.2,30),
                        new Point3D(-80,-80,120),new Point3D(-50,-80,120),new Point3D(-50,-50,120),new Point3D(-80,-50,120)));


        scene.addLights(/*new PointLight(new primitives.Color(700, 400, 400), //
                new Point3D(0, -80, 100), 1, 4E-4, 2E-5),*/
                new PointLight(new primitives.Color(700, 400, 400),
                        new Point3D(-120,-120,130),1, 4E-4, 2E-5),
                new PointLight(new primitives.Color(700, 400, 400),
                        new Point3D(120,120,130),1, 4E-4, 2E-5));


        ImageWriter imageWriter = new ImageWriter("Soft Shadow Test3", 200, 200, 1000, 1000);
        Render render = new Render(imageWriter, scene)
                .setMultithreading(3)
                .setDebugPrint()
                ;

        render.renderImage();
        render.writeToImage();
    }


    public Polygon Square(primitives.Color color,Material material,Point3D center, double size){
        return new Polygon(color,material,
                center.add(new Vector(size,size,0)),
                center.add(new Vector(-size,size,0)),
                center.add(new Vector(-size,-size,0)),
                center.add(new Vector(size,-size,0))
        );
    }

    //create a pattern of white and mirror polygon
    public Geometries pattern(int numWithe, int numLen , double size){
        primitives.Color color1 = new primitives.Color(1,1,1).scale(80);
        primitives.Color color2 = new primitives.Color(1,1,1);
        Material material1 = new Material(0.5,0.5,0,0,0);
        Material material2 = new Material(0.2,0.8,30,0,0.8);
        Geometries geometries = new Geometries();
        for (int i = -numWithe;i<numWithe;i++){
            for (int j = -numLen;j<numLen ;j++){
                Material material = (i+j)%2 ==0 ?material1 :material2;
                primitives.Color color =(i+j)%2 ==0 ?color1 :color2;
                geometries.add(Square(color,material,new Point3D(i*size,j*size,0),size/2));
            }
        }
        return geometries;
    }

    @Test
    public  void testComplex_scene(){
        Scene scene = new Scene("Test scene");
        double cameraAngel = Math.toRadians(-3);
        scene.set_camera(new Camera(new Point3D(0, -15, 3), new Vector(0, Math.cos(cameraAngel), Math.sin(cameraAngel)), new Vector(0, -Math.sin(cameraAngel), Math.cos(cameraAngel))));
//        double cameraAngel = Math.toRadians(-90);
//        scene.setCamera(new Camera(new Point3D(0, -0, 20), new Vector(0, Math.cos(cameraAngel), Math.sin(cameraAngel)), new Vector(0, -Math.sin(cameraAngel), Math.cos(cameraAngel))));
        scene.set_distance(400);
        scene.set_background(new primitives.Color(255,255,255).scale(0.2));
        scene.set_ambientLight(new AmbientLight(new primitives.Color(0,0,0), 0.15));
        Material materialPlane = new Material(1, 0, 0, 0, 0);
        scene.addGeometries(
                pattern(8,5,2),
//
//
                new Sphere(new primitives.Color(0,0,255),
                        new Material(0.5, 0.5, 20, 0, 0.9),2,
                        new Point3D(2.1,0,2.1)),
                new Sphere(new primitives.Color(100,0,0),
                        new Material(0.5, 0.5, 20, 0, 0.9),1,
                        new Point3D(0,-3,1.1)),
                new Sphere(new primitives.Color(173,255,47).scale(.4),
                        new Material(0.3, 0.5, 30, .0,.3 ),2.5,
                        new Point3D(-2.6,0,2.6)),

                new Sphere(new primitives.Color(255,215,0),
                        new Material(0.4, 0.4, 20, .6, 0),0.7,
                        new Point3D(-6,2,0.75))
        );

        scene.addLights(
                new PointLight(new primitives.Color(255, 255, 255).scale(0.5),
                        new Point3D(20, 50, 10), 1, 4E-5, 2E-7),
                new DirectionalLight(new primitives.Color(255, 255, 204).scale(0.1),
                        new Vector(-1,-0.1,-0.3)),
                new SpotLight(
                        new primitives.Color(255,255,200).scale(2),
                        new Point3D(0,0,20),
                        1,  4E-5, 2E-7,new Vector(0,0,-1)));


        ImageWriter imageWriter = new ImageWriter("testComplex_scene", 400, 300, 1440, 1080);
        Render render = new Render(imageWriter, scene).setMultithreading(3).setDebugPrint().setAmountOfRaysForSoftShadow(100).setRadiusOfLightSource(10).enableBVH();
        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void SphereTriangleInitial() {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(primitives.Color.BLACK);
        scene.set_ambientLight(new AmbientLight(primitives.Color.BLACK, 0));

        scene.addGeometries(new Sphere(new primitives.Color(Color.green), new Material(0.5, 0.5, 30), //
                        60, new Point3D(0, 0, 200)), //
                new Triangle(new primitives.Color(java.awt.Color.BLUE), new Material(0.5, 0.5, 30), //
                        new Point3D(-70, 40, 0), new Point3D(-40, 70, 0), new Point3D(-68, 68, 4)));

        scene.addLights(new SpotLight(new primitives.Color(400, 240, 0), //
                new Point3D(-100, 100, -200), 1, 1E-5, 1.5E-7, new Vector(1, -1, 3)));

        ImageWriter imageWriter = new ImageWriter("sphereTriangleInitial2", 200, 200, 400, 400);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }
}
