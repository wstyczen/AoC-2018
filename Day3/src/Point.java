import java.util.HashSet;
import java.util.Set;

public class Point extends Position implements Comparable<Point>{
    Set<Integer> overlap = new HashSet<>();

    public Point(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass())
            return false;
        Point p = (Point) obj;
        if (this.getX() == p.getX() && this.getY() == p.getY())
            return true;
        return false;
    }

    public void addToOverlap(int id)
    {
        this.overlap.add(id);
    }

    @Override
    public int compareTo(Point p) {
        if (this.getY() < p.getY())
        {
            return -1;
        }
        if (this.getY() == p.getY())
        {
            if (this.getX() == p.getX())
                return 0;
            else if (this.getX() < p.getX())
                return -1;
            else
                return 1;
        }
        return 1;
    }

    public int getOverlapSize()
    {
        return this.overlap.size();
    }
}
