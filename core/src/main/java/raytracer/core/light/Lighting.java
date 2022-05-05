package raytracer.core.light;

import raytracer.core.geometry.Point;
import raytracer.core.geometry.Vector;
import raytracer.core.graphics.Color;

public record Lighting(Material material, PointLight light) {
    public Color computeAt(Point point, Vector normal, Vector eye) {
        var effectiveColor = light.intensity().times(material.color());
        var lightVector = light().position().minus(point).normalize();
        var ambient = effectiveColor.times(material.ambient());
        Color diffuse = Color.BLACK;
        Color specular = Color.BLACK;

        var lightDotNormal = lightVector.dot(normal);
        if (lightDotNormal >= 0) {
            diffuse = effectiveColor.times(material.diffuse()).times(lightDotNormal);
            var reflectDotEye = eye.dot(lightVector.negate().reflect(normal));
            if (reflectDotEye >= 0) {
                var factor = Math.pow(reflectDotEye, material.shininess());
                specular = light.intensity().times(material.specular()).times(factor);
            }
        }
        return ambient.plus(diffuse).plus(specular);


    }
}
