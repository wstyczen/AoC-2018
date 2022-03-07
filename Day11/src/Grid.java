public class Grid {
    private int [][] grid = new int[300][300];
    private int [] topLeft = new int[2];
    private int totalPower = Integer.MIN_VALUE;
    private int gridSerialNr = 9435;

    public int getPowerLevel(int x, int y)
    {
        int power = x + 10; // rack id
        power *= y;
        power += gridSerialNr;
        power *= (x + 10);
        if (power < 100)
            return -5;
        power %= 1000;
        power /= 100;
        return power -5;
    }

    public void fillTheGrid()
    {
        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 300; j++) {
                grid[i][j] = getPowerLevel(i, j);
            }
        }
    }

    public void part1(int size) {
        fillTheGrid();
        for (int i = 0; i < 300 - (size - 1); i++) {
            for (int j = 0; j < 300 - (size - 1); j++) {
                int total = 0;
                for (int x = i; x < i + size; x++)
                    for (int y = j; y < j + size; y++)
                        total += grid[x][y];
                if (total > totalPower)
                {
                    totalPower = total;
                    topLeft[0] = i;
                    topLeft[1] = j;
                }
            }
        }
    }

    public void part2()
    {
        fillTheGrid();
        int bestSize=0;
        for (int size = 1; size <= 300; size++) {
            for (int i = 0; i < 300 - (size - 1); i++) {
                for (int j = 0; j < 300 - (size - 1); j++) {
                    int total = 0;
                    for (int x = i; x < i + size; x++)
                        for (int y = j; y < j + size; y++)
                            total += grid[x][y];
                    if (total > totalPower)
                    {
                        bestSize = size;
                        totalPower = total;
                        topLeft[0] = i;
                        topLeft[1] = j;
                    }
                }
            }
        }
        System.out.println(topLeft[0] + "," + topLeft[1] + "," + bestSize);
    }

}
