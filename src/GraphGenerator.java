import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GraphGenerator extends JPanel {
  private static final long serialVersionUID = 1L;
  private List<double[]> data = new ArrayList<double[]>();
  private List<Color> colors = new ArrayList<Color>();
  private double minValue = 0;
  private double maxValue = 0;

  public GraphGenerator() {
    setPreferredSize(new Dimension(800, 600));
  }

  public void addData(double[] values, Color color) {
    data.add(values);
    colors.add(color);
    for (double value : values) {
      if (value < minValue) {
        minValue = value;
      }
      if (value > maxValue) {
        maxValue = value;
      }
    }
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    int width = getWidth();
    int height = getHeight();

    int xMargin = width / 10;
    int yMargin = height / 10;

    int graphWidth = width - 2 * xMargin;
    int graphHeight = height - 2 * yMargin;

    g.setColor(Color.BLACK);
    g.drawLine(xMargin, yMargin, xMargin, yMargin + graphHeight);
    g.drawLine(xMargin, yMargin + graphHeight, xMargin + graphWidth, yMargin + graphHeight);

    int numPoints = data.get(0).length;
    for (int i = 1; i < data.size(); i++) {
      if (data.get(i).length != numPoints) {
        throw new IllegalArgumentException("All arrays must have the same length");
      }
    }

    for (int i = 0; i < data.size(); i++) {
      g.setColor(colors.get(i));
      double[] values = data.get(i);
      int[] xPoints = new int[numPoints];
      int[] yPoints = new int[numPoints];
      for (int j = 0; j < numPoints; j++) {
        double x = j * 1.0 / (numPoints - 1);
        double y = (values[j] - minValue) / (maxValue - minValue);
        xPoints[j] = (int) (xMargin + x * graphWidth);
        yPoints[j] = (int) (yMargin + (1 - y) * graphHeight);
      }
      g.drawPolyline(xPoints, yPoints, numPoints);
    }
  }

  public static void main(String[] args) {
    GraphGenerator graph = new GraphGenerator();
    graph.addData(new double[] {1, 2, 3, 4, 5}, Color.RED);
    graph.addData(new double[] {2, 4, 6, 8, 10}, Color.BLUE);

    JFrame frame = new JFrame("Line Graph");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(graph);
    frame.pack();
    frame.setVisible(true);
  }
}
