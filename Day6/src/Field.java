import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Field {
    private ArrayList<Coordinate> cords = new ArrayList<>();
    private ArrayList<Point> pts = new ArrayList<>();
    private Set<Integer> excluded = new HashSet<>();
    private Map<Integer, Integer> counts = new HashMap<>();

    public Field(String fileName) {
        this.getCoordinates(fileName);
        this.simulateField();
        this.getCounts();
    }

    public boolean checkIfPointIsCoordinate(Point p)
    {
        for (Coordinate c : this.cords)
        {
            if (c.equals(p))
                return true;
        }
        return false;
    }

    public void getCoordinates(String fileName)
    {
        BufferedReader br;
        int lineId = 1;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            while (line != null)
            {
                String [] cs = line.split(", ");
                int x = Integer.parseInt(cs[0]);
                int y = Integer.parseInt(cs[1]);
                this.cords.add(new Coordinate(lineId, x, y));
                lineId++;
                line = br.readLine();
            }
            br.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void simulateField()
    {
        for (int j = Coordinate.bottom - 1; j <= Coordinate.top + 1; j++)
        {
            for (int i=Coordinate.farLeft - 1; i <= Coordinate.farRight + 1; i++)
            {
                Point p = new Point(i, j, this.cords);
//                if (!this.checkIfPointIsCoordinate(p))
                    this.pts.add(p);
                if (i <= Coordinate.farLeft || i >= Coordinate.farRight ||
                        j <= Coordinate.bottom || j >= Coordinate.top)
                    excluded.add(p.getIdClosest());
            }
        }
    }

    public void getCounts()
    {
        for (Point pt : this.pts)
        {
            int closest = pt.getIdClosest();
            if (!this.excluded.contains(closest))
            {
                if (counts.containsKey(closest))
                {
                    counts.put(closest, counts.get(closest) + 1);
                }
                else
                    counts.put(closest, 1);
            }
        }
    }

    public void printGrid()
    {
        int y = this.pts.get(0).getY();
        for (Point p : this.pts)
        {
            if (p.getY() != y)
            {
                System.out.print("\n");
                y = p.getY();
            }
            System.out.print(p.getIdClosest());
        }
    }

    public int getSizeOfBiggestField()
    {
        int biggest = 0;
        for (Integer size : this.counts.values())
        {
            if (size > biggest)
                biggest = size;
        }
        return biggest;
    }

    public int getPartTwo(int reqDistance)
    {
        int fieldSize=0;
        for (Point pt : this.pts)
        {
            int total=0;
            for (Coordinate cd : this.cords)
            {
                total += cd.getManhattanDistance(pt);
            }
            if (total < reqDistance)
                fieldSize++;
        }
        return fieldSize;
    }
}
