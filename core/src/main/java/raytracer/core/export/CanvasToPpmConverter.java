package raytracer.core.export;

import raytracer.core.graphics.Canvas;
import raytracer.core.graphics.Color;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CanvasToPpmConverter {
    private CanvasToPpmConverter() {
    }

    public static String convert(Canvas c) {
        var newLine = System.getProperty("line.separator");
        var sb = new StringBuilder("P3").append(newLine)
                .append(c.width).append(" ").append(c.height).append(newLine)
                .append("255").append(newLine);
        for (int j = 0; j < c.height; j++) {
            final int y = j;
            var canvasLine = IntStream.range(0, c.width)
                    .mapToObj(x -> c.pixelAt(x, y))
                    .map(CanvasToPpmConverter::rgb)
                    .collect(Collectors.joining(" "));
            sb.append(canvasLine).append(newLine);
        }
        return sb.toString();
    }

    private static String rgb(Color c) {
        return normalize(c.red()) + " " + normalize(c.green()) + " " + normalize(c.blue());
    }

    private static int normalize(double originalValue) {
        int value = (int) Math.ceil(originalValue * 255);
        if (value > 255) return 255;
        return Math.max(value, 0);
    }
}
