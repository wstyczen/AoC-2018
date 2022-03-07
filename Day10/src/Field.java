import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Field {
    private List<Point> points = new ArrayList<>();
    private static int lowest = Integer.MAX_VALUE;
    private static int highest = Integer.MIN_VALUE;
    private static int farRight = Integer.MIN_VALUE;
    private static int farLeft = Integer.MAX_VALUE;

    public Field(String fileName) {
        this.readFromFile(fileName);
    }

    public void readFromFile(String fileName)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line = br.readLine();
            while (line != null)
            {
                int [] posAndVels = new int[4];
                Matcher mt = Pattern.compile("(-?\\d+)").matcher(line);
                int count = 0;
                while (mt.find())
                {
                    posAndVels[count] = Integer.parseInt(mt.group(1));
                    count++;
                }
                this.points.add(new Point(posAndVels[0], posAndVels[1], posAndVels[2], posAndVels[3]));
                line = br.readLine();
            }
        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void sortPoints()
    {
        Collections.sort(this.points);
    }

    public void advance()
    {
        for (Point pt : points)
        {
            pt.advance();
        }
    }

    public boolean anySolitaries()
    {
        for (Point pt : points)
            if (pt.checkIfSolitary())
                return true;
        return false;
    }

    public void simulate()
    {
        int second = 0;
        while (anySolitaries())
        {
//            if (second == 0)
//                System.out.println("Initially:");
//            else
//            {
//                System.out.print("After " + second + " second");
//                if (second > 1)
//                    System.out.print("s");
//                System.out.println(":");
//            }
//
//            printField();

            advance();
            second++;
        }
        System.out.println("Result achieced after " + second + " seconds:");
        printField();
    }

    public void updateCorners()
    {
        for (Point pt : points)
        {
            if (pt.getX() > farRight)
                farRight = pt.getX();
            if (pt.getX() < farLeft)
                farLeft = pt.getX();
            if (pt.getY() > highest)
                highest = pt.getY();
            if (pt.getY() < lowest)
                lowest = pt.getY();
        }
    }

    public void printField()
    {
        //Collections.sort(this.points);
        updateCorners();
        for (int i = lowest; i <= highest; i++)
        {
            for (int j = farLeft; j <= farRight; j++)
            {
                if (points.contains(new Point(j, i, 0, 0)))
                    System.out.print("#");
                else
                    System.out.print(".");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isAPoint(int x, int y)
    {
        return this.points.contains(new Point(x, y, 0, 0));
    }

    public void printInfo()
    {
        for (Point pt : points) {
            System.out.print("Point - " + pt.getX() + " " + pt.getY() + " ");
            if (pt.checkIfSolitary())
                System.out.println(" is solitary.");
            else
                System.out.println(" is not solitary.");
        }
        System.out.println("Any solitaries -> " + anySolitaries());
    }

    public class Point implements Comparable<Point>{
        int x;
        int y;
        int velX;
        int velY;

        public Point(int x, int y, int velX, int velY) {
            this.x = x;
            this.y = y;
            this.velX = velX;
            this.velY = velY;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void advance()
        {
            this.x += this.velX;
            this.y += this.velY;
        }

        public boolean checkIfSolitary()
        {
            for (Point pt : points)
            {
                int dist = Math.abs(pt.getX() - this.x) + Math.abs(pt.getX() + this.x);
                if (dist == 1 || dist == 2)
                    return false;
            }
            return true;
        }

        @Override
        public boolean equals(Object obj) {
            if (this.getClass() != obj.getClass())
                return false;
            Point pt = (Point) obj;
            return (pt.getX() == this.getX() && pt.getY() == this.getY());
        }

        @Override
        public int compareTo(Point p) {
            if (this.getY() < p.getY())
                return 1;
            else if (this.getY() == p.getY())
            {
                if (this.getX() < p.getX())
                    return 1;
                else if (this.getX() == p.getX())
                    return 0;
                return -1;
            }
            return -1;
        }
    }


}
