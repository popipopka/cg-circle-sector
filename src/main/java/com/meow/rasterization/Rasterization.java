package com.meow.rasterization;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Rasterization {

    private Rasterization() {
        throw new IllegalStateException("Utility class");
    }

    public static void drawSector(final GraphicsContext graphicsContext,
                                  final int x0, final int y0,
                                  final double angle1, final double angle2,
                                  final double r,
                                  Color start, Color end) {
        PixelWriter writer = graphicsContext.getPixelWriter();

        final double minAngle = Math.min(angle1, angle2);
        final double maxAngle = Math.max(angle1, angle2);

        int xLeft;
        int xRight;
        double sqrt;
        boolean isBelongsToSector;
        for (int y = (int) (y0 - r + 1); y < (int) (y0 + r); y++) {
            sqrt = Math.round(Math.sqrt(Math.pow(r, 2) - Math.pow((y - y0), 2)));

            xLeft = (int) (x0 - sqrt);
            xRight = (int) (x0 + sqrt);

            for (int x = xLeft; x <= xRight; x++) {
                isBelongsToSector = belongsToSector(minAngle, maxAngle, x0, y0, x, y, r);

                if (isBelongsToSector) {
                    writer.setColor(x, y, interpolateColor(start, end, r, x0, y0, x, y));
                }
            }
        }
    }

    // Зеркально отражает относительно Oy и делает его значение от 0 до 360
    public static double normalizeAngle(double angle) {
        return 360 - (angle + 360) % 360;
    }

    private static boolean belongsToSector(double minAngle, double maxAngle, int x0, int y0, int x, int y, double radius) {
        double r = distanceBetweenPoints(x0, y0, x, y);
        double angle = normalizeAngle(Math.toDegrees(Math.atan2((y - y0), (x - x0))));

        return minAngle <= angle && angle <= maxAngle && r <= radius;
    }

    private static double distanceBetweenPoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public static Color interpolateColor(Color c1, Color c2, double radius, int x0, int y0, int x, int y) {
        double r1 = c1.getRed();
        double g1 = c1.getGreen();
        double b1 = c1.getBlue();

        double r2 = c2.getRed();
        double g2 = c2.getGreen();
        double b2 = c2.getBlue();

        double ratio = distanceBetweenPoints(x0, y0, x, y) / radius;

        double r = r1 * (1 - ratio) + r2 * ratio;
        double g = g1 * (1 - ratio) + g2 * ratio;
        double b = b1 * (1 - ratio) + b2 * ratio;

        return new Color(r, g, b, 1.0);
    }
}
