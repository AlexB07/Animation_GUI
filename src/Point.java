import javafx.scene.shape.Circle;

/** Simple class to hold a point position */
class Point
{
    public Point (double x, double y) {
        this.x = x;
        this.y = y;
    }
    /*Update x and y co-ordinates */
    public void updatePoint(double x, double y) {
    	this.x = x;
    	this.y = y;
    }
    /*Takes center point from nodes and updates point  */
    public void updatePointFromNode(Circle circle) {
    	this.x = circle.getCenterX();
    	this.y = circle.getCenterY();
    }

    public double x;
    public double y;
}
