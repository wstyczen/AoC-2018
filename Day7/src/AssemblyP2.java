import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class AssemblyP2 extends Assembly {
    public class Worker {
        private int id;
        private int remainingTime = 0;
        private Character task = null;

        public Worker(int id) {
            this.id = id;
        }

        public Worker(int id, Character task) {
            this.id = id;
            this.task = task;
            this.setTimer(task);
        }

        public int getRemainingTime() {
            return remainingTime;
        }

        public int getId() {
            return id;
        }

        public Character getTask() {
            return task;
        }

        public void setTimer(Character step)
        {
            int baseLine = (int)'A' - 1;
            this.remainingTime = 60 + ((int)step - baseLine);
            // Test
            //this.remainingTime = (int)step - baseLine;
        }

        public void beginTask(Character step)
        {
            if (this.task != null)
                return;
            this.setTimer(step);
            this.task = step;
            initiatedSteps.add(this.task);
        }

        public void finishTask()
        {
            finishedSteps.add(this.task);
            order += this.task;
            initiatedSteps.remove(this.task);
            this.task = null;
            this.remainingTime = 0;
        }

        public void lapseTime()
        {
            if (this.task != null)
            {
                this.remainingTime--;
                if (this.remainingTime <= 0)
                {
                    this.finishTask();
                }
            }
        }

    }

    int timer=0;
    Set<Worker> workers = new HashSet<>();
    Set<Step> availableSteps = new TreeSet<>();
    Set<Character> initiatedSteps = new HashSet<>();

    AssemblyP2(Assembly assembly)
    {
        this.steps = assembly.steps;
        this.allSteps = assembly.allSteps;
    }

    public void addWorkers(int nr)
    {
        while (nr > 0)
        {
            this.workers.add(new Worker(nr));
            nr--;
        }
    }

    public void availableSteps() {
//        availableSteps.clear();
//        for (Step step : this.steps) {
//            if (this.finishedSteps.containsAll(step.getPrerequisites()) && !this.initiatedSteps.contains(step.getLetter())
//            && !this.finishedSteps.contains(step.getLetter()))
//                this.availableSteps.add(step);
//        }

        this.availableSteps = this.steps.stream()
                .filter(step -> this.finishedSteps.containsAll(step.getPrerequisites()))
                .filter(step -> !this.initiatedSteps.contains(step.getLetter()))
                .filter(step -> !this.finishedSteps.contains(step.getLetter()))
                .sorted((step1, step2) -> (Character.compare(step1.getLetter(), step2.getLetter())))
                .collect(Collectors.toSet());

    }

    public void assignTasks() {
        this.workers.forEach(worker -> {
                this.availableSteps.forEach(step -> {
                    if (worker.getTask() == null && !this.initiatedSteps.contains(step.getLetter())
                            && !this.finishedSteps.contains(step.getLetter()))
                        worker.beginTask(step.getLetter());
                });
        });
    }

    public void progress()
    {
        this.availableSteps();
        this.assignTasks();
        this.workers.forEach(Worker::lapseTime);
        this.timer++;
    }
    public void simulate()
    {
        while (this.finishedSteps.size() < this.allSteps.size())
            this.progress();
    }

    public int getTimer() {
        return timer;
    }
}
