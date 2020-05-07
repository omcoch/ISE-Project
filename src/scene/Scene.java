package scene;

import elements.*;
import geometries.*;
import primitives.Color;

public class Scene {
    private final String _name;
    private final Geometries _geometries;

    private Color _background;
    private AmbientLight _ambientLight;
    private Camera _camera;
    private double _distance;


    public void set_background(Color _background) {
        this._background = _background;
    }

    public void set_ambientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    public void set_camera(Camera _camera) {
        this._camera = _camera;
    }

    public void set_distance(double _distance) {
        this._distance = _distance;
    }

    public String get_name() {
        return _name;
    }

    public Geometries get_geometries() {
        return _geometries;
    }

    public Color get_background() {
        return _background;
    }

    public AmbientLight get_ambientLight() {
        return _ambientLight;
    }

    public Camera get_camera() {
        return _camera;
    }

    public double get_distance() {
        return _distance;
    }

    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
    }

    /**
     * Add a list of Intersectables to this 3D scene model
     * @param geometries
     */
    public void addGeometries(Intersectable... geometries) {
        _geometries.add(geometries);
    }
}
