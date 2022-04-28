package raytracer.projectile;

import static raytracer.core.geometry.Tuple.point;
import static raytracer.core.geometry.Tuple.vector;

public class App {
    public static void main(String[] args) {
        System.out.println("Launching projectile!");
        var p = new Projectile(point(0, 1, 0), vector(1, 3, 0).normalize());
        var e = new Environment(vector(0, -0.1, 0), vector(-0.01, 0, 0));
        while (p.position().y > 0) {
            p = tick(e, p);
        }
        System.out.println("Projectile's position: " + p.position());
    }

    private static Projectile tick(Environment env, Projectile proj) {
        return proj.atNextPosition(env);
    }
}
