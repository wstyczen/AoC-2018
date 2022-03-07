import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        int[] startingRecipes = {3, 7};
//        Recipes recipes = new Recipes(2, startingRecipes, 2241241);
//        System.out.println(recipes.Part1());
//        int[] sequence = {8, 4, 6, 0, 2, 1};
//        int [] sequence = {5,9,4,1,4};

//        System.out.println(recipes.indexOfSequence(sequence));
        System.out.println(part2("846021"));

    }
        public static int part2(String breakCondition) {

            List<Integer> scoreBoard = new ArrayList<>();
            scoreBoard.add(3);
            scoreBoard.add(7);

            int scoreEntry1 = 0;
            int scoreEntry2 = 1;

            String scoreBoardString = "37";
            loop:
            while (true) {
                int combinedScore = scoreBoard.get(scoreEntry1) + scoreBoard.get(scoreEntry2);
                for (char digit : String.valueOf(combinedScore).toCharArray()) {
                    scoreBoard.add(Integer.parseInt(String.valueOf(digit)));
                    if (scoreBoardString.length() >= breakCondition.length()) {
                        scoreBoardString = scoreBoardString.substring(1);
                    }
                    scoreBoardString += digit;

                    if (scoreBoardString.equals(breakCondition)) {
                        break loop;
                    }
                }

                scoreEntry1 = (scoreEntry1 + scoreBoard.get(scoreEntry1) + 1) % scoreBoard.size();
                scoreEntry2 = (scoreEntry2 + scoreBoard.get(scoreEntry2) + 1) % scoreBoard.size();

            }

            return scoreBoard.size() - 6;
        }

}
