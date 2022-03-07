public class Main {
    public static void main(String[] args) {
        //Cave cave = new Cave("Day15.txt");
        //cave.simulate();
        //cave.printResult();
        part2("Day15.txt");
    }

    public static void part2(String fileName) {
        Cave cave = new Cave(fileName);;
        int elfCount = cave.getCount('E');
        int increment = 1;
        int currentCount = 0;
        while (currentCount < elfCount) {
            cave = new Cave(fileName);
            cave.upTheAttack('E', increment);
            cave.simulate();
            currentCount = cave.getCount('E');
            increment++;
        }
        System.out.println("Attack power: " + (3 + increment - 1));
        cave.printResult();
    }
}
