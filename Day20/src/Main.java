import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
//        String regex = getRegex("Day20.txt");
//        Map map = new Map(regex);

        Map map = new Map("ENWWW(NEEE|SSE(EE|N))");
        map.generatePaths();
    }

    public static String getRegex(String fileName) {
        String regex = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            Matcher mr = Pattern.compile("^\\^(.*?)\\$$").matcher(line);
            if (mr.find())
                regex = mr.group(1);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return regex;
    }
}


