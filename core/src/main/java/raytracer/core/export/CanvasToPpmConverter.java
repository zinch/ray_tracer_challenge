package raytracer.core.export;

import raytracer.core.graphics.Canvas;

public class CanvasToPpmConverter {
    private CanvasToPpmConverter() {
    }

    public static String convert(Canvas c) {
        var newLine = System.getProperty("line.separator");
        var sb = new StringBuilder("P3").append(newLine)
                .append(c.width).append(" ").append(c.height).append(newLine)
                .append("255");
        return sb.toString();
    }
}
