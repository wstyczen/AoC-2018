public class Main {
    public static void main(String[] args) {
        // TEST
//        Cave cave = new Cave(510, 10, 10, 16, 16); // test values
        Cave cave = new Cave(8103, 9, 758, 1000, 30); // ACTUAL VALUES
//        cave.printRegions();

        System.out.println(cave.djikstra());
    }
}
