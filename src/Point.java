	

/** Simple class to hold a point position */
class Point {

	private double x;
	private double y;
	/* Creates new point */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/* Update x and y co-ordinates */
	public void updatePoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/* Update point from another point */
	public void updatePointFromOther(Point other) {
		this.x = other.x;
		this.y = other.y;
	}

	/* Takes center point from nodes and updates point */
	public void updatePointFromNode(Node circle) {
		this.x = circle.getCenterX();
		this.y = circle.getCenterY();
	}
	/*Return x*/
	public double getX() {
		return this.x;
	}
	/*Sets x equal to its self plus value*/
	public void setX(double x) {
		this.x += x;
	}
	/*Return y*/
	public double getY() {
		return this.y;
	}
	/*Sets y equal to its self plus value*/
	public void setY(double y) {
		this.y += y;
	}



}
