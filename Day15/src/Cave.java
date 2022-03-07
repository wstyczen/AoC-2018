import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Cave {

    public static class Coordinates {
        int x;
        int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coordinates(Coordinates cds) {
            this.x = cds.x;
            this.y = cds.y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this.getClass() != obj.getClass())
                return false;
            Coordinates cd = (Coordinates) obj;
            return (this.x == cd.x && this.y == cd.y);
        }
    }

    public class Unit implements Comparable<Unit> {
        private char faction;
        Coordinates location;
        private int healthPoints;
        private int attackPower;

        public Unit(char faction, Coordinates location, int healthPoints, int attackPower) {
            this.faction = faction;
            this.location = location;
            this.healthPoints = healthPoints;
            this.attackPower = attackPower;
        }

        public Unit(Coordinates location) {
            this.location = location;
        }

        public void incrementAttack(int increment) {
            attackPower += increment;
        }

        public char getFaction() {
            return faction;
        }

        public char getEnemyFaction() {
            return (this.getFaction() == 'E') ? 'G' : 'E';
        }

        public Coordinates getLocation() {
            return location;
        }

        public int getAttackPower() {
            return attackPower;
        }

        public int getHealthPoints() {
            return healthPoints;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj.getClass() != this.getClass())
                return false;
            Unit unit = (Unit) obj;
            if (this.getLocation().equals(unit.getLocation()))
                return true;
            return false;
        }

        @Override
        public int compareTo(Unit unit) {
            if (this.getLocation().y > unit.getLocation().y)
                return 1;
            else if (this.getLocation().y == unit.getLocation().y)
            {
                if (this.getLocation().x > unit.getLocation().x)
                    return 1;
                else if (this.getLocation().x == unit.getLocation().x)
                    return 0;
                else
                    return -1;
            }
            else
                return -1;
        }

        public void attack(Unit unit) {
            unit.takeDamage(this.attackPower);
        }

        public void takeDamage(int value) {
            this.healthPoints -= value;
        }

        public void move(Coordinates newPosition) {
            location = newPosition;
        }

        public boolean checkIfAlive() {
            if (healthPoints <= 0)
                return false;
            return true;
        }
    }

    ArrayList<Unit> units = new ArrayList<>();
    char [][] field;
    int round = 0;

    public Cave(String fileName) {
        readInput(fileName);
    }

    public void initializeField(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int counter = 0;
            String line = br.readLine();
            int rowSize = line.length();
            while (line != null) {
                counter++;
                line = br.readLine();
            }
            this.field = new char[counter][rowSize];
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void readInput(String fileName) {
        initializeField(fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int lineCounter = 0;
            String line = br.readLine();
            while (line != null)
            {
                for (int i = 0; i < line.length(); i++)
                {
                    char c = line.charAt(i);
                    field[lineCounter][i] = c;
                    if (Character.isLetter(c))
                    {
                        if (c == 'E' || c == 'G')
                            units.add(new Unit(c, new Coordinates(i, lineCounter), 200, 3));
                    }
                }
                lineCounter++;
                line = br.readLine();
            }
        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public int getManhattanDistance(Coordinates c1, Coordinates c2) {
        return Math.abs(c1.x - c2.x) + Math.abs(c1.y - c2.y);
    }

    public LinkedList<Coordinates> getAdjacent(Coordinates pt) {
        LinkedList<Coordinates> adjacent = new LinkedList<>();
        adjacent.add(new Coordinates(pt.x, pt.y - 1));
        adjacent.add(new Coordinates(pt.x - 1, pt.y));
        adjacent.add(new Coordinates(pt.x + 1, pt.y));
        adjacent.add(new Coordinates(pt.x, pt.y + 1));
        return adjacent;
    }

    public LinkedList<Coordinates> getFilteredAdjacent(Coordinates pt) {
        LinkedList<Coordinates> adjacent = getAdjacent(pt);
        return adjacent.stream().filter(cord -> (field[cord.y][cord.x] == '.')).collect(Collectors.toCollection(LinkedList::new));
    }

    public boolean isAdjacentToEnemy(Unit u, Coordinates cds) {
        LinkedList<Coordinates> adjacent = getAdjacent(cds);
        for (Coordinates cord : adjacent) {
            if (field[cord.y][cord.x] == u.getEnemyFaction())
                return true;
        }
        return false;
    }

    public boolean containsKey(Map<Coordinates, Coordinates> visited, Coordinates key) {
        for (Coordinates k : visited.keySet()) {
            if (k.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<Coordinates> getPath(Unit unit) {
        // Breadth first search implementation
        Coordinates start = new Coordinates(unit.getLocation());
        ArrayDeque<Coordinates> queue = new ArrayDeque<>();
        Map<Coordinates, Coordinates> visited = new HashMap();
        Coordinates end = null;

        queue.add(start);
        visited.put(start, start);
        outerloop:
        while (queue.size() != 0) {
            Coordinates pt = queue.removeFirst();
            for (Coordinates cd : getFilteredAdjacent(pt)) {
                //if (!visited.containsKey(cd)) { // for some reason multiple instances of the same key are instantiated ???
                if (!containsKey(visited, cd)) {
                    queue.add(cd);
                    visited.put(cd, pt);
                    if (isAdjacentToEnemy(unit, cd)) {
                        end = cd;
                        break outerloop;
                    }
                }
            }
        }

        if (end == null)
            return null;

        LinkedList<Coordinates> bestPath = new LinkedList<>();
        while (end != start) {
            bestPath.add(0, end);
            end = visited.get(end);
        }
        return bestPath;
    }

    public Unit getEnemyInRange(Unit unit) {
        LinkedList<Coordinates> adjacent = getAdjacent(new Coordinates(unit.getLocation()));
        Unit target = null;
        for (Coordinates cord : adjacent) {
            int index = units.indexOf(new Unit(cord));
            if (index != -1) {
                if (units.get(index).getFaction() != unit.getFaction()) {
                    if (target != null) {
                        if (target.getHealthPoints() > units.get(index).getHealthPoints())
                            target = units.get(index);
                    } else {
                        target = units.get(index);
                    }
                }
            }
        }
        return target;
    }

    public boolean attack(Unit unit) {
        Unit enemy = getEnemyInRange(unit);
        if (enemy == null)
            return false;
        unit.attack(enemy);
        if (!enemy.checkIfAlive()) {
            field[enemy.getLocation().y][enemy.getLocation().x] = '.';
            units.remove(enemy);
        }
        return true;
    }

    public void move(Unit unit, Coordinates cds) {
        field[unit.getLocation().y][unit.getLocation().x] = '.';
        unit.move(cds);
        field[cds.y][cds.x] = unit.getFaction();
    }

    public void moveTowardsClosest(Unit unit) {
        LinkedList<Coordinates> path = getPath(unit);
        if (path == null)
            return;
        move(unit, path.get(0));
    }

    public void simulateARound(int start) {
        int i;
        int size = units.size();
        for (i = start; i < size; i++) {
            Unit u = units.get(i);
            if (!attack(u)) {
                moveTowardsClosest(u);
                attack(u);
            }
            if (size != units.size()) { // someone died - can't modify units while iterating over them
                simulateARound(units.indexOf(u) + 1);
                break;
            }
        }
    }

    public boolean won() {
        char faction = units.get(0).getFaction();
        for (Unit u : units) {
            if (u.getFaction() != faction)
                return false;
        }
        if (faction == 'E') {
            System.out.print("Elves");
        } else {
            System.out.print("Goblins");
        }
        System.out.println(" won after " + round + " rounds with " + units.size() + " units remaining.");
        return true;
    }

    public void simulate() {
        while (!won()) {
            Collections.sort(units);
            //printField();
            round++;
            simulateARound(0);
        }
        //printField();
    }

    public void printResult() {
        int healthSum = 0;
        for (Unit unit : units) {
            healthSum += unit.getHealthPoints();
        }
        // for some reason its a round ahead
        //round--;

        printField();
        System.out.println("Outcome: " + round + " * " + healthSum + " = " + round*healthSum);
    }

    public void printField() {
        System.out.println("\n -> Round: " + round);
        for (int i = 0; i < field.length; i++) {

            for (int j = 0; j < field[0].length; j++) {
                System.out.print(field[i][j]);
            }
            for (Unit u : units) {
                if (u.getLocation().y == i) {
                    System.out.print("\t" + u.getFaction() + " (" + u.getHealthPoints() + ")");
                }
            }
            System.out.println();
        }
    }

    public void upTheAttack(char faction, int increment) {
        for (Unit unit : units) {
            if (unit.getFaction() == faction) {
                unit.incrementAttack(increment);
            }
        }
    }

    public int getCount(char faction) {
        int count = 0;
        for (Unit unit : units) {
            if (unit.getFaction() == faction) {
                count++;
            }
        }
        return count;
    }


}
