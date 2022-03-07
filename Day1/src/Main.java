import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        System.out.println(getResultingFrequency(0, "Day1-1.txt"));

        int freq = getFirstRepeatedFrequency(0, "Day1-1.txt");
        System.out.println(freq);

    }


    public static int getFirstRepeatedFrequency(int startingFrequency, String fileName)
    {
        boolean found = false;
        ArrayList<Integer> reachedFrequencies = new ArrayList<Integer>();
        int currentFrequency = startingFrequency;
        while (!found) {
            ArrayList<Integer> frequencyChangeList = getFrequencyChangeList(fileName);
            for (int element : frequencyChangeList)
            {
                currentFrequency += element;
                if (reachedFrequencies.contains(currentFrequency))
                {
                    found = false;
                    return currentFrequency;
                }
                reachedFrequencies.add(currentFrequency);
            }
        }
        return currentFrequency;
    }

    public static ArrayList<Integer> getFrequencyChangeList(String fileName)
    {
        ArrayList<Integer> frequencyChangeList = new ArrayList<Integer>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            while (line != null) {
                frequencyChangeList.add(Integer.parseInt(line));
                line = br.readLine();
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return frequencyChangeList;
    }

}
