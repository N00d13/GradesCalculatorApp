package ui.pages;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;

//Overrides basic progress square progress bar to a circle
public class AverageProgressCircle extends BasicProgressBarUI {

    //MODIFIES: this
    //EFFECTS: Sets preferred size of circle progress bar
    public Dimension getPreferredSize(JComponent component) {
        Dimension dimensions = super.getPreferredSize(component);
        int size = Math.max(dimensions.width,dimensions.height);
        dimensions.setSize(size,size);
        return dimensions;
    }

    //MODIFIES: this
    //EFFECTS: paints progress bar to create a circle instead of a rectangle
    public void paint(Graphics graphics, JComponent component) {
        Insets borderArea = progressBar.getInsets();
        int borderAreaRecWidth = progressBar.getWidth() - borderArea.right - borderArea.left;
        int borderAreaRecHeight = progressBar.getHeight() - borderArea.top - borderArea.bottom;
        if (borderAreaRecHeight <= 0 || borderAreaRecHeight <= 0) {
            return;
        }

        Graphics2D graphics2D = (Graphics2D) graphics.create();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setPaint(progressBar.getForeground());

        double degree = 360 * progressBar.getPercentComplete();
        double size = Math.min(borderAreaRecWidth, borderAreaRecHeight);
        double coX = borderArea.left + borderAreaRecWidth * 0.5;
        double coY = borderArea.top + borderAreaRecHeight * 0.5;
        double outRadius = size * 0.5;
        double inRadius = outRadius * 0.5;

        Shape innerCircle = new Ellipse2D.Double(coX - inRadius, coY - inRadius, inRadius * 2, inRadius * 2);
        Shape outerCircle = new Arc2D.Double(coX - outRadius, coY - outRadius,
                                             size, size, 90 - degree, degree, Arc2D.PIE);
        Area area = new Area(outerCircle);
        area.subtract(new Area(innerCircle));
        graphics2D.fill(area);
        graphics2D.dispose();

        checkIsStringPainted(graphics, borderArea, borderAreaRecWidth, borderAreaRecHeight);
    }

    //EFFECTS: Checks if progress bar is painted and if not then repaints it
    private void checkIsStringPainted(Graphics graphics,Insets borderArea, int borderAreaRecWidth,
                                      int borderAreaRecHeight) {
        if (progressBar.isStringPainted()) {
            paintString(graphics, borderArea.left, borderArea.top, borderAreaRecWidth,
                    borderAreaRecHeight, 0, borderArea);
        }
    }
}
