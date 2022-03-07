import java.util.*;

public class Cave {

    public class Coordinates {
        int x;
        int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public enum RegionType {
        ROCKY(0), //0
        NARROW(1), //1
        WET(2); // 2

        private int value;

        RegionType(int val) {
            this.value = val;
        }

        public int getValue() {
            return value;
        }

        public char getAlternate() {
            switch (value) {
                case 0:
                    return '.';
                case 1:
                    return '|';
                case 2:
                    return '=';
            }
            return ' ';
        }

        public int getRiskLevel() {
            switch (value) {
                case 0:
                    return 0;
                case 1:
                    return 2;
                case 2:
                    return 1;
            }
            return -1;
        }

    }

    private int depth;
    private Coordinates target;
    private int [][] erosionLevels;
    private RegionType [][] regions;

    public Cave(int depth, int targetX, int targetY, int fieldHeight, int fieldWidth) {
        this.depth = depth;
        this.target = new Coordinates(targetX, targetY);
        generateErosionLevels(fieldHeight, fieldWidth); // generates all levels before the target
        // recursively - as they are required to compute this one
        generateRegionTypes(fieldHeight, fieldWidth);
    }

    public void generateErosionLevels(int height, int width) {
        erosionLevels = new int [height][width];
        for (int i = 0; i < erosionLevels.length; i++) {
            for (int j = 0; j < erosionLevels[0].length; j++) {
                generateErosionLevel(j, i);
            }
        }
    }
    public int generateErosionLevel(int x, int y) {
        if (erosionLevels[y][x] != 0) {
            return erosionLevels[y][x];
        }
        if ((x == 0 && y == 0) || (x == target.x && y == target.y)) {
            erosionLevels[y][x] = getErosionLevel(0);
            return getErosionLevel(0);
        } else if (y == 0) {
            erosionLevels[y][x] = getErosionLevel(x*16807);
            return getErosionLevel(x*16807);
        } else if (x == 0) {
            erosionLevels[y][x] = getErosionLevel(y*48271);
            return getErosionLevel(y*48271);
        }
        int geoIndex = generateErosionLevel(x - 1, y)*generateErosionLevel(x, y - 1);
        erosionLevels[y][x] = getErosionLevel(geoIndex);
        return getErosionLevel(geoIndex);
    }
    int getErosionLevel(int geologicalLevel) {
        return (geologicalLevel + depth) % 20183;
    }
    public void generateRegionTypes(int height, int width) {
        regions = new RegionType[height][width];
        for (int i = 0; i < regions.length; i++) {
            for (int j = 0; j < regions[0].length; j++) {
                switch (erosionLevels[i][j] % 3) {
                    case 0:
                        regions[i][j] = RegionType.ROCKY;
                        break;
                    case 1:
                        regions[i][j] = RegionType.WET;
                        break;
                    case 2:
                        regions[i][j] = RegionType.NARROW;
                        break;
                }
            }
        }
    }

    public void printRegions() {
        for (int y = 0; y < regions.length; y++) {
            for (int x = 0; x < regions[0].length; x++) {
                if (y == 0 && x == 0) {
                    System.out.print("M");
                } else if (x == target.x && y == target.y) {
                    System.out.print("T");
                } else {
                    System.out.print(regions[y][x].getAlternate());
                }
            }
            System.out.println();
        }
    }

    public int getRiskLevel() {
        int riskLevel = 0;
        for (RegionType[] region : regions) {
            for (int j = 0; j < regions[0].length; j++) {
                riskLevel += region[j].getRiskLevel();
            }
        }
        return riskLevel;
    }

    // PART2

    public boolean isWithinRegions(Coordinates c) {
        return (c.x >= 0 && c.x < regions[0].length && c.y >= 0 && c.y < regions.length);
    }
    public ArrayList<Coordinates> getAdjacent(Coordinates c) {
        ArrayList<Coordinates> adjacent = new ArrayList<>();
        adjacent.add(new Coordinates(c.x, c.y + 1));
        adjacent.add(new Coordinates(c.x  + 1, c.y));
        adjacent.add(new Coordinates(c.x, c.y - 1));
        adjacent.add(new Coordinates(c.x - 1, c.y));
        return adjacent;
    }

    public Set<Integer[]> getUnvisitedSet() {
        // state = {y, x, toolValue} - toolValue equal to the value of a terrain that cant be traversed with it
        Set<Integer[]> unvisited = new HashSet<>();
        for (int i = 0; i < regions.length; i++) {
            for (int j = 0; j < regions[0].length; j++) {
                if (i == target.y && j == target.x) {
                    unvisited.add(new Integer[]{target.y, target.x, 2});
                } else
                    for (int t = 0; t < 3; t++) {
                        if (regions[i][j].getValue() != t && !(i == 0 && j == 0))
                            unvisited.add(new Integer[]{i, j, t});
                }
            }
        }
        return unvisited;
    }

    public Map<Integer[], Integer> getDistances(Integer[] startingNode) {
        Set<Integer[]> unvisited = getUnvisitedSet();
        Map<Integer[], Integer> distances = new HashMap<>();
        distances.put(startingNode, 0);
        for (Integer[] state : unvisited) {
            distances.put(state, Integer.MAX_VALUE);
        }
        return distances;
    }

    public Set<Integer[]> getUnvisitedNeighbours(Integer[] currentNode, Map<Integer[], Integer> distances, Set<Integer []> visited) {
        Set<Integer []> unvisitedNeighbours = new HashSet<>();
        for (Integer[] node : distances.keySet()) {
            if (!visited.contains(node))
                if (Math.abs(node[0] - currentNode[0]) + Math.abs(node[1] - currentNode[1]) == 1) {
                    unvisitedNeighbours.add(node);
                }
        }
        return unvisitedNeighbours;
    }

    public Integer[] getSmallestCostNode(Map<Integer[], Integer> distances, Set<Integer []> visited) {
        Integer [] smallestCostNode = new Integer[]{0, 0, 0};
        int smallestCost = Integer.MAX_VALUE;
        for (Integer [] node : distances.keySet()) {
            if (!visited.contains(node) && distances.get(node) < smallestCost) {
                smallestCostNode = node;
                smallestCost = distances.get(smallestCostNode);
            }
        }
        return smallestCostNode;
    }

    public int djikstra() {
        // Neither = 0, ClimbingGear = 1, Torch = 2;
        // Node = {y, x, tool}
        Integer [] startingNode = new Integer[]{0, 0, 2};
        Map<Integer[], Integer> distances = getDistances(startingNode);
        Set<Integer []> visited = new HashSet<>();

        Integer [] targetNode = startingNode;

        while (visited.size() < distances.keySet().size()) {
            Integer[] current = getSmallestCostNode(distances, visited);
            visited.add(current);
            for (Integer[] neighbour : getUnvisitedNeighbours(current, distances, visited)) {
                int addedCost = 1;
                if (neighbour[2] != current[2])
                    addedCost += 7;
                if (distances.get(current) + addedCost < distances.get(neighbour)) {
                    distances.put(neighbour, distances.get(current) + addedCost);
                }
            }
            if (current[0] == target.y && current[1] == target.x) {
                targetNode = current;
            }
        }

        // TESTING ON THE EXAMPLE
//        printCost(1, 0, 2, distances);
//        printCost(1, 1, 2, distances);
//        printCost(1, 1, 0, distances);
//        printCost(1, 4, 0, distances);
//        printCost(1, 4, 1, distances);
//        printCost(11, 4, 1, distances);
//        printCost(11, 5, 1, distances);
//        printCost(8, 5, 1, distances);

        return distances.get(targetNode);
    }

    public void printCost(int y, int x, int tool, Map<Integer[], Integer> distances) {
        for (Integer [] key : distances.keySet()) {
            if (key[0] == y && key[1] == x && key[2] == tool) {
                System.out.println("Y: " + key[0] + " X: " + key[1] + " T: " + key[2]);
                System.out.println(distances.get(key));
            }
        }
    }

}
