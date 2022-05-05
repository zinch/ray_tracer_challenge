package raytracer.core.light;

import org.junit.jupiter.api.Test;
import raytracer.core.graphics.Color;

import static org.assertj.core.api.Assertions.assertThat;

class MaterialTest {
    @Test
    void the_default_material() {
        var material = Material.builder().build();
        assertThat(material.color()).isEqualTo(Color.WHITE);
        assertThat(material.ambient()).isEqualTo(0.1);
        assertThat(material.diffuse()).isEqualTo(0.9);
        assertThat(material.specular()).isEqualTo(0.9);
        assertThat(material.shininess()).isEqualTo(200);
    }

    @Test
    void custom_material() {
        var material = Material.builder()
                .ambient(0.3)
                .specular(0.25)
                .shininess(125.7)
                .diffuse(0.7)
                .color(new Color(0.2, 0, 0.9))
                .build();
        assertThat(material.color()).isEqualTo(new Color(0.2, 0, 0.9));
        assertThat(material.ambient()).isEqualTo(0.3);
        assertThat(material.diffuse()).isEqualTo(0.7);
        assertThat(material.specular()).isEqualTo(0.25);
        assertThat(material.shininess()).isEqualTo(125.7);
    }
}