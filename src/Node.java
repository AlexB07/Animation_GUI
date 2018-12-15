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
		//works out the length of a line given two points
		length = (Math.pow((two.getX() - one.getX()), 2) + (Math.pow((two.getY() - one.getY()), 2)));
		length = Math.sqrt(length);
	}
	/*Created a new node without length*/
	public Node(int radius, Color color) {
		super(radius, color);
	}
	/*Returns point one*/
	public Point getPointOne() {
		return this.one;
	}
	/*Returns point two*/
	public Point getPointTwo() {
		return this.two;
	}
	/*return lenth of the line*/
	public double getLength() {
		return this.length;
	}
	/*sets the point of the node from a point*/
	public void updatePos(Point point) {
		setCenterX(point.getX());
		setCenterY(point.getY());

	}

}
