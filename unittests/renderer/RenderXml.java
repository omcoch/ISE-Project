package renderer;

import geometries.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import primitives.Color;
import primitives.Point3D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Help class for handling Scene and ImageWriter elements from xml file
 *
 * @author Ron & Omri
 */
public abstract class RenderXml {
    /**
     * The function gets Point node from xml file
     * and create from the data Point3D object
     *
     * @param node the Point xml element
     * @return Point3D object
     */
    static Point3D createPointFromElement(Node node) {
        Element point = (Element) node;
        double x = Double.parseDouble(point.getElementsByTagName("x").item(0).getTextContent());
        double y = Double.parseDouble(point.getElementsByTagName("y").item(0).getTextContent());
        double z = Double.parseDouble(point.getElementsByTagName("z").item(0).getTextContent());
        return new Point3D(x, y, z);
    }

    /**
     * The function gets Color node from xml file
     * and create from the data Color object
     *
     * @param node the Color xml element
     * @return primitive.Color object
     */
    static Color createColorFromElement(Node node) {
        Element color = (Element) node;
        double r = Double.parseDouble(color.getElementsByTagName("r").item(0).getTextContent());
        double g = Double.parseDouble(color.getElementsByTagName("g").item(0).getTextContent());
        double b = Double.parseDouble(color.getElementsByTagName("b").item(0).getTextContent());
        return new Color(r, g, b);
    }

    /**
     * Returns an array of intersectables that contain the geometries elements
     * which received from the xml file.
     *
     * @param geometries (type NodeList) a list of geometries elements
     * @return array of intersectables
     */
    public static Intersectable[] createListOfGeometries(NodeList geometries) {
        // because we get unnecessary text we skip every other place
        Intersectable[] l = new Intersectable[geometries.getLength() / 2];
        int index = 0; // real index for the array
        for (int i = 1; i < geometries.getLength(); i += 2) {
            Node geo = geometries.item(i);
            if (geo.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) geo;
                if ("Sphere".equals(eElement.getNodeName()))
                    l[index++] = createSphere(eElement);
                else if ("Triangle".equals(eElement.getNodeName()))
                    l[index++] = (createTriangle(eElement));
                else if ("Plane".equals(eElement.getNodeName()))
                    l[index++] = (createPlane(eElement));
            }
        }
        return l;
    }

    /**
     * The function gets Plane node from xml file
     * and create from the data Plane object
     *
     * @param eElement the Plane xml element
     * @return Plane object
     */
    private static Intersectable createPlane(Element eElement) {
        // no implementation because we don't need it right now
        return null;
    }

    /**
     * The function gets Sphere node from xml file
     * and create from the data Sphere object
     *
     * @param eElement the Sphere xml element
     * @return Sphere object
     */
    private static Sphere createSphere(Element eElement) {
        return new Sphere(
                Double.parseDouble(eElement.getElementsByTagName("radius").item(0).getTextContent()),
                createPointFromElement(eElement.getElementsByTagName("Point").item(0)));
    }

    /**
     * The function gets Triangle node from xml file
     * and create from the data Triangle object
     *
     * @param eElement the Color xml element
     * @return Triangle object
     */
    private static Triangle createTriangle(Element eElement) {
        return new Triangle(
                createPointFromElement(eElement.getElementsByTagName("Point").item(0)),
                createPointFromElement(eElement.getElementsByTagName("Point").item(1)),
                createPointFromElement(eElement.getElementsByTagName("Point").item(2)));
    }


}
