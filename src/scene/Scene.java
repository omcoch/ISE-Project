package scene;

import elements.*;
import geometries.*;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that represents a scene
 */
public class Scene {
    private final String _name;
    private final Geometries _geometries;

    private Color _background;
    private AmbientLight _ambientLight;
    private Camera _camera;
    private double _distance;

    private List<LightSource> _lights=new LinkedList<LightSource>();

    /**
     * Constructor of scene
     * @param name Name of the scene
     */
    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
    }


    /**
     * Set the background color of the scene
     * @param _background the background color
     */
    public void set_background(Color _background) {
        this._background = _background;
    }

    /**
     * Set the ambient light of the scene
     * @param _ambientLight the ambient Light
     */
    public void set_ambientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    /**
     * Set the camera in the scene
     * @param _camera the camera
     */
    public void set_camera(Camera _camera) {
        this._camera = _camera;
    }

    /**
     * Set the camera distance from the view plane
     * @param _distance the distance
     */
    public void set_distance(double _distance) {
        this._distance = _distance;
    }

    /**
     * Gets the name of the scene
     * @return the name
     */
    public String get_name() {
        return _name;
    }

    /**
     * Gets the geometries list in the scene
     * @return the geometries list
     */
    public Geometries get_geometries() {
        return _geometries;
    }

    /**
     * Gets the background color of the scene
     * @return the background color
     */
    public Color get_background() {
        return _background;
    }

    /**
     * Gets the ambient light of the scene
     * @return the ambient Light
     */
    public AmbientLight get_ambientLight() {
        return _ambientLight;
    }

    /**
     * Gets the scene's camera
     * @return the camera
     */
    public Camera get_camera() {
        return _camera;
    }

    /**
     * Gets the distance from camera in the scene
     * @return the distance
     */
    public double get_distance() {
        return _distance;
    }

    /**
     * getter for the light sources in the scene
     * @return the light sources
     */
    public List<LightSource> get_lights() {
        return _lights;
    }

    public void addLights(LightSource... lights){_lights.addAll(List.of(lights));}

    /**
     * Add a list of Intersectables to this 3D scene model
     * @param geometries the geometries elements
     */
    public void addGeometries(Intersectable... geometries) {
        _geometries.add(geometries);
    }
}
