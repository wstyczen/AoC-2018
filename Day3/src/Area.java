import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Area {
    private ArrayList<Claim> claims = new ArrayList<>();
    private List<Point> pts = new ArrayList<>();

    public Area(String fileName) {
        this.readClaims(fileName);
        this.simulateAreas();
        Collections.sort(pts);
    }

    public void readClaims(String fileName) {
        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] claimData = line.split(" ");
                int id = Integer.parseInt(claimData[0].substring(1));
                int x = Integer.parseInt(claimData[2].substring(0, claimData[2].indexOf(",")));
                int y = Integer.parseInt(claimData[2].substring(claimData[2].indexOf(",") + 1, claimData[2].indexOf(":")));
                int height = Integer.parseInt(claimData[3].substring(0, claimData[3].indexOf("x")));
                int width = Integer.parseInt(claimData[3].substring(claimData[3].indexOf("x") + 1));
                this.claims.add(new Claim(id, new Position(x, y), height, width));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void simulateAreas()
    {
        for (Claim claim : claims)
        {
            for (int i = claim.getStartPoint().getX(); i < claim.getStartPoint().getX() + claim.getWidth(); i++)
            {
                for (int j = claim.getStartPoint().getY(); j < claim.getStartPoint().getY() + claim.getHeight(); j++)
                {
                    if (this.pts.contains(new Point(i, j)))
                    {
                        this.pts.get(this.pts.indexOf(new Point(i, j))).addToOverlap(claim.getId());
                    }
                    else
                    {
                        Point p = new Point(i, j);
                        p.addToOverlap(claim.getId());
                        pts.add(p);
                    }
                }
            }
        }
    }


    public int getPart1Result()
    {
        int nr = 0;
        for (Point pt : this.pts)
        {
            if (pt.getOverlapSize() > 1)
            {
               nr++;
            }
        }
        return nr;
    }
}
