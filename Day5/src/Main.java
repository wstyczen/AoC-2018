import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        long startTime = System.nanoTime();

        String input = getInput("Day5.txt");
        // Part1

//        Pol2 pol2 = new Pol2(input);
//        System.out.println(pol2.getPolymerLength());

        // Part 2
        Polymer bestPol = getBestPolymer(input);
        System.out.println(bestPol.getPolymerLength());

        long stopTime = System.nanoTime();
        double elapsedSeconds = (stopTime - startTime) / Math.pow(10, 9);
        System.out.println("Run time: " + elapsedSeconds + "s");

    }

    public static Polymer getBestPolymer(String pol)
    {
        Polymer polymer = null;
        int shortestLength = pol.length();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        for (char c : alphabet)
        {
            String redacted = pol.replace(String.valueOf(c), "");
            redacted = redacted.replace(String.valueOf(c).toUpperCase(), "");
            Polymer current = new Polymer(redacted);
            if (current.getPolymerLength() < shortestLength)
            {
                polymer = current;
                shortestLength = current.getPolymerLength();
            }
        }
        return polymer;
    }

    public static String getInput(String fileName)
    {
        String input="";
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            while (line != null)
            {
                input = line;
                line = br.readLine();
            }
            br.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return input;
    }

    public static String processPolymer(String polymer) // ???
    {
        for (int i=1; i < polymer.length(); i++)
        {
            String newPolymer = "";
            char thisC = polymer.charAt(i);
            char prevC = polymer.charAt(i-1);
            if (Character.toLowerCase(thisC) == Character.toLowerCase(prevC))
            {
                if ((Character.isUpperCase(thisC) && Character.isLowerCase(prevC)) ||
                        (Character.isLowerCase(thisC) && Character.isUpperCase(prevC)))
                {
                    if (i-1 > 0)
                    {
                        newPolymer += polymer.substring(0, i-1);
                    }
                    if (i < polymer.length() - 1)
                    {
                        newPolymer += polymer.substring(i + 1);
                    }
                    return processPolymer(newPolymer);
                }
            }
        }
        return polymer;
    }
}
