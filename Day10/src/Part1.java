import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part1 {
    int [][] positionsAndVelocities;

    public Part1(String fileName) {
        readFromFile(fileName);
    }

    public void readFromFile(String fileName)
    {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) lines++;
        } catch (IOException e) {
            e.printStackTrace();
        }

        positionsAndVelocities = new int[lines][4];


        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line = br.readLine();
            int lineCount = 0;
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
                positionsAndVelocities[lineCount] = posAndVels;
                lineCount++;
                line = br.readLine();
            }
        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void advance()
    {
        for (int[] row : positionsAndVelocities)
        {
            row[0] += row[2];
            row[1] += row[3];
        }
    }

    public static int calculateManhattanDistance(int x1, int y1, int x2, int y2)
    {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public boolean isIsolated(int x, int y) {
        for (int[] row : this.positionsAndVelocities) {
            int dist = calculateManhattanDistance(x, y, row[0], row[1]);
            if (dist == 1 || dist == 2)
                return false;
        }
        return true;
    }

    public boolean areAnyIsolated()
    {
        for (int[] row : positionsAndVelocities)
        {
            if (isIsolated(row[0], row[1]))
                return true;
        }
        return false;
    }

    public int simulate(String fileName)
    {
        int second = 0;
        boolean unfinished = true;
        while (unfinished)
        {
            advance();
            second++;
            unfinished = areAnyIsolated();
        }
        writeResult(fileName);
        return second;
    }

    public int[] getCorners()
    {
        int [] corners = {Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE};
        // highest, lowest, farRight, farLeft
        for (int[] row : positionsAndVelocities)
        {
            if (row[1] > corners[0]) // highest
                corners[0] = row[1];
            if (row[1] < corners[1]) // lowest
                corners[1] = row[1];
            if (row[0] > corners[2]) // farRight
                corners[2] = row[0];
            if (row[0] < corners[3]) // farLeft
                corners[3] = row[0];
        }
        return corners;
    }

    public boolean isPresent(int x, int y) {
        for (int[] row : positionsAndVelocities) {
            if (row[0] == x && row[1] == y)
                return true;
        }
        return false;
    }

    public void writeResult(String fileName)
    {
        int[] corners = getCorners();
        try (FileWriter filewriter = new FileWriter(fileName)) {
            for (int j = corners[1]; j <= corners[0]; j++) // from lowest to highest
            {
                for (int i=corners[3]; i <= corners[2]; i++) // from farLeft to farRight
                {
                    if (isPresent(i, j))
                    {
                        filewriter.write("#");
                    }
                    else
                    {
                        filewriter.write(".");
                    }
                }
                filewriter.write("\n");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
