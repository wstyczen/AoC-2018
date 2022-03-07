import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Map {
    String regex;
    LinkedList<String> instructions = new LinkedList<>();
    int index = 0;

    public Map(String regex) {
        this.regex = regex;
    }

    public void generatePaths() {
        List<String> paths = new ArrayList<>();
        processAParenthesis(0, paths, 0);
    }

    public void processAParenthesis(int index, List<String> paths, int rootIndex) {
        while (index < regex.length()) {
            char currentChar = regex.charAt(index);
            if (Character.isAlphabetic(currentChar)) {
                if (paths.size() == 0) {
                    String s = "";
                    s += currentChar;
                    paths.add(s);
                } else {
                    paths.set(rootIndex, paths.get(rootIndex) + currentChar);
                }
                index++;
            } else if (currentChar == '(') {
                index++;
                paths.add(paths.get(rootIndex)); // make a copy of current path
                processAParenthesis(index, paths, rootIndex);
            } else if (currentChar == '|') {
                index++;
                processAParenthesis(index, paths, rootIndex + 1);
            } else if (currentChar == '(') {
                return;
            }
        }
    }

}
