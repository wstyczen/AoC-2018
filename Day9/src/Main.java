import java.util.Arrays;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        //getPart1(424, 71144); // P1
        getPart2(424, 220000, 190000); // P2

    }

    public static void getPart1(int numPlayers, int lastMarble)
    {
        LinkedList<Integer> marbles = new LinkedList<>();
        marbles.add(0);
        int currentMarble=1;
        int currentPosition=1;
        int playerIndex=0;
        int [] scores = new int [numPlayers];
        while (currentMarble <= lastMarble)
        {
            if (currentMarble % 23 == 0)
            {
                scores[playerIndex] += currentMarble;
                currentPosition -= (2 + 7); // added 2 to pos preemptively earlier
                if (currentPosition < 0)
                    currentPosition += marbles.size();
                scores[playerIndex] += marbles.remove(currentPosition);
            }
            else
            {
                if (currentPosition > marbles.size())
                {
                    currentPosition = 1;
                    marbles.add(currentPosition, currentMarble);
                }
                else if (currentPosition == marbles.size())
                    marbles.add(currentMarble);
                else
                    marbles.add(currentPosition, currentMarble);
            }
            currentPosition += 2;
            currentMarble++;
            playerIndex++;
            if (playerIndex >= scores.length)
                playerIndex = 0;
        }
        System.out.println(Arrays.stream(scores).max().getAsInt());
        //System.out.println("The winner is player " + winner.getId() + " with a score of " + winner.getPoints());
    }

    public static void getPart2(int numPlayers, int lastMarble, int startChecking) {

        int max = 0;
        int maxMarbleIndex = 0;

        LinkedList<Integer> marbles = new LinkedList<>();
        marbles.add(0);
        int currentMarble=1;
        int currentPosition=1;
        int playerIndex=0;
        int [] scores = new int [numPlayers];
        while (currentMarble <= lastMarble)
        {
            if (currentMarble % 23 == 0)
            {
                scores[playerIndex] += currentMarble;
                currentPosition -= (2 + 7); // added 2 to pos preemptively earlier
                if (currentPosition < 0)
                    currentPosition += marbles.size();
                scores[playerIndex] += marbles.remove(currentPosition);
            }
            else
            {
                if (currentPosition > marbles.size())
                {
                    currentPosition = 1;
                    marbles.add(currentPosition, currentMarble);
                }
                else if (currentPosition == marbles.size())
                    marbles.add(currentMarble);
                else
                    marbles.add(currentPosition, currentMarble);
            }
            currentPosition += 2;
            currentMarble++;
            playerIndex++;
            if (playerIndex >= scores.length)
                playerIndex = 0;

            // PART2
            if (currentMarble >= startChecking) {
                int newMax = Arrays.stream(scores).max().getAsInt();
                if (newMax > max) {
                    System.out.println("Changed max value at marble: " + currentMarble + " to: " + newMax);
                    System.out.println("Difference in Marble nr: " + (currentMarble - maxMarbleIndex));
                    System.out.println("Difference in Value: " + (newMax - max));
                    System.out.println();
                    max = newMax;
                    maxMarbleIndex = currentMarble;
                }
            }


        }
        System.out.println(Arrays.stream(scores).max().getAsInt());
        //System.out.println("The winner is player " + winner.getId() + " with a score of " + winner.getPoints());

    }



}
