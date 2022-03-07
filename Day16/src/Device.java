import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Device {
    private static HashMap<Integer, Set<Integer>> opcodes = new HashMap<>();

    private static int [] finishedOpcodes = new int [16];

    private static int howManyInitilized = 0;
    private static int part1 = 0;
    private int [] registers;
    private int [] expectedRegisters;
    private int [] instruction;

    public Device(int[] registers, int[] expectedRegisters, int [] instruction) {
        this.registers = registers;
        this.expectedRegisters = expectedRegisters;
        this.instruction = instruction;
        simulate();
        howManyInitilized++;
    }

    public void operation() {
        int A =instruction[1];
        int B = instruction[2];
        int C = instruction[3];
        switch (finishedOpcodes[instruction[0]]) {
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

    public int [] operation(int opcode) {
        int [] newRegisters = new int [4];
        System.arraycopy(registers, 0, newRegisters, 0, newRegisters.length);
        int A =instruction[1];
        int B = instruction[2];
        int C = instruction[3];
        switch (opcode) {
            case 0: //addr
                newRegisters[C] = newRegisters[A] + newRegisters[B];
                break;
            case 1: //addi
                newRegisters[C] = newRegisters[A] + B;
                break;
            case 2: //mulr
                newRegisters[C] = newRegisters[A] * newRegisters[B];
                break;
            case 3: //muli
                newRegisters[C] = newRegisters[A] * B;
                break;
            case 4: //banr
                newRegisters[C] = newRegisters[A] & newRegisters[B];
                break;
            case 5: // bani
                newRegisters[C] = newRegisters[A] & B;
                break;
            case 6: // borr
                newRegisters[C] = newRegisters[A] | newRegisters[B];
                break;
            case 7: //bori
                newRegisters[C] = newRegisters[A] | B;
                break;
            case 8: //setr
                newRegisters[C] = newRegisters[A];
                break;
            case 9: //seti
                newRegisters[C] = A;
                break;
            case 10: //gtir
                newRegisters[C] = (A > newRegisters[B]) ? 1 : 0;
                break;
            case 11: //gtri
                newRegisters[C] = (newRegisters[A] > B) ? 1 : 0;
                break;
            case 12: //gtrr
                newRegisters[C] = (newRegisters[A] > newRegisters[B]) ? 1 : 0;
                break;
            case 13: //eqir
                newRegisters[C] = (A == newRegisters[B]) ? 1 : 0;
                break;
            case 14: //eqri
                newRegisters[C] = (newRegisters[A] == B) ? 1 : 0;
                break;
            case 15: //eqrr
                newRegisters[C] = (newRegisters[A] == newRegisters[B]) ? 1 : 0;
                break;
        }
        return newRegisters;
    }

    public void  simulate() {
        Set<Integer> matchingOpcodes = new HashSet<>();
        for (int i = 0; i < 16; i++) {
            int [] result = operation(i);
            if (Arrays.equals(expectedRegisters, result)) {
                matchingOpcodes.add(i);
            }
        }
        if (matchingOpcodes.size() >= 3)
            part1++;

        if (opcodes.containsKey(instruction[0])) {
            matchingOpcodes = opcodes.get(instruction[0]).stream()
                    .filter(matchingOpcodes::contains)
                    .collect(Collectors.toSet());
            opcodes.replace(instruction[0], matchingOpcodes);
        } else {
            opcodes.put(instruction[0], matchingOpcodes);
        }
    }

    public static int getPart1() {
        return part1;
    }

    public HashMap<Integer, Set<Integer>> getOpcodes() {
        return opcodes;
    }

    public int[] getFinishedOpcodes() {
        return finishedOpcodes;
    }

    public int getHowManyInitilized() {
        return howManyInitilized;
    }

    public boolean finish() {
        boolean found1 = false;
            for (int i : opcodes.keySet()) {
                if (opcodes.get(i).size() == 1) {
                    found1 = true;
                    int value = opcodes.get(i).iterator().next();
                    finishedOpcodes[i] = value;
                    for (Set<Integer> potentialOpcodes : opcodes.values()) {
                        potentialOpcodes.remove(value);
                    }
                    opcodes.remove(i);
                    break;
                }
            }
            if (opcodes.size() == 0)
                return true;
            if (!found1) {
                return false;
            } else {
                finish();
            }
            return false;
    }

    public int getPart2(String fileName) {
        finish();
        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            while (scanner.hasNextLine()) {
                int count = 0;
                while (count < 4) {
                    instruction[count] = scanner.nextInt();
                    count++;
                }
                operation();
                if (scanner.hasNextLine())
                    scanner.nextLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return registers[0];
    }

}
