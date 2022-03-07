public class Pair {
    private String first;
    private String second;
    private int differences;

    public Pair(String first, String second, int differences) {
        this.first = first;
        this.second = second;
        this.differences = differences;
    }

    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    public int getDifferences() {
        return differences;
    }
}
