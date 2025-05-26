package TSP;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TSPVisualizer extends JPanel {

    private final List<City> cities;
    private final List<City> tour;

    public TSPVisualizer(List<City> cities, List<City> tour) {
        this.cities = cities;
        this.tour = tour;
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (tour == null || tour.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLUE);

        for (City city : cities) {
            int x = (int) city.x * 5 + 50;
            int y = (int) city.y * 5 + 50;
            g2.fillOval(x - 5, y - 5, 10, 10);
            g2.drawString(String.valueOf(city.id), x + 6, y - 6);
        }

        g2.setColor(Color.BLACK);
        for (int i = 0; i < tour.size(); i++) {
            City c1 = tour.get(i);
            City c2 = tour.get((i + 1) % tour.size());
            int x1 = (int) c1.x * 5 + 50;
            int y1 = (int) c1.y * 5 + 50;
            int x2 = (int) c2.x * 5 + 50;
            int y2 = (int) c2.y * 5 + 50;
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    public static void showFrame(List<City> cities, List<City> tour) {
        JFrame frame = new JFrame("TSP Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TSPVisualizer(cities, tour));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
