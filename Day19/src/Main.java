public class Main {

    public static void main(String[] args) {
        Device d = new Device("Day19.txt", 36);
//        Device d = new Device("Day19-test.txt", 7);
        int i = d.part1();
        // Cheated but whatever
        // register 2 gets stuck with 10551309
        // prime factors of 10551309 are: 1 x 3 x 41 x 109 x 787
        // the answer is sum of multiplications of all the combinations
    }
}


