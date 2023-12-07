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
                                  Color c1, Color c2) {
        PixelWriter writer = graphicsContext.getPixelWriter();

        double minAngle;
        double maxAngle;

        if (Math.abs(angle2) >= Math.abs(angle1)) {
            minAngle = angle1;
            maxAngle = angle2;
        } else {
            minAngle = angle2;
            maxAngle = angle1;
        }

        boolean isBig = Math.abs(maxAngle) > 360;

        minAngle = simplifyAngle(minAngle);
        if(maxAngle % 360 == 0) {
            maxAngle = 360;
        } else maxAngle = simplifyAngle(maxAngle);

        double temp = Math.min(minAngle, maxAngle);
        maxAngle = Math.max(minAngle, maxAngle);
        minAngle = temp;

        int xLeft;
        int xRight;
        double sqrt;
        for (
                int y = (int) (y0 - r + 1); y < (int) (y0 + r); y++) {
            sqrt = Math.round(Math.sqrt(Math.pow(r, 2) - Math.pow((y - y0), 2)));

            xLeft = (int) (x0 - sqrt);
            xRight = (int) (x0 + sqrt);

            for (int x = xLeft; x <= xRight; x++) {
                if (!isBig && belongsToSector(minAngle, maxAngle, x0, y0, x, y, r)) {
                    writer.setColor(x, y, interpolateColor(c1, c2, r, x0, y0, x, y));
                } else if (isBig && !belongsToSector(minAngle, maxAngle, x0, y0, x, y, r)) {
                    writer.setColor(x, y, interpolateColor(c1, c2, r, x0, y0, x, y));
                }
            }
        }
    }

    private static double mirrorAngleByOy(double angle) {
        return 360 - simplifyAngle(angle);
    }

    private static double simplifyAngle(double angle) {
        angle = (angle + 360) % 360;
        if (angle < 0) angle += 360;
        return angle;
    }

    private static boolean belongsToSector(double minAngle, double maxAngle, int x0, int y0, int x, int y,
                                           double radius) {
        double r = distanceBetweenPoints(x0, y0, x, y);
        double angle = mirrorAngleByOy(Math.toDegrees(Math.atan2((y - y0), (x - x0))));

        return minAngle <= angle && angle <= maxAngle && r <= radius + 1;
    }

    private static double distanceBetweenPoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    private static Color interpolateColor(Color c1, Color c2, double radius, int x0, int y0, int x, int y) {
        double r1 = c1.getRed();
        double g1 = c1.getGreen();
        double b1 = c1.getBlue();

        double r2 = c2.getRed();
        double g2 = c2.getGreen();
        double b2 = c2.getBlue();

        double ratio = distanceBetweenPoints(x0, y0, x, y) / radius;

        double r = cosineInterpolate(r1, r2, ratio);
        double g = cosineInterpolate(g1, g2, ratio);
        double b = cosineInterpolate(b1, b2, ratio);

        return new Color(r, g, b, 1.0);
    }

    private static double cosineInterpolate(double a, double b, double ratio) {
        double f = (1 - Math.cos(ratio * Math.PI)) * 0.5;
        return a * (1 - f) + b * ratio;
    }
}
