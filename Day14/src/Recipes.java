import java.util.LinkedList;

public class Recipes {
    int[] recipes;
    int recipeCount = 0;
    int recipesReq;
    int [] elves;

    public Recipes(int nrOfElves, int[] startingRecipes, int recipesRequired) {
        this.elves = new int[nrOfElves];
        for (int i = 0; i < nrOfElves; i++)
        {
            this.elves[i] = i;
        }
        this.recipesReq = recipesRequired;
        this.recipes = new int[recipesReq + 10];
        while (recipeCount < startingRecipes.length)
        {
            this.recipes[recipeCount] = startingRecipes[recipeCount];
            recipeCount++;
        }
    }

    public String Part1()
    {
        String nextTen = "";
        while (nextTen.length() < 10)
        {
            int sum = 0;
            for (int elf : elves)
            {
                sum += recipes[elf];
            }
            String sumString = String.valueOf(sum);
            for (char digit : sumString.toCharArray())
            {
                if (recipeCount >= recipesReq)
                {
                    nextTen = nextTen.concat(Character.toString(digit));
                }
                recipes[recipeCount++] = Character.getNumericValue(digit);
            }
            for (int i = 0; i < elves.length; i++)
            {
                elves[i] = (elves[i] + recipes[elves[i]] + 1) % recipeCount;
            }
        }
        if (nextTen.length() > 10)
            nextTen = nextTen.substring(0, 10);
        return nextTen;
    }

    public int part2(int sequence)
    {
        String seqString = String.valueOf(sequence);
        int [] reqSeq = new int[seqString.length()];
        int index = 0;
        for (char digit : seqString.toCharArray())
        {
            reqSeq[index++] = Character.getNumericValue(digit);
        }

        index = -1;
        while (index == -1)
        {
            int sum = 0;
            for (int elf : elves)
            {
                sum += recipes[elf];
            }
            String sumString = String.valueOf(sum);
            for (char digit : sumString.toCharArray())
            {
                recipes[recipeCount++] = Character.getNumericValue(digit);
            }
            for (int i = 0; i < elves.length; i++)
            {
                elves[i] = (elves[i] + recipes[elves[i]] + 1) % recipeCount;
            }

            if (recipes.length >= reqSeq.length)
            {
                boolean found = true;
                for (int x = recipeCount - reqSeq.length; x < recipeCount; x++)
                    if (reqSeq[x] != recipes[x])
                        found = false;
                if (found)
                    return recipeCount - reqSeq.length;
            }

        }
        return index;
    }

    public int indexOfSequence(int [] sequence)
    {
        for (int i=0; i < recipes.length; i++)
            if (sequence[0] == recipes[i] && i + sequence.length <= recipes.length)
            {
                for (int j = i; j < i + sequence.length; j++)
                {
                    if (sequence[j - i] != recipes[j])
                        break;
                    if (sequence[j - i] == recipes[j] && j == i + sequence.length - 1)
                        return i;
                }
            }
        return -1;
    }

}
