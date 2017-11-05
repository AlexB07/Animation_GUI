class Point
{
    public Point (double x, double y) {
        this.x = x;
        this.y = y;
    }

    int xInt() {
        return (int) Math.round(x);
    }

    int yInt() {
        return (int) Math.round(y);
    }
    
    public double x;
    public double y;
}
