package raytracer.core.geometry;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static raytracer.core.geometry.Matrix.*;

public class MatrixTransformationsTest {
    private final Matrix translation = translation(5, -3, 2);
    private final Matrix scaling = scaling(2, 3, 4);

    @Test
    void multiplying_by_a_translation_matrix() {
        var p = new Point(-3, 4, 5);
        assertThat(translation.times(p)).isEqualTo(new Point(2, 1, 7));
    }

    @Test
    void multiplying_by_the_inverse_of_a_translation_matrix() {
        var inverse = translation.inverse();
        var p = new Point(2, 1, 7);
        assertThat(inverse.times(p)).isEqualTo(new Point(-3, 4, 5));
    }

    @Test
    void translation_does_not_affect_vectors() {
        var v = new Vector(-3, 4, 5);
        assertThat(translation.times(v)).isEqualTo(new Vector(-3, 4, 5));
    }

    @Test
    void applying_a_scaling_matrix_to_a_point() {
        var p = new Point(-4, 6, 8);
        assertThat(scaling.times(p)).isEqualTo(new Point(-8, 18, 32));
    }

    @Test
    void applying_a_scaling_matrix_to_a_vector() {
        var v = new Vector(-4, 6, 8);
        assertThat(scaling.times(v)).isEqualTo(new Vector(-8, 18, 32));
    }

    @Test
    void multiplying_by_the_inverse_of_a_scaling_matrix() {
        var inv = scaling.inverse();
        var v = new Vector(-4, 6, 8);
        assertThat(inv.times(v)).isEqualTo(new Vector(-2, 2, 2));
    }

    @Test
    void reflection_is_scaling_by_a_negative_value() {
        var reflection = scaling(-1, 1, 1);
        var p = new Point(2, 3, 4);
        assertThat(reflection.times(p)).isEqualTo(new Point(-2, 3, 4));
    }

    @Test
    void rotating_a_point_around_the_x_axis() {
        var p = new Point(0, 1, 0);
        var halfQuarter = rotationX(Math.PI / 4);
        var fullQuarter = rotationX(Math.PI / 2);
        assertThat(halfQuarter.times(p)).isEqualTo(new Point(0, Math.sqrt(2) / 2, Math.sqrt(2) / 2));
        assertThat(fullQuarter.times(p)).isEqualTo(new Point(0, 0, 1));
    }

    @Test
    void inverse_of_an_x_rotating_matrix_rotates_in_an_opposite_direction() {
        var p = new Point(0, 1, 0);
        var halfQuarter = rotationX(Math.PI / 4);
        var inv = halfQuarter.inverse();
        double z = -Math.sqrt(2) / 2;
        assertThat(inv.times(p)).isEqualTo(new Point(0, Math.sqrt(2) / 2, z));
    }

    @Test
    void rotating_a_point_around_the_y_axis() {
        var p = new Point(0, 0, 1);
        var halfQuarter = Matrix.rotationY(Math.PI / 4);
        var fullQuarter = Matrix.rotationY(Math.PI / 2);
        assertThat(halfQuarter.times(p)).isEqualTo(new Point(Math.sqrt(2) / 2, 0, Math.sqrt(2) / 2));
        assertThat(fullQuarter.times(p)).isEqualTo(new Point(1, 0, 0));
    }

    @Test
    void rotating_a_point_around_the_z_axis() {
        var p = new Point(0, 1, 0);
        var halfQuarter = Matrix.rotationZ(Math.PI / 4);
        var fullQuarter = Matrix.rotationZ(Math.PI / 2);
        double x = -Math.sqrt(2) / 2;
        assertThat(halfQuarter.times(p)).isEqualTo(new Point(x, Math.sqrt(2) / 2, 0));
        assertThat(fullQuarter.times(p)).isEqualTo(new Point(-1, 0, 0));
    }

    @Test
    void shearing_transformation_moves_x_in_proportion_to_y() {
        var transform = Matrix.shearingBuilder().xPerY(1).build();
        var p = new Point(2, 3, 4);
        assertThat(transform.times(p)).isEqualTo(new Point(5, 3, 4));
    }

    @Test
    void shearing_transformation_moves_x_in_proportion_to_z() {
        var transform = Matrix.shearingBuilder().xPerZ(1).build();
        var p = new Point(2, 3, 4);
        assertThat(transform.times(p)).isEqualTo(new Point(6, 3, 4));
    }

    @Test
    void shearing_transformation_moves_y_in_proportion_to_x() {
        var transform = Matrix.shearingBuilder().yPerX(1).build();
        var p = new Point(2, 3, 4);
        assertThat(transform.times(p)).isEqualTo(new Point(2, 5, 4));
    }

    @Test
    void shearing_transformation_moves_y_in_proportion_to_z() {
        var transform = Matrix.shearingBuilder().yPerZ(1).build();
        var p = new Point(2, 3, 4);
        assertThat(transform.times(p)).isEqualTo(new Point(2, 7, 4));
    }

    @Test
    void shearing_transformation_moves_z_in_proportion_to_x() {
        var transform = Matrix.shearingBuilder().zPerX(1).build();
        var p = new Point(2, 3, 4);
        assertThat(transform.times(p)).isEqualTo(new Point(2, 3, 6));
    }

    @Test
    void shearing_transformation_moves_z_in_proportion_to_y() {
        var transform = Matrix.shearingBuilder().zPerY(1).build();
        var p = new Point(2, 3, 4);
        assertThat(transform.times(p)).isEqualTo(new Point(2, 3, 7));
    }

    @Nested
    @DisplayName("Multiple transformations")
    class MultipleTransformations {
        private final Point point = new Point(1, 0, 1);
        private final Matrix A = rotationX(Math.PI / 2);
        private final Matrix B = scaling(5, 5, 5);
        private final Matrix C = translation(10, 5, 7);

        @Test
        void individual_transformation_are_applied_in_sequence() {
            var p2 = A.times(point);
            assertThat(p2).isEqualTo(new Point(1, -1, 0));
            var p3 = B.times(p2);
            assertThat(p3).isEqualTo(new Point(5, -5, 0));
            var p4 = C.times(p3);
            assertThat(p4).isEqualTo(new Point(15, 0, 7));
        }

        @Test
        void chained_transformation_must_be_applied_in_reverse_order() {
            var T = C.times(B).times(A);
            assertThat(T.times(point)).isEqualTo(new Point(15, 0, 7));
        }

        @Test
        void transformations_can_be_chained_via_fluent_api() {
            var transformation = Matrix.IDENTITY
                    .rotateX(Math.PI / 2)
                    .scale(5, 5, 5)
                    .translate(10, 5, 7);
            assertThat(transformation.times(point)).isEqualTo(new Point(15, 0, 7));
        }
    }
}
