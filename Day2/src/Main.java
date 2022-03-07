import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String fileName = "Day2-1.txt";
        List<String> input = getInput(fileName);

        //1
        int[] countOfTwosAndThrees = getCountOfTwosAndThrees(fileName);
        System.out.println(countOfTwosAndThrees[0]*countOfTwosAndThrees[1]);

        //2
        Pair pair = getClosestPair("Day2-1.txt");
        System.out.println(whatLettersAreCommon(pair));

    }

    public static String whatLettersAreCommon(Pair pair)
    {
        String commonLetters = "";
        for (int i=0; i < pair.getFirst().length(); i++)
        {
            if (pair.getFirst().charAt(i) == pair.getSecond().charAt(i))
                commonLetters += pair.getFirst().charAt(i);
        }
        return commonLetters;
    }

    public static Pair getClosestPair(String fileName)
    {
        List<String> input = getInput(fileName);
        int lineSize = input.get(0).length();
        Pair pair = new Pair("", "", lineSize);

        for (int i=0; i < input.size(); i++) {
            for (int j = i + 1; j < input.size(); j++) {
                String stringI = input.get(i);
                String stringJ = input.get(j);
                char[] charsI = new char[lineSize];
                char[] charsJ = new char[lineSize];
                stringI.getChars(0, lineSize, charsI, 0);
                stringJ.getChars(0, lineSize, charsJ, 0);
                int differences = 0;
                for (int k=0; k < lineSize; k++) {
                    if (charsI[k] != charsJ[k])
                        differences++;
                }
                if (differences < pair.getDifferences())
                    pair = new Pair(stringI, stringJ, differences);
            }
        }
        return pair;
    }

    public static int[] getCountOfTwosAndThrees(String fileName) {
        List<String> input = getInput(fileName);
        int[] countOfTwosAndThrees = new int[2];
        for (String line : input) {
            getCount(line, countOfTwosAndThrees);
        }
        return countOfTwosAndThrees;
    }
    public static void getCount(String line, int [] countOfTwosAndThrees)
    {
        char[] chars = new char[line.length()];
        line.getChars(0, line.length(), chars, 0);

        boolean foundTwo = false;
        boolean foundThree = false;
        for (char character : chars) {
            long count = line.chars().filter(ch -> ch == character).count();
            if (count == 2)
                foundTwo = true;
            if (count == 3)
                foundThree = true;
            if (foundTwo && foundThree)
                break;
        }
        if (foundTwo)
            countOfTwosAndThrees[0]++;
        if (foundThree)
            countOfTwosAndThrees[1]++;
    }

    public static List<String> getInput(String fileName)
    {
        ArrayList<String> input = new ArrayList<String>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            while (line != null)
            {
                input.add(line);
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
}
