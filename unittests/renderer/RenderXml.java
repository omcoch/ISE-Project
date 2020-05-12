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

public abstract class RenderXml {
    static Point3D createPointFromElement(Node node) {
        Element point = (Element) node;
        double x = Double.parseDouble(point.getElementsByTagName("x").item(0).getTextContent());
        double y = Double.parseDouble(point.getElementsByTagName("y").item(0).getTextContent());
        double z = Double.parseDouble(point.getElementsByTagName("z").item(0).getTextContent());
        return new Point3D(x, y, z);
    }

    static Color createColorFromElement(Node node) {
        Element color = (Element) node;
        double r = Double.parseDouble(color.getElementsByTagName("r").item(0).getTextContent());
        double g = Double.parseDouble(color.getElementsByTagName("g").item(0).getTextContent());
        double b = Double.parseDouble(color.getElementsByTagName("b").item(0).getTextContent());
        return new Color(r, g, b);
    }

    public static Intersectable[] createListOfGeometries(NodeList geometries) {
        Intersectable[] l = new Intersectable[geometries.getLength()/2];
        for (int i = 1; i < geometries.getLength(); i+=2) {
            Node geo = geometries.item(i);
            if (geo.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) geo;
                if ("Sphere".equals(eElement.getNodeName()))
                    l[(i-1)/2] = createSphere(eElement);
                else if ("Triangle".equals(eElement.getNodeName()))
                    l[(i-1)/2] = (createTriangle(eElement));
                else if ("Plane".equals(eElement.getNodeName()))
                    l[(i-1)/2] = (createPlane(eElement));
            }
        }
        return l;
    }

    private static Intersectable createPlane(Element eElement) {
        // no implementation because we don't need it right now
        return null;
    }

    private static Sphere createSphere(Element eElement) {
        return new Sphere(
                Double.parseDouble(eElement.getElementsByTagName("radius").item(0).getTextContent()),
        createPointFromElement(eElement.getElementsByTagName("Point").item(0)));
    }

    private static Triangle createTriangle(Element eElement) {
        return new Triangle(
          createPointFromElement(eElement.getElementsByTagName("Point").item(0)),
          createPointFromElement(eElement.getElementsByTagName("Point").item(1)),
          createPointFromElement(eElement.getElementsByTagName("Point").item(2)));
    }


}
