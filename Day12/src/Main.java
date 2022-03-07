public class Main {
    public static void main(String[] args) {
//        Plants plants = new Plants("Day12.txt");
//        int part1 = plants.getPart1(20);
//        System.out.println(part1);
//        plants.writeGenerationsIntoAFile("Day12-Output.txt");

        // After a certain point there is a linear growth to the sum
        // -> it is greater by 62 with each iteration

        Plants plants = new Plants("Day12.txt");
        long sum = plants.getPart1(1000);
        sum += 62 * (50000000000l - 1000);
        System.out.println(sum);
    }

}
