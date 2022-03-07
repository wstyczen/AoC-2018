import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        long[][] input = readInput("Day23.txt");
        System.out.println(getPart1(input));
        long [] best = getBest(input);
        System.out.println(best[0] + best[1] + best[2]);
    }

    public static int getNrOfLinesInAFile(String fileName) {
        int noOfLines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while (reader.readLine() != null) {
                noOfLines++;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return noOfLines;
    }

    public static long[][] readInput(String fileName) {
        long[][] input;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int lineCount = getNrOfLinesInAFile(fileName);
            input = new long[lineCount][4];
            String line = reader.readLine();
            Pattern pattern = Pattern.compile("-??\\d+");
            Matcher matcher = pattern.matcher(line);
            lineCount = 0;
            while (line != null) {
                long[] lineNrs = new long[4];
                matcher.reset(line);
                int index = 0;
                while (matcher.find()) {
                    lineNrs[index] = Integer.parseInt(matcher.group(0));
                    index++;
                }
                input[lineCount] = lineNrs;
                lineCount++;
                line = reader.readLine();
            }
            return input;
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public static long getManhattanDistance(long[] nanobot1, long[] nanobot2) {
//        long distance = 0;
//        for (int i = 0; i < nanobot1.length - 1; i++) {
//            distance += Math.abs(nanobot1[i] - nanobot2[i]);
//        }
//        return distance;
        return Math.abs(nanobot1[0] - nanobot2[0]) + Math.abs(nanobot1[1] - nanobot2[1]) + Math.abs(nanobot1[2] - nanobot2[2]);
    }

    public static long[] getStrongestNanobot(long[][] nanobots) {
        long[] strongestNanobot = new long[4];
        for (long[] nanobot : nanobots) {
            if (strongestNanobot[3] == 0 || strongestNanobot[3] < nanobot[3])
                strongestNanobot = nanobot;
        }
        return strongestNanobot;
    }

    public static long getHowManyInRange(long[][] nanobots, long[] strongest) {
        long count = 0;
        for (long[] nanobot : nanobots) {
            long distance = getManhattanDistance(nanobot, strongest);
            if (distance <= strongest[3]) {
                count++;
            }
        }
        return count;
    }

    public static long getPart1(long[][] input) {
        return getHowManyInRange(input, getStrongestNanobot(input));
    }

    public static long[] getMins(long[][] input) {
        long minX = Integer.MAX_VALUE;
        long minY = Integer.MAX_VALUE;
        long minZ = Integer.MAX_VALUE;
        for (long[] bot : input) {
            if (bot[0] < minX)
                minX = bot[0];
            if (bot[1] < minY)
                minY = bot[1];
            if (bot[2] < minZ)
                minZ = bot[2];
        }
        return new long[]{minX, minY, minZ};
    }

    public static long[] getMaxes(long[][] input) {
        long minX = Integer.MIN_VALUE;
        long minY = Integer.MIN_VALUE;
        long minZ = Integer.MIN_VALUE;
        for (long[] bot : input) {
            if (bot[0] > minX)
                minX = bot[0];
            if (bot[1] > minY)
                minY = bot[1];
            if (bot[2] > minZ)
                minZ = bot[2];
        }
        return new long[]{minX, minY, minZ};
    }

    public static long howManyInRange(long[][] input, long[] position) {
        long inRange = 0;
        for (long[] bot : input) {
            if (getManhattanDistance(bot, position) <= bot[3])
                inRange += 1;
        }
        return inRange;
    }

    public static long [] getBest(long[][] input) {
        long [][] bounds = getBoundsWithIncrement(input, 100000000, getMins(input), getMaxes(input));
        bounds = getBoundsWithIncrement(input, 10000000, bounds[0], bounds[1]);
        bounds = getBoundsWithIncrement(input, 1000000, bounds[0], bounds[1]);
        bounds = getBoundsWithIncrement(input, 100000, bounds[0], bounds[1]);
        bounds = getBoundsWithIncrement(input, 10000, bounds[0], bounds[1]);
        bounds = getBoundsWithIncrement(input, 1000, bounds[0], bounds[1]);
        bounds = getBoundsWithIncrement(input, 100, bounds[0], bounds[1]);
        bounds = getBoundsWithIncrement(input, 10, bounds[0], bounds[1]);
        long [] best = getBestPoint(input, bounds[0], bounds[1]);
        return best;
    }

    public static long[] getBestPoint(long[][] input, long [] lower, long [] upper) {
        long bestInRange = 0;
        long[] best = null;
        for (long x = lower[0]; x < upper[0]; x += 1) {
            for (long y = lower[1]; y < upper[1]; y += 1) {
                for (long z = lower[2]; z < upper[2]; z += 1) {
                    long[] pos = {x, y, z};
                    long inRange = howManyInRange(input, pos);
                    if (inRange > bestInRange) {
                        best = new long[]{x, y, z};
                        bestInRange = inRange;
                    }
                }
            }
        }
        return best;
    }

    public static long[][] getBoundsWithIncrement(long[][] input, int increment, long [] lower, long [] upper) {
        long bestInRange = 0;
        long[] lowerBounds = {};
        long[] upperBounds = {};
        for (long x = lower[0]; x < upper[0]; x += increment) {
            for (long y = lower[1]; y < upper[1]; y += increment) {
                for (long z = lower[2]; z < upper[2]; z += increment) {
                    long[] pos = {x, y, z};
                    long inRange = howManyInRange(input, pos);
                    if (inRange > bestInRange) {
                        lowerBounds = new long[]{x - increment, y - increment, z - increment};
                        upperBounds = new long[]{x + increment, y + increment, z + increment};
                        bestInRange = inRange;
                    }
                }
            }
        }
        return new long[][]{lowerBounds, upperBounds};
    }
}