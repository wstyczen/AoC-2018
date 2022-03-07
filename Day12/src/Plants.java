import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Plants {
    private String configuration;
    private int generation = 0;
    private HashMap<String, String> combinations = new HashMap<>();
//    private ArrayList<String> generations = new ArrayList<>();
    private int addedToTheFront = 0;

    public Plants(String fileName) {
        readInput(fileName);
    }

    public void readInput(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            this.configuration = line.substring(line.indexOf(":") + 2);
            br.readLine();
            line = br.readLine();
            String comb="";
            String result="";
            Pattern pt = Pattern.compile("([#|.]{5}) => ([.|#])");
            Matcher mr = pt.matcher("");

            while (line != null) {
                mr.reset(line);
                if (mr.find())
                {
                    comb = mr.group(1);
                    result = mr.group(2);
                }
                this.combinations.put(comb, result);
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void simulate(long reqGeneration) {
        while (this.generation++ < reqGeneration) {
//            this.generations.add(this.configuration);
            while (this.configuration.indexOf("#") < 4) {
                this.configuration = ".".concat(this.configuration);
                this.addedToTheFront++;
            }
            String newConfiguration = "..";
            while (this.configuration.lastIndexOf("#") > this.configuration.length() - 5) {
                this.configuration += ".";
            }
            for (int pos = 2; pos < this.configuration.length() - 2; pos++) {
                String comb = this.configuration.substring(pos - 2, pos + 3);
                newConfiguration += this.combinations.getOrDefault(comb, ".");
            }
            this.configuration = newConfiguration;
        }
//        this.generations.add(this.configuration);
    }

//    public void writeGenerationsIntoAFile(String fileName)
//    {
//        try (FileWriter fw = new FileWriter(fileName)) {
//            for (int i = addedToTheFront; i >= 0; i--)
//            {
//                fw.write(" ");
//            }
//            fw.write("0\n");
//            for (String gen : generations)
//            {
//                fw.write(gen + "\n");
//            }
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }

    public int getPart1(long generationReq)
    {
        simulate(generationReq);
        int sum = 0;
        int potIndex = 0;
        while (this.configuration.indexOf("#", potIndex + 1) != -1)
        {
            potIndex = this.configuration.indexOf("#", potIndex + 1);
            sum += potIndex - this.addedToTheFront;
        }
        return sum;
    }
}
