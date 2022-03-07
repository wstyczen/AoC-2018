public class Coordinate {
    static int farLeft = Integer.MAX_VALUE;
    static int farRight = 0;
    static int top = 0;
    static int bottom = Integer.MAX_VALUE;

    private int id;
    private int x;
    private int y;

    public Coordinate(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        if (x < farLeft)
            farLeft = x;
        if (x > farRight)
            farRight = x;
        if (y > top)
            top = y;
        if (y < bottom)
            bottom = y;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getManhattanDistance(Point pt)
    {
        return Math.abs(pt.getX() - this.getX()) + Math.abs(pt.getY() - this.getY());
    }
    public boolean equals(Point p) {
        if (this.getX() == p.getX() && this.getY() == p.getY())
            return true;
        return false;
    }
}
