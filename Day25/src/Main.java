import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        ArrayList<Point> pts = getInput("Day25.txt");
        ArrayList<Constellation> constellations = mergeIntoConstellations(pts);
        System.out.println(constellations.size());
    }

    public static ArrayList<Constellation> mergeIntoConstellations(ArrayList<Point> pts) {
        ArrayList<Constellation> constellations = new ArrayList<>();
        for (Point pt : pts) {
            if (constellations.size() == 0) {
                constellations.add(new Constellation(pt));
            } else {
                boolean foundViableConstellation = false;
                for (Constellation constellation : constellations) {
                    if (constellation.checkIfPointIsInRange(pt)) {
                        constellation.addAPoint(pt);
                        foundViableConstellation = true;
                        break;
                    }
                }
                if (!foundViableConstellation)
                    constellations.add(new Constellation(pt));
            }
        }
        mergePossible(constellations);
        return constellations;
    }

    public static void mergePossible(ArrayList<Constellation> constellations) {
        while (checkForMerges(constellations));
    }

    public static boolean checkForMerges(ArrayList<Constellation> constellations) {
        for (int j = 0; j < constellations.size(); j++) {
            for (int i = j + 1; i < constellations.size(); i++) {
                if (constellations.get(j).canBeMergedIn(constellations.get(i))) {
                    constellations.get(j).mergeIn(constellations.get(i));
                    constellations.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<Point> getInput(String filename) {
        ArrayList<Point> pts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            Pattern pattern = Pattern.compile("-??\\d+");
            Matcher matcher = pattern.matcher(line);
            while (line != null) {
                int [] coordinates = new int [4];
                matcher.reset(line);
                int i = 0;
                while (matcher.find()) {
                    coordinates[i] = Integer.parseInt(matcher.group(0));
                    i++;
                }
                line = reader.readLine();
                pts.add(new Point(coordinates));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return pts;
    }

    public static int compareInts(int n1, int n2) {
        if (n1 > n2)
            return 1;
        else if (n1 == n2)
            return 0;
        else
            return -1;
    }

}

class Constellation {
    ArrayList<Point> points = new ArrayList<>();

    public Constellation(Point pt) {
        this.points.add(pt);
    }

    public Constellation(Constellation constellation) {
        this.points = constellation.getPoints();
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void addAPoint(Point pt) {
        this.points.add(pt);
    }

    public boolean checkIfPointIsInRange(Point pt) {
        for (Point point : this.points) {
            if (pt.getManhattanDistance(point) <= 3) {
                return true;
            }
        }
        return false;
    }

    public boolean canBeMergedIn(Constellation constellation) {
        for (Point pt : this.getPoints()) {
            for (Point otherPt : constellation.getPoints()) {
                if (pt.getManhattanDistance(otherPt) <= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    public void mergeIn(Constellation constellation) {
        this.points.addAll(constellation.getPoints());
    }
}

class Point implements Comparable<Point> {
    private int [] coordinates = new int [4];

    public Point(int[] coordinates) {
        this.coordinates = coordinates;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public int getManhattanDistance(Point pt) {
        int distance = 0;
        for (int i = 0; i < this.getCoordinates().length; i++) {
            distance += Math.abs(pt.getCoordinates()[i] - this.getCoordinates()[i]);
        }
        return distance;
    }

    @Override
    public int compareTo(Point pt) {
        int thisDist = getManhattanDistance(new Point(new int[]{0, 0, 0, 0}));
        int ptDist = getManhattanDistance(new Point(new int[]{0, 0, 0, 0}));
        if (thisDist > ptDist) {
            return 1;
        } else if (thisDist == ptDist) {
            return 0;
        } else {
            return -1;
        }
    }
}
