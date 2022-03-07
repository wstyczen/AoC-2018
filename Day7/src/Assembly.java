import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Assembly {
    List<Step> steps = new ArrayList<>();
    Set<Character> allSteps = new HashSet<>();
    Set<Character> finishedSteps = new HashSet<>();
    String order = "";

    public Assembly() {

    }

    public void addLine(char letter, char prerequisite)
    {
        int index = this.steps.indexOf(new Step(letter, prerequisite));
        if (index != -1)
            this.steps.get(index).addPrerequisite(prerequisite);
        else
            this.steps.add(new Step(letter, prerequisite));
    }

    public void addToAllSteps(char c)
    {
        this.allSteps.add(c);
    }

    public boolean checkIfStepAvailable(Step s)
    {
        return this.finishedSteps.containsAll(s.getPrerequisites());
    }

    public List<Step> getSteps() {
        return steps;
    }

    public String getOrder() {
        return order;
    }

    public void addStepsWithoutPrerequisites()
    {
        for (char letter : this.allSteps)
            if (!this.steps.contains(new Step(letter)))
                this.steps.add(new Step(letter));
    }

    public void simulatePart1()
    {
        while (this.steps.size() > 0)
        {
            for (Step step : this.steps)
            {
                if (checkIfStepAvailable(step))
                {
                    this.order += step.getLetter();
                    this.finishedSteps.add(step.getLetter());
                    this.steps.remove(step);
                    break;
                }
            }
        }
    }
}

