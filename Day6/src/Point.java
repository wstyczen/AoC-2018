import java.util.ArrayList;

public class Point{
    private int x;
    private int y;
    private int idClosest;

    public Point(int x, int y, ArrayList<Coordinate> cds) {
        this.x = x;
        this.y = y;
        this.idClosest = this.getClosestId(cds);
    }


    public int getIdClosest() {
        return idClosest;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public int getClosestId(ArrayList<Coordinate> cds)
    {
        int count=0;
        int shortestDist = Integer.MAX_VALUE;
        Coordinate cd = null;
        for (Coordinate cord : cds)
        {
            int dist = cord.getManhattanDistance(this);
            if (dist < shortestDist)
            {
                shortestDist = dist;
                cd = cord;
                count = 1;
            }
            else if (dist == shortestDist)
            {
                count++;
            }
        }
        if (count == 1)
            return cd.getId();
        else // tied
            return 0;
    }

}

