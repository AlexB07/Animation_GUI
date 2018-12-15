import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Node extends Circle {

	private Point one;
	private Point two;
	private double length;

	/*Creates a node with the constructor from circle*/
	public Node(int radius, Color color, Point one, Point two) {
		super(radius, color);
		this.one = one;
		this.two = two;
		length = (Math.pow((two.x - one.x), 2) + (Math.pow((two.y - one.y), 2)));
		length = Math.sqrt(length);
	}

	public Node(int radius, Color color) {
		super(radius, color);
	}

	public Point getPointOne() {
		return this.one;
	}

	public Point getPointTwo() {
		return this.two;
	}

	public double getLength() {
		return this.length;
	}

	public void updatePos(Point point) {
		setCenterX(point.x);
		setCenterY(point.y);

	}

}
