import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Map<Date, String> input = readInput("Day4.txt");
        List<Guard> guards = getGuards(input);
        int resultPart1 = getPart1(guards);
        int resultPart2 = getPart2(guards);
    }

    public static Map<Date, String> readInput(String fileName) {
        Map<Date, String> input = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while (line != null) {
                input.put(new Date(line.substring(0, line.indexOf("]") + 1)), line.substring(line.indexOf("]") + 2));
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        Map<Date, String> sortedInput = input.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        return sortedInput;
    }

    public static List<Guard> getGuards(Map<Date, String> input) {
        List<Guard> guards = new ArrayList<>();

        int currentId = -1;
        int start = -1;
        int end = -1;

        for (Date date : input.keySet()) {
            String value = input.get(date);
            int hashIndex = value.indexOf("#");
            if (hashIndex != -1) {
                currentId = Integer.parseInt(value.substring(hashIndex + 1, value.indexOf(" ", hashIndex)));
            } else if (value.contains("falls asleep")) {
                start = date.getMinute();
            } else if (value.contains("wakes up")) {
                end = date.getMinute();
                int index = guards.indexOf(new Guard(currentId));
                if (index == -1) {
                    Guard newGuard = new Guard(currentId);
                    newGuard.addAsleep(start, end - 1);  // guards count as awake the minute the wake up
                    guards.add(newGuard);
                } else {
                    guards.get(index).addAsleep(start, end - 1); // guards count as awake the minute the wake up
                }
            }
        }
        return guards;
    }

    public static int getPart1(List<Guard> guards) {
        Guard mostAsleepGuard = new Guard(0);
        for (Guard guard : guards) {
            if (guard.getTotalAsleepMinutes() > mostAsleepGuard.getTotalAsleepMinutes()) {
                mostAsleepGuard = guard;
            }
        }
        int minute = mostAsleepGuard.getMostAsleepMinute();
        int id = mostAsleepGuard.getId();

        System.out.println(id + " * " + minute + " = " + id*minute);

        return id*minute;
    }

    public static int getPart2(List<Guard> guards) {
        Guard chosenGuard = new Guard(0);
        for (Guard guard : guards) {
            if (guard.getMostAsleepValue() > chosenGuard.getMostAsleepValue()) {
                chosenGuard = guard;
            }
        }

        System.out.println(chosenGuard.getId() + " * " + chosenGuard.getMostAsleepMinute() + " = " + chosenGuard.getId()* chosenGuard.getMostAsleepMinute());

        return chosenGuard.getId()*chosenGuard.getMostAsleepMinute();
    }

}