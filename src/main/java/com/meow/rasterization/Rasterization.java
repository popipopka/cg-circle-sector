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
                                  double angle1, double angle2,
                                  final double radius) {
        PixelWriter writer = graphicsContext.getPixelWriter();

        double minAngle = Math.min(angle1, angle2);
        double maxAngle = Math.max(angle1, angle2);

        int xLeft;
        int xRight;
        double sqrt;
        boolean isBelongsToSector;
        for (int y = (int) (y0 - radius + 1); y < (int) (y0 + radius); y++) {
            sqrt = Math.round(Math.sqrt(Math.pow(radius, 2) - Math.pow((y - y0), 2)));

            xLeft = (int) (x0 - sqrt);
            xRight = (int) (x0 + sqrt);

            for (int x = xLeft; x <= xRight; x++) {
                isBelongsToSector = belongsToSector(minAngle, maxAngle, x0, y0, x, y, radius);

                if (isBelongsToSector) {
                    writer.setColor(x, y, Color.BLACK);
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

    private static double distanceBetweenPoints(int x0, int y0, int x, int y) {
        return Math.sqrt(Math.pow((x - x0), 2) + Math.pow((y - y0), 2));
    }
}
