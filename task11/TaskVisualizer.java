package task11;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TaskVisualizer extends JPanel {
    private final List<Task> allTasks;
    private final List<Task> selectedTasks;
    private final int padding = 50;
    private final int taskHeight = 25;
    private final int rowGap = 10;

    private final List<List<Task>> layeredTasks;

    public TaskVisualizer(List<Task> allTasks, List<Task> selectedTasks) {
        this.allTasks = allTasks;
        this.selectedTasks = selectedTasks;
        this.layeredTasks = assignRows(allTasks);

        int maxEnd = allTasks.stream().mapToInt(Task::end).max().orElse(100);
        int width = Math.max(800, maxEnd + 2 * padding);
        int height = layeredTasks.size() * (taskHeight + rowGap) + 2 * padding;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);
    }

    private List<List<Task>> assignRows(List<Task> tasks) {
        tasks.sort(Comparator.comparingInt(Task::start));
        List<List<Task>> layers = new ArrayList<>();

        for (Task task : tasks) {
            boolean placed = false;
            for (List<Task> row : layers) {
                if (row.stream().noneMatch(existing -> overlaps(existing, task))) {
                    row.add(task);
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                List<Task> newRow = new ArrayList<>();
                newRow.add(task);
                layers.add(newRow);
            }
        }
        return layers;
    }

    private boolean overlaps(Task t1, Task t2) {
        return t1.start() < t2.end() && t2.start() < t1.end();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int maxEnd = allTasks.stream().mapToInt(Task::end).max().orElse(100);
        double scale = (getWidth() - 2.0 * padding) / maxEnd;

        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int row = 0; row < layeredTasks.size(); row++) {
            int y = padding + row * (taskHeight + rowGap);
            for (Task task : layeredTasks.get(row)) {
                int x = padding + (int) (task.start() * scale);
                int width = Math.max(30, (int) ((task.end() - task.start()) * scale));

                boolean selected = selectedTasks.contains(task);
                g2.setColor(selected ? new Color(76, 175, 80) : new Color(220, 220, 220));
                g2.fillRoundRect(x, y, width, taskHeight, 10, 10);
                g2.setColor(Color.BLACK);
                g2.drawRoundRect(x, y, width, taskHeight, 10, 10);

                String label = "(" + task.start() + "," + task.end() + ")";
                int labelWidth = g2.getFontMetrics().stringWidth(label);
                if (labelWidth < width - 6) {
                    g2.drawString(label, x + 5, y + taskHeight / 2 + 5);
                } else {
                    g2.drawString(label, x, y - 3);
                }
            }
        }
    }

    public static void visualize(List<Task> allTasks, List<Task> selectedTasks) {
        JFrame frame = new JFrame("Task Schedule Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(new TaskVisualizer(allTasks, selectedTasks)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
