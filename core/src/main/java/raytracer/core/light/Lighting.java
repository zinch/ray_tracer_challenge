package raytracer.core.light;

import raytracer.core.geometry.Point;
import raytracer.core.geometry.Vector;
import raytracer.core.graphics.Color;

public record Lighting(Material material, PointLight light) {
    public Color computeAt(Point point, Vector normal, Vector eye) {
        var effectiveColor = light.intensity().times(material.color());
        var lightVector = light().position().minus(point).normalize();
        var ambient = effectiveColor.times(material.ambient());
        Color diffuse;
        Color specular;

        var lightDotNormal = lightVector.dot(normal);
        var isLightBehindSurface = lightDotNormal < 0;
        if (isLightBehindSurface) {
            diffuse = Color.BLACK;
            specular = Color.BLACK;
        } else {
            diffuse = effectiveColor.times(material.diffuse()).times(lightDotNormal);
            var reflect = lightVector.negate().reflect(normal);
            var reflectDotEye = eye.dot(reflect);
            var lightReflectsAway = reflectDotEye < 0;
            if (lightReflectsAway) {
                specular = Color.BLACK;
            } else {
                var factor = Math.pow(reflectDotEye, material.shininess());
                specular = light.intensity().times(material.specular()).times(factor);
            }
        }
        return ambient.plus(diffuse).plus(specular);


    }
}
