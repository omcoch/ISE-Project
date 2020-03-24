import primitives.*;
import static java.lang.System.out;
import static primitives.Util.simpleSquare;

public final class Exercise1_answers {
    public static void main(String[] args) {
        Vector u = new Vector(6,0,3);
        Vector v = new Vector(-4,3,-2);
        Vector w = new Vector(1,2,-2);

        out.println(u.crossProduct(w).length());
        out.println(w.crossProduct(v).length());
        out.println((u.crossProduct(w)).dotProduct(u));
        out.println(w.crossProduct(u).length());
        out.println(u.dotProduct(v));
        out.println(
                (v.lengthSquared()-simpleSquare(u.normalized().dotProduct(v)))
                        *(u.lengthSquared())
                );
        out.println(u.crossProduct(v).lengthSquared());
        out.println(u.normalized().get_head().get_y());
    }
}