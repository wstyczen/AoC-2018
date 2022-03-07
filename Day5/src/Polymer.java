import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Polymer {
    private List<Character> polymer = new LinkedList<>();

    public Polymer(String base) {
        this.polymer = base
                .chars() // Convert to an IntStream
                .mapToObj(i -> (char) i) // Convert int to char, which gets boxed to Character
                .collect(Collectors.toList()); // Collect in a List<Character>;
        this.processPolymer();
    }
    public int getPolymerLength()
    {
        return this.polymer.size();
    }

    private boolean processSingleReaction()
    {
        for (int i=0; i < this.polymer.size() - 1; i++)
        {
            char thisC = this.polymer.get(i);
            char nextC = this.polymer.get(i+1);
            if (Character.toLowerCase(thisC) == Character.toLowerCase(nextC)) // is same letter
            {
                if ((Character.isUpperCase(thisC) && Character.isLowerCase(nextC)) ||
                        (Character.isLowerCase(thisC) && Character.isUpperCase(nextC))) // is diff case
                {
                    this.polymer.remove(i);
                    this.polymer.remove(i);
                    return false;
                }
            }
        }
        return true;
    }
    private void processPolymer()
    {
        boolean isProcessed = false;
        while (!isProcessed)
        {
            isProcessed = this.processSingleReaction();
        }
    }

    public void removeOccurencesInPolymer(Character c)
    {
        this.polymer.removeIf(c::equals);
    }

}
