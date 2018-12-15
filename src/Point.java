
/** Simple class to hold a point position */
class Point {

	public Point() {

	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Point(double x, double y, Point p) {
		this.x = x;
		this.y = y;
		this.length = (Math.pow((p.x - x), 2) + (Math.pow((p.y - y), 2)));
		this.length = Math.sqrt(this.length);
	}

	/* Update x and y co-ordinates */
	public void updatePoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void updatePointFromOther(Point other) {
		this.x = other.x;
		this.y = other.y;
	}

	/* Takes center point from nodes and updates point */
	public void updatePointFromNode(Node circle) {
		this.x = circle.getCenterX();
		this.y = circle.getCenterY();
	}

	public double getLength() {
		return this.length;
	}

	public double x;
	public double y;
	private double length;
}
