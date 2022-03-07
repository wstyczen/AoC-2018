import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Guard {
    private int id;
    Set<Integer[]> asleep = new HashSet<>();

    public Guard(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Guard))
            return false;
        Guard guard = (Guard) obj;
        return (this.getId() == guard.getId());
    }

    public void addAsleep(int start, int end) {
        asleep.add(new Integer[]{start, end});
    }

    public int[] getSleepEveryMinute() {
        int [] minutes = new int[60];
        for (Integer [] period : asleep) {
            for (int i = period[0]; i <= period[1]; i++) {
                minutes[i]++;
            }
        }
        return minutes;
    }

    public int getMostAsleepValue() {
        return Arrays.stream(getSleepEveryMinute()).max().getAsInt();
    }

    public int getMostAsleepMinute() {
        Integer [] minutesArray = Arrays.stream(getSleepEveryMinute()).boxed().toArray(Integer[]::new);
        Integer maxValue = getMostAsleepValue();
        return Arrays.asList(minutesArray).indexOf(maxValue);
    }




    public int getTotalAsleepMinutes() {
        int total = 0;
        for (Integer [] period : asleep) {
            total += period[1] - period[0] + 1;
        }
        return total;
    }

}
