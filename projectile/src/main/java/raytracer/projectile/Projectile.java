package raytracer.projectile;

import raytracer.core.Point;
import raytracer.core.Vector;

public record Projectile(Point position, Vector velocity) {

    public Projectile atNextPosition(Environment env) {
        var newPosition = position.plus(velocity);
        var newVelocity = velocity.plus(env.gravity()).plus(env.wind());
        return new Projectile(newPosition, newVelocity);
    }
}
