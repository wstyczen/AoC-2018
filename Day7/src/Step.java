import java.util.HashSet;
import java.util.Set;

public class Step {
    char letter;
    Set<Character> prerequisites = new HashSet<>();

    public Step(char letter, char prerequisite) {
        this.letter = letter;
        this.prerequisites.add(prerequisite);
    }

    public Step(char letter) {
        this.letter = letter;
    }

    public char getLetter() {
        return letter;
    }

    public void addPrerequisite(char newPrerequisite)
    {
        this.prerequisites.add(newPrerequisite);
    }

    public Set<Character> getPrerequisites() {
        return prerequisites;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass())
            return false;
        Step step2 = (Step) obj;
        return this.getLetter() == step2.getLetter();
    }
}

