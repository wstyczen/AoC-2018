import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        readInput("Day16-part1.txt");
        System.out.println(Device.getPart1());

        int [] registers = {0, 0, 0, 0};
        int [] instruction = {0, 0, 0, 0};
        int [] expected = {0, 0, 0, 0};
        Device d = new Device(registers, expected, instruction);
        System.out.println(d.getPart2("Day16-part2.txt"));

    }

    public static void readInput(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int [] instruction = new int[4];
            int [] before = new int [4];
            int [] after = new int [4];

            String line = reader.readLine();

            Pattern pt = Pattern.compile("(\\d+)");
            Matcher m = pt.matcher(line);

            boolean inProgress = false;

            int [] thisLineArray = new int [4];

            while (line != null) {
                m.reset(line);
                if (line.length() > 0) {
                    int i = 0;
                    while (m.find()) {
                        thisLineArray[i] = Integer.parseInt(m.group(1));
                        i++;
                    }
                }
                if (line.length() > 0) {
                    if (inProgress) {
                        if (line.contains("After")) {
                            System.arraycopy(thisLineArray, 0, after, 0, 4);
                            inProgress = false;
                            Device device = new Device(before, after, instruction);
                        } else {
                            System.arraycopy(thisLineArray, 0, instruction, 0, 4);
                        }
                    } else {
                        System.arraycopy(thisLineArray, 0, before, 0, 4);
                        inProgress = true;
                    }
                }

                line = reader.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
