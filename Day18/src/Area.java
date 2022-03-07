import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Area {
    private char [][] map;
    private int minuteRequired;

    public Area(String fileName, int minuteReq) {
        readFromFile(fileName);
        this.minuteRequired = minuteReq;
    }

    public void setMinuteRequired(int minuteRequired) {
        this.minuteRequired = minuteRequired;
    }

    public void readFromFile(String fileName) {
        int lineCount = 0;
        int lineSize = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            lineSize = line.length();
            while (line != null) {
                lineCount++;
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        char[][] areaMap = new char[lineCount][lineSize];
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            int lineNr = 0;
            while (line != null) {
                char[] lineChars = new char[lineSize];
                for (int i = 0; i < lineSize; i++) {
                    lineChars[i] = line.charAt(i);
                }
                areaMap[lineNr] = lineChars;
                lineNr++;
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        this.map = areaMap;
    }

    public boolean isWithin(int x, int y) {
        return (x >= 0 && y >= 0 && x <= this.map[0].length - 1 && y <= this.map.length - 1);
    }

    public boolean containsEnoughChars(int x, int y, int nr, char c) {
        int count = 0;
        for (int j = y - 1; j <= y + 1; j++) {
            for (int i = x - 1; i <= x + 1; i++) {
                if (isWithin(i, j) && (i != x || j != y)) {
                    if (this.map[j][i] == c)
                        count++;
                }
            }
        }
        return count >= nr;
    }

    public void printMap() {
        for (char[] row : map) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public char [][] getDeepCopyOfMap() {
        char[][] newMap = new char[map.length][map[0].length];
        for (int y = 0; y < map.length; y++) {
            newMap[y] = Arrays.copyOf(map[y], map[0].length);
        }
        return newMap;
    }

    public void elapseTime() {
        //System.out.println("Start");
        //printMap();

        int minute = 0;
        while (minute++ < this.minuteRequired) {
            char [][] newMap = getDeepCopyOfMap();
            for (int y = 0; y < this.map.length; y++) {
                for (int x = 0; x < this.map[0].length; x++) {
                    switch (this.map[y][x]) {
                        case '.':
                            if (containsEnoughChars(x, y, 3, '|'))
                                newMap[y][x] = '|';
                            break;
                        case '|':
                            if (containsEnoughChars(x, y, 3, '#'))
                                newMap[y][x] = '#';
                            break;
                        case '#':
                            if (!(containsEnoughChars(x, y, 1, '#') && containsEnoughChars(x, y, 1, '|')))
                                newMap[y][x] = '.';
                            break;
                    }
                }
            }
            this.map = newMap;
            //System.out.println("Minute: " + minute);
//            printMap();
        }
    }

    public int getNumberOf(Character character) {
        int count = 0;
        for (char [] row : map) {
            for (char c : row)
                if (c == character)
                    count++;
        }
        return count;
    }

    // part 2;

    public ArrayList<char[][]> elapseTime(int minuteReq) {
        int minute = 0;
        ArrayList<char[][]> maps = new ArrayList<>();
        while (minute++ < minuteReq) {
            maps.add(this.map);
            char [][] newMap = getDeepCopyOfMap();
            for (int y = 0; y < this.map.length; y++) {
                for (int x = 0; x < this.map[0].length; x++) {
                    switch (this.map[y][x]) {
                        case '.':
                            if (containsEnoughChars(x, y, 3, '|'))
                                newMap[y][x] = '|';
                            break;
                        case '|':
                            if (containsEnoughChars(x, y, 3, '#'))
                                newMap[y][x] = '#';
                            break;
                        case '#':
                            if (!(containsEnoughChars(x, y, 1, '#') && containsEnoughChars(x, y, 1, '|')))
                                newMap[y][x] = '.';
                            break;
                    }
                }
            }
            this.map = newMap;
        }
        return maps;
    }

    public boolean areEqual(char[][] ar1, char [][] ar2) {
        for (int y = 0; y < ar1.length; y++) {
            if (!Arrays.equals(ar1[y], ar2[y]))
                return false;
        }
        return true;
    }

    public int getLengthOfCycle() {
        setMinuteRequired(1000);
        elapseTime();
        ArrayList<char[][]> maps = elapseTime(100);
        char [][] firstMap = maps.get(0);
        for (int i = 1; i < maps.size(); i++)
        {
            if (areEqual(firstMap, maps.get(i)))
                return i;
        }
        return -1;
    }

}

