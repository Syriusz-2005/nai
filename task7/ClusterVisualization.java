package task7;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class ClusterVisualization extends JPanel {
    private final ArrayList<DataPoint> dataPoints;
    private final double[][] centroids;
    private final static Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN, Color.PINK};

    public ClusterVisualization(ArrayList<DataPoint> dataPoints, double[][] centroids) {
        this.dataPoints = dataPoints;
        this.centroids = centroids;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minX = dataPoints.stream().map(e -> e.getFeatures()[0]).min(Comparator.naturalOrder()).get();
        double maxX = dataPoints.stream().map(e -> e.getFeatures()[0]).max(Comparator.naturalOrder()).get();
        double minY = dataPoints.stream().map(e -> e.getFeatures()[1]).min(Comparator.naturalOrder()).get();
        double maxY = dataPoints.stream().map(e -> e.getFeatures()[1]).max(Comparator.naturalOrder()).get();

        int margin = 50;
        int plotWidth = getWidth() - 2 * margin;
        int plotHeight = getHeight() - 2 * margin;

        double scaleX = plotWidth / (maxX - minX);
        double scaleY = plotHeight / (maxY - minY);

        dataPoints.stream()
                .filter(p -> p.clusterId >= 0 && p.clusterId < colors.length)
                .forEach(point -> {
                    g2d.setColor(colors[point.clusterId]);

                    int x = margin + (int)((point.getFeatures()[0] - minX) * scaleX);
                    int y = getHeight() - margin - (int)((point.getFeatures()[1] - minY) * scaleY);

                    g2d.fillOval(x - 5, y - 5, 10, 10);
                });

        for (int i = 0; i < centroids.length; i++) {
            if (i < colors.length) {
                g2d.setColor(colors[i]);

                int x = margin + (int) ((centroids[i][0] - minX) * scaleX);
                int y = getHeight() - margin - (int) ((centroids[i][1] - minY) * scaleY);

                g2d.setStroke(new BasicStroke(3));
                g2d.drawOval(x - 8, y - 8, 16, 16);
                g2d.drawLine(x - 10, y, x + 10, y);
                g2d.drawLine(x, y - 10, x, y + 10);
            }
        }

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(margin, getHeight() - margin, getWidth() - margin, getHeight() - margin);
        g2d.drawLine(margin, margin, margin, getHeight() - margin);

        g2d.drawString("Długość działki (cm)", getWidth() / 2, getHeight() - 10);
        g2d.rotate(-Math.PI / 2);
        g2d.drawString("Szerokość działki (cm)", -getHeight() / 2, 15);
    }
}
