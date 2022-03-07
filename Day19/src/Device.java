import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Device {
    int bound;
    int ip = 0;
    int [][] instructions;
    int [] registers = new int[6];
    static List<String> operationMap = Arrays.asList("addr", "addi", "mulr", "muli", "banr", "bani", "borr", "bori", "setr", "seti", "gtir", "gtri",
            "gtrr", "eqir", "eqri", "eqrr");

    public Device(String fileName, int size) {
        // part 2
        registers[0] = 1;

        instructions = new int[size][4];
        readInput(fileName);
    }

    public int getOperationIndex(String op) {
        return operationMap.indexOf(op);
    }

    public void readInput(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            Matcher numberMatcher = Pattern.compile("(\\d+)").matcher(line);
            Matcher opcodeMatcher = Pattern.compile("([a-z]+)").matcher(line);
            numberMatcher.find();
            bound = Integer.parseInt(numberMatcher.group(1));
            int lineCount = 0;
            line = reader.readLine();
            while (line != null) {
                int [] instruction = new int[4];
                numberMatcher.reset(line);
                opcodeMatcher.reset(line);
                opcodeMatcher.find();
                instruction[0] = getOperationIndex(opcodeMatcher.group(1));
                int i = 1;
                while (numberMatcher.find()) {
                    instruction[i] = Integer.parseInt(numberMatcher.group(1));
                    i++;
                }
                instructions[lineCount++] = instruction;
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void operation(int [] instruction) {
        int A = instruction[1];
        int B = instruction[2];
        int C = instruction[3];
        switch (instruction[0]) {
            case 0: //addr
                registers[C] = registers[A] + registers[B];
                break;
            case 1: //addi
                registers[C] = registers[A] + B;
                break;
            case 2: //mulr
                registers[C] = registers[A] * registers[B];
                break;
            case 3: //muli
                registers[C] = registers[A] * B;
                break;
            case 4: //banr
                registers[C] = registers[A] & registers[B];
                break;
            case 5: // bani
                registers[C] = registers[A] & B;
                break;
            case 6: // borr
                registers[C] = registers[A] | registers[B];
                break;
            case 7: //bori
                registers[C] = registers[A] | B;
                break;
            case 8: //setr
                registers[C] = registers[A];
                break;
            case 9: //seti
                registers[C] = A;
                break;
            case 10: //gtir
                registers[C] = (A > registers[B]) ? 1 : 0;
                break;
            case 11: //gtri
                registers[C] = (registers[A] > B) ? 1 : 0;
                break;
            case 12: //gtrr
                registers[C] = (registers[A] > registers[B]) ? 1 : 0;
                break;
            case 13: //eqir
                registers[C] = (A == registers[B]) ? 1 : 0;
                break;
            case 14: //eqri
                registers[C] = (registers[A] == B) ? 1 : 0;
                break;
            case 15: //eqrr
                registers[C] = (registers[A] == registers[B]) ? 1 : 0;
                break;
        }
    }

    public void printRegisters() {
        System.out.print("[ ");
        for (int i = 0; i < 6; i++) {
            System.out.print(registers[i] + " ");
        }
        System.out.println("]");
    }

    public int part1() {
        int count = 1;
        while (ip >= 0 && ip < instructions.length) {
            registers[bound] = ip;
            operation(instructions[ip]);
            ip = registers[bound];
            ip++;

            if (count > 10000) {
                printRegisters();
            }
            if (count > 10010)
                break;
            count++;
        }
        return registers[0];
    }
}