import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/** JPanel which we can draw our figure on */
public class Canvas extends JPanel
{
    /* Set up some initial positions for the parts of the stick man */
    Point leftHand = new Point(50, 100);
    Point shoulder = new Point(100, 100);
    Point rightHand = new Point(150, 100);
    Point head = new Point(100, 50);
    Point posterior = new Point(100, 150);
    Point leftFoot = new Point(65, 200);
    Point rightFoot = new Point(135, 200);
    
    public Canvas(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));

	/* Set up a listener so that we hear about mouse clicks on the canvas */
        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse pressed at " + e.getX() + " " + e.getY());
            }

            public void mouseReleased(MouseEvent e) {
            }
        });

	/* Set up a listener so that we hear about mouse movement on the canvas */
        addMouseMotionListener(new MouseMotionListener() {
            public void mouseMoved(MouseEvent e) {
                
            }
            
            public void mouseDragged(MouseEvent e) {
                System.out.println("Mouse dragged to " + e.getX() + " " + e.getY());
            }
        });
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        /* Draw the stick man at its current coordinates */
        g2.drawLine(leftHand.xInt(), leftHand.yInt(), shoulder.xInt(), shoulder.yInt());
        g2.drawLine(rightHand.xInt(), rightHand.yInt(), shoulder.xInt(), shoulder.yInt());
        g2.drawLine(leftFoot.xInt(), leftFoot.yInt(), posterior.xInt(), posterior.yInt());
        g2.drawLine(rightFoot.xInt(), rightFoot.yInt(), posterior.xInt(), posterior.yInt());
        g2.drawLine(head.xInt(), head.yInt(), shoulder.xInt(), shoulder.yInt());
        g2.drawLine(posterior.xInt(), posterior.yInt(), shoulder.xInt(), shoulder.yInt());
        g2.fillOval(head.xInt() - 20, head.yInt() - 20, 40, 40);
    }
}
