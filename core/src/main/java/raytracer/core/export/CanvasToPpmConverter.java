package raytracer.core.export;

import raytracer.core.graphics.Canvas;
import raytracer.core.graphics.Color;

public class CanvasToPpmConverter {
    private final static int MAX_LINE_WIDTH = 70;

    private CanvasToPpmConverter() {
    }

    public static String convert(Canvas c) {
        var newLine = System.getProperty("line.separator");
        var sb = new StringBuilder("P3").append(newLine)
                .append(c.width).append(" ").append(c.height).append(newLine)
                .append("255").append(newLine);
        for (int y = 0; y < c.height; y++) {
            var line = new StringBuilder(MAX_LINE_WIDTH);
            for (int x = 0; x < c.width; x++) {
                var color = c.pixelAt(x, y);
                for (var colorComponent : rgbComponents(color)) {
                    if (line.length() + colorComponent.length() + 1 > MAX_LINE_WIDTH) {
                        sb.append(line).append(newLine);
                        line = new StringBuilder(MAX_LINE_WIDTH);
                        line.append(colorComponent);
                    } else {
                        if (line.length() > 0) {
                            line.append(" ");
                        }
                        line.append(colorComponent);
                    }
                }
            }
            sb.append(line).append(newLine);
        }
        return sb.toString();
    }

    private static String[] rgbComponents(Color c) {
        return new String[]{normalize(c.red()), normalize(c.green()), normalize(c.blue())};
    }

    private static String normalize(double originalValue) {
        int value = (int) Math.ceil(originalValue * 255);
        if (value > 255) return "255";
        return String.valueOf(Math.max(value, 0));
    }
}
