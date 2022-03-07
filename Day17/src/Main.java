import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static char [][] map;
    public static int sourceX;
    public static int[] borderValues;

    public static void main(String[] args) {
        readInput("Day17-test.txt");
//        readInput("Day17.txt");
        move();
//        printMap();
        System.out.println(getPart1());
    }

    public static void printMap() {
        for (int j = 0; j < map.length; j++)
        {
            for (int i = 0; i < map[0].length; i++) {
                char x = map[j][i];
                if (x == 0)
                    System.out.print('.');
                else
                    System.out.print(x);
            }
            System.out.println();
        }
    }

    public static void readInput(String fileName) {
        List<Character> firstCoords = new ArrayList<>();
        List<Integer[]> coords = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while (line != null) {
                Matcher m = Pattern.compile("\\d+")
                        .matcher(line);
                firstCoords.add(line.charAt(0));
                Integer[] ints = new Integer[3];
                int index = 0;
                while (m.find()) {
                    ints[index] = Integer.parseInt(m.group());
                    index++;
                }
                coords.add(ints);
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        borderValues = getBorderValues(firstCoords, coords);
        map = new char [borderValues[3] - borderValues[2] + 2][borderValues[1] - borderValues[0] + 1];
        for (int i = 0; i < firstCoords.size(); i++) {
                char firstCoord = firstCoords.get(i);
                Integer [] vals = coords.get(i);
                for (int j = vals[1]; j <= vals[2]; j++) {
                    if (firstCoord == 'x') {
                        map[j-borderValues[2] + 1][vals[0] - borderValues[0]] = '#';
                    } else {
                        map[vals[0] - borderValues[2] + 1][j - borderValues[0]] = '#';
                    }
                }
        }
        sourceX = 500 - borderValues[0];
        map[0][sourceX] = '+';
        fillEmptyWithDots();
    }

    public static int[] getBorderValues(List<Character> firstCoords, List<Integer[]> coords) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int i = 0; i < firstCoords.size(); i++) {
            int i1 = coords.get(i)[0];
            int i2 = coords.get(i)[1];
            int i3 = coords.get(i)[2];
            if (firstCoords.get(i) == 'x') {
                if (minX > i1)
                    minX = i1;
                if (maxX < i1)
                    maxX = i1;
                if (minY > i2)
                    minY = i2;
                if (maxY < i3)
                    maxY = i3;
            } else {
                if (minY > i1)
                    minY = i1;
                if (maxY < i1)
                    maxY = i1;
                if (minX > i2)
                    minX = i2;
                if (maxX < i3)
                    maxX = i3;
            }
        }
        return new int[]{minX, maxX, minY, maxY};
    }

    public static void fillEmptyWithDots() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 0)
                    map[i][j] = '.';
            }
        }
    }

    public static void move() {
        move(sourceX, 0);
    }

    public static boolean isWithinArea(int x, int y) {
        return (y > 0 && y < map.length && x >= 0 && x < map[0].length);
    }

    public static boolean isOnTheEdgeOfArea(int x, int y) {
        return (y == 1 || y == map.length - 1 || x == 0 || x == map[0].length - 1);
    }

    public static boolean reachable(int x, int y) {
        if (!isWithinArea(x, y))
            return false;
        return ("#~|".indexOf(map[y][x]) == -1);
    }

    public static boolean isWall(int x, int y) {
        if (!isWithinArea(x, y))
            return false;
        return map[y][x] == '#';
    }
    public static boolean hasWalls(int x, int y) {
        return (hasWall(x, y, 1) && hasWall(x, y, -1));
    }
    public static boolean hasWall(int x, int y, int diff) {
        while (true) {
            if (map[y + 1][x] == '.')
                return false;
            if (map[y][x] == '#')
            {
                break;
            }
            x += diff;
        }
        return true;
    }

    public static void move(int x, int y) {
        //printMap();
        while (reachable(x, y + 1)) {
            y++;
            map[y][x] = '|';
        }
        map[y][x] = '~';
        int startingX = x;
        while (reachable(x + 1, y) && !reachable(x, y + 1) && !isOnTheEdgeOfArea(x, y)) {
            x++;
            map[y][x] = '~';
        }
        if (!isWall(x + 1, y) && !isOnTheEdgeOfArea(x, y)) {
            move(x, y);
        }
        x = startingX;
        while (reachable(x - 1, y) && !reachable(x, y + 1) && !isOnTheEdgeOfArea(x, y)) {
            x--;
            map[y][x] = '~';
        }
        if (!isWall(x - 1, y) && !isOnTheEdgeOfArea(x, y)) {
            move(x, y);
        }
        x = startingX;
        if (!isOnTheEdgeOfArea(x, y) && hasWalls(x, y))
            move(x, y - 1);
    }

    public static int getPart1() {
        int total = 0;
        for (char [] row : map) {
            for (char c : row)
                if (c == '~' || c == '|')
                    total++;
        }
        return total;
    }

    public static void writeMap(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (char [] row : map) {
                for (char c : row) {
                    writer.write(c);
                }
                writer.write("\n");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
