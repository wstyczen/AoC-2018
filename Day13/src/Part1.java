import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Part1 {
    private char [][] grid;
    private ArrayList<Cart> carts = new ArrayList<>();
    private static ArrayList<Character> directions = new ArrayList<>(Arrays.asList('<', '^', '>', 'v'));

    public Part1(String fileName) {
        readInput(fileName);
    }

    public void initializeGrid(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            int width = line.length();
            int height = 0;
            while (line != null)
            {
                height += 1;
                line = reader.readLine();
            }
            this.grid = new char [height][width];
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void readInput(String fileName) {
        initializeGrid(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            int lineNr = 0;
            while (line != null)
            {
                for (int i = 0; i < line.length(); i++)
                {
                    char track = line.charAt(i);
                    if (track == ' ')
                        continue;
                    else if (track == '>' || track == '<') {
                        grid[lineNr][i] = '-';
                        carts.add(new Cart(i, lineNr, track));
                    } else if (track == '^' || track == 'v') {
                        grid[lineNr][i] = '|';
                        carts.add(new Cart(i, lineNr, track));
                    } else {
                        grid[lineNr][i] = track;
                    }
                }
                lineNr++;
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public String checkForCrashes()
    {
        for (int i = 0; i < carts.size() - 1; i++) {
            for (int j = i + 1; j < carts.size(); j++) {
                if (carts.get(i).equals(carts.get(j))) {
                    return String.valueOf(carts.get(i).getX()) + "," + String.valueOf(carts.get(i).getY());
                }
            }
        }
        return null;
    }

    public String simulate() {
        String crash = null;
        while (crash == null) {
//            printMap();
            carts.sort((c1, c2) -> {
                if (c1.getY() > c2.getY())
                    return 1;
                else if (c1.getY() == c2.getY()) {
                    if (c1.getX() > c2.getX())
                        return 1;
                    else if (c1.getX() == c2.getX())
                        return 0;
                    else
                        return -1;
                }
                return -1;
            });
            for (Cart cart : carts) {
                cart.move(grid[cart.getY()][cart.getX()]);
            }
            crash = checkForCrashes();
//            printMap();
        }
        return crash;
    }

    public void printMap() {
        for (int y = 0; y < grid.length; y++)
        {
            for (int x = 0; x < grid[0].length; x++)
            {
                int index = carts.indexOf(new Cart(x, y, 'v'));
                if (index != -1)
                {
                    Cart c = carts.get(index);
                    Object [] crash = carts.stream().filter(cart -> cart.equals(c)).toArray();
                    if (crash.length > 1)
                        System.out.print("X");
                    else
                        System.out.print(c.getOrientation());
                }
                else if (grid[y][x] == '\u0000')
                    System.out.print(" ");
                else
                    System.out.print(grid[y][x]);
            }
            System.out.println();
        }
    }

    public class Cart {
        private int x;
        private int y;
        private char orientation;
        private int turnCounter = 0;

        public Cart(int x, int y, char orientation) {
            this.x = x;
            this.y = y;
            this.orientation = orientation;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public char getOrientation() {
            return orientation;
        }

        public void moveStraight()
        {
            switch (this.orientation)
            {
                case '>':
                    this.x++;
                    break;
                case '<':
                    this.x--;
                    break;
                case '^':
                    this.y--;
                    break;
                case 'v':
                    this.y++;
                    break;
            }
        }

        public void turn(char track)
        {
            if (track == '/')
            {
                if (orientation == '>')
                    orientation = '^';
                else if (orientation == '<')
                    orientation = 'v';
                else if (orientation == '^')
                    orientation = '>';
                else if (orientation == 'v')
                    orientation = '<';
            }
            else if (track == '\\')
            {
                if (orientation == '>')
                    orientation = 'v';
                else if (orientation == '<')
                    orientation = '^';
                else if (orientation == '^')
                    orientation = '<';
                else if (orientation == 'v')
                    orientation = '>';
            }
            moveStraight();
        }

        public char changeDirection(int change)
        {
            int newIndex = (directions.indexOf(orientation) + change);
            if (newIndex < 0)
                newIndex = 3;
            else if (newIndex > 3)
                newIndex = 0;
            return directions.get(newIndex);
        }

        public void intersection()
        {
            switch (this.turnCounter)
            {
                case 0:
                    orientation = changeDirection(-1);
                    break;
                case 2:
                    orientation = changeDirection(1);
                    break;
            }
            this.turnCounter++;
            if (this.turnCounter > 2)
                this.turnCounter = 0;
            moveStraight();
        }

        public void move(char track) {
            switch (track) {
                case '-':
                case '|':
                    moveStraight();
                    break;
                case '/':
                case '\\':
                    turn(track);
                    break;
                case '+':
                    intersection();
                    break;
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (this.getClass() != obj.getClass())
                return false;
            Cart cart = (Cart) obj;
            return (cart.getX() == this.getX() && cart.getY() == this.getY());
        }
    }


}
