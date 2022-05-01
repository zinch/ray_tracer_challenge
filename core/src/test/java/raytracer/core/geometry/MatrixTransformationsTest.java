package raytracer.core.geometry;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static raytracer.core.geometry.Tuple.point;
import static raytracer.core.geometry.Tuple.vector;

public class MatrixTransformationsTest {
    private final Matrix translation = Matrix.translation(5, -3, 2);
    private final Matrix scaling = Matrix.scaling(2, 3, 4);

    @Test
    void multiplying_by_a_translation_matrix() {
        var p = point(-3, 4, 5);
        assertThat(translation.times(p)).isEqualTo(point(2, 1, 7));
    }

    @Test
    void multiplying_by_the_inverse_of_a_translation_matrix() {
        var inverse = translation.inverse();
        var p = point(2, 1, 7);
        assertThat(inverse.times(p)).isEqualTo(point(-3, 4, 5));
    }

    @Test
    void translation_does_not_affect_vectors() {
        var v = vector(-3, 4, 5);
        assertThat(translation.times(v)).isEqualTo(vector(-3, 4, 5));
    }

    @Test
    void applying_a_scaling_matrix_to_a_point() {
        var p = point(-4, 6, 8);
        assertThat(scaling.times(p)).isEqualTo(point(-8, 18, 32));
    }

    @Test
    void applying_a_scaling_matrix_to_a_vector() {
        var v = vector(-4, 6, 8);
        assertThat(scaling.times(v)).isEqualTo(vector(-8, 18, 32));
    }

    @Test
    void multiplying_by_the_inverse_of_a_scaling_matrix() {
        var inv = scaling.inverse();
        var v = vector(-4, 6, 8);
        assertThat(inv.times(v)).isEqualTo(vector(-2, 2, 2));
    }

    @Test
    void reflection_is_scaling_by_a_negative_value() {
        var reflection = Matrix.scaling(-1, 1, 1);
        var p = point(2, 3, 4);
        assertThat(reflection.times(p)).isEqualTo(point(-2, 3, 4));
    }

    @Test
    void rotating_a_point_around_the_x_axis() {
        var p = point(0, 1, 0);
        var halfQuarter = Matrix.rotationX(Math.PI / 4);
        var fullQuarter = Matrix.rotationX(Math.PI / 2);
        assertThat(halfQuarter.times(p)).isEqualTo(point(0, Math.sqrt(2) / 2, Math.sqrt(2) / 2));
        assertThat(fullQuarter.times(p)).isEqualTo(point(0, 0, 1));
    }

    @Test
    void inverse_of_an_x_rotating_matrix_rotates_in_an_opposite_direction() {
        var p = point(0, 1, 0);
        var halfQuarter = Matrix.rotationX(Math.PI / 4);
        var inv = halfQuarter.inverse();
        assertThat(inv.times(p)).isEqualTo(point(0, Math.sqrt(2) / 2, -Math.sqrt(2) / 2));
    }

    @Test
    void rotating_a_point_around_the_y_axis() {
        var p = point(0, 0, 1);
        var halfQuarter = Matrix.rotationY(Math.PI / 4);
        var fullQuarter = Matrix.rotationY(Math.PI / 2);
        assertThat(halfQuarter.times(p)).isEqualTo(point(Math.sqrt(2) / 2, 0, Math.sqrt(2) / 2));
        assertThat(fullQuarter.times(p)).isEqualTo(point(1, 0, 0));
    }

    @Test
    void rotating_a_point_around_the_z_axis() {
        var p = point(0, 1, 0);
        var halfQuarter = Matrix.rotationZ(Math.PI / 4);
        var fullQuarter = Matrix.rotationZ(Math.PI / 2);
        assertThat(halfQuarter.times(p)).isEqualTo(point(-Math.sqrt(2) / 2, Math.sqrt(2) / 2, 0));
        assertThat(fullQuarter.times(p)).isEqualTo(point(-1, 0, 0));
    }

    @Test
    void shearing_transformation_moves_x_in_proportion_to_y() {
        var transform = Matrix.shearingBuilder().xPerY(1).build();
        var p = point(2, 3, 4);
        assertThat(transform.times(p)).isEqualTo(point(5, 3, 4));
    }

    @Test
    void shearing_transformation_moves_x_in_proportion_to_z() {
        var transform = Matrix.shearingBuilder().xPerZ(1).build();
        var p = point(2, 3, 4);
        assertThat(transform.times(p)).isEqualTo(point(6, 3, 4));
    }

    @Test
    void shearing_transformation_moves_y_in_proportion_to_x() {
        var transform = Matrix.shearingBuilder().yPerX(1).build();
        var p = point(2, 3, 4);
        assertThat(transform.times(p)).isEqualTo(point(2, 5, 4));
    }
    @Test
    void shearing_transformation_moves_y_in_proportion_to_z() {
        var transform = Matrix.shearingBuilder().yPerZ(1).build();
        var p = point(2, 3, 4);
        assertThat(transform.times(p)).isEqualTo(point(2, 7, 4));
    }
    @Test
    void shearing_transformation_moves_z_in_proportion_to_x() {
        var transform = Matrix.shearingBuilder().zPerX(1).build();
        var p = point(2, 3, 4);
        assertThat(transform.times(p)).isEqualTo(point(2, 3, 6));
    }
    @Test
    void shearing_transformation_moves_z_in_proportion_to_y() {
        var transform = Matrix.shearingBuilder().zPerY(1).build();
        var p = point(2, 3, 4);
        assertThat(transform.times(p)).isEqualTo(point(2, 3, 7));
    }
}
