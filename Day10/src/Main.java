public class Main {
    public static void main(String[] args) {
//        Field field = new Field("Day10.txt");
//        int x = 0;
//        while (x++ < 10000)
//        {
//            field.advance();
//        }
//        field.simulate();

        long startTime = System.nanoTime();

        Part1 p1 = new Part1("Day10.txt");
        int second = p1.simulate("Day10-result.txt");
        System.out.println(second);

        long stopTime = System.nanoTime();
        double elapsedSeconds = (stopTime - startTime)/Math.pow(10, 9);
        System.out.println("Time in seconds: " + elapsedSeconds);
    }
}
