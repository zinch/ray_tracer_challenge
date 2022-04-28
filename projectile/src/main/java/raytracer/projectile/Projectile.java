package raytracer.projectile;

import raytracer.core.geometry.Point;
import raytracer.core.geometry.Vector;

public record Projectile(Point position, Vector velocity) {

    public Projectile atNextPosition(Environment env) {
        var newPosition = position.plus(velocity);
        var newVelocity = velocity.plus(env.gravity()).plus(env.wind());
        return new Projectile(newPosition, newVelocity);
    }
}
