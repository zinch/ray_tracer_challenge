package raytracer.core;

import org.junit.jupiter.api.Test;
import raytracer.core.geometry.Matrix;
import raytracer.core.geometry.Sphere;
import raytracer.core.graphics.Color;
import raytracer.core.light.Material;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static raytracer.core.geometry.Tuple.point;

class WorldTest {

    @Test
    void the_default_world() {
        var w = World.DEFAULT;

        var light = w.light();
        assertThat(light.position()).isEqualTo(point(-10, 10, -10));
        assertThat(light.intensity()).isEqualTo(Color.WHITE);

        var objects = w.objects();
        assertThat(objects).hasSize(2);
        assertThat(objects.get(0)).isInstanceOf(Sphere.class);
        var firstSphere = (Sphere) objects.get(0);
        assertThat(firstSphere.material()).isEqualTo(Material.builder()
                .color(new Color(0.8, 1.0, 0.6))
                .diffuse(0.7)
                .specular(0.2)
                .build());

        assertThat(objects.get(1)).isInstanceOf(Sphere.class);
        Sphere secondSphere = (Sphere) objects.get(1);
        assertThat(secondSphere.transform()).isEqualTo(new Matrix(new double[][]{
                {0.5, 0, 0, 0},
                {0, 0.5, 0, 0},
                {0, 0, 0.5, 0},
                {0, 0, 0, 1}
        }));
    }
}