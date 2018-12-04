/** Simple class to hold a point position */
class Point
{
    public Point (double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void updatePoint(double x, double y) {
    	this.x = x;
    	this.y = y;
    }

    public double x;
    public double y;
}
