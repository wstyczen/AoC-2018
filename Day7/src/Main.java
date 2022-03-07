import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Assembly assembly = readInput("Day7.txt");
        AssemblyP2 assemblyP2 = new AssemblyP2(assembly);
        assemblyP2.addWorkers(5);
        assemblyP2.simulate();
        System.out.println(assemblyP2.getTimer());
    }

    public static Assembly readInput(String fileName)
    {
        Assembly assembly = new Assembly();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line = br.readLine();
            while (line != null)
            {
                String [] words = line.split(" ");
                assembly.addLine(words[7].charAt(0), words[1].charAt(0));

                assembly.addToAllSteps(words[7].charAt(0));
                assembly.addToAllSteps(words[1].charAt(0));

                line = br.readLine();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        assembly.addStepsWithoutPrerequisites();
        Collections.sort(assembly.getSteps(), (Step s1, Step s2) -> (Character.compare(s1.getLetter(), s2.getLetter())));
        return assembly;
    }
}
