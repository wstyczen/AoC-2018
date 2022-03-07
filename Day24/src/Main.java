import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static TreeMap<Group, Group> attacking = new TreeMap<Group, Group>((g1, g2) -> {
        if (g1.getInitiative() > g2.getInitiative()) {
            return -1;
        } else if (g1.getInitiative() == g2.getInitiative()) {
            return 0;
        } else {
            return 1;
        }

    });
    public static void main(String[] args) {
        Army [] armies = readInput("Day24.txt");
        Army winningArmy = armies[0];
        int damageBoost = 94;
        while (!winningArmy.getFaction().equals("Immune System")) {
            System.out.println("\n\nCurrent damage boost: " + String.valueOf(damageBoost) + ".\n\n");
            armies[0].boostDamage(damageBoost);
            winningArmy = initiateCombat(armies);
            damageBoost++;
        }

    }

    public static Army initiateCombat(Army [] armies) {
        Army[] armiesCopy = new Army[]{new Army(armies[0]), new Army(armies[1])};
        Army army1 = armiesCopy[0];
        Army army2 = armiesCopy[1];
        while (!army1.isDead() && !army2.isDead()) {
            army1.targetSelection(army2, attacking);
            army2.targetSelection(army1, attacking);
            for (Group key : attacking.keySet()) {
                attacking.get(key).isAttacked(key);
            }
            attacking.clear();
            army1.removeDead();
            army2.removeDead();
        }
        army1.printOverview();
        army2.printOverview();
        if (army1.isDead()) {
            return army2;
        }
        return army1;
    }

    public static Army [] readInput(String fileName) {
        Army [] armies = new Army[2];
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            String faction = null;
            int factionIndex = 0;
            while (line != null) {
                if (line.length() == 0) {
                    faction = null;
                    factionIndex++;
                } else if (faction == null) {
                    faction = line.substring(0, line.length() - 1);
                    armies[factionIndex] = new Army(new String(faction));
                } else {
                    Pattern pattern = Pattern.compile("^(.+?) units each with (.+?) hit points(.*?) with an attack " +
                            "that does (\\d+) (.+?) damage at initiative (\\d+)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        int unitCount = Integer.parseInt(matcher.group(1));
                        int hitPoints = Integer.parseInt(matcher.group(2));
                        String weaknessesAndImmunities = matcher.group(3);
                        int attackDamage = Integer.parseInt(matcher.group(4));
                        String damageType = matcher.group(5);
                        int initiative = Integer.parseInt(matcher.group(6));
                        ArrayList<String> weaknesses = new ArrayList<>();
                        ArrayList<String> immunities = new ArrayList<>();
                        if (weaknessesAndImmunities.length() > 0) {
                            weaknessesAndImmunities = weaknessesAndImmunities.substring(2);
                            Pattern weaknessesPattern = Pattern.compile("weak to ([a-z, ]+)");
                            Matcher weaknessesMatcher = weaknessesPattern.matcher(weaknessesAndImmunities);
                            if (weaknessesMatcher.find()) {
                                String weaknessesString = weaknessesMatcher.group(1);
                                weaknesses = new ArrayList<>(Arrays.asList(weaknessesString.split(", ")));
                            }
                            Pattern immunitiesPattern = Pattern.compile("immune to ([a-z, ]+)");
                            Matcher immunitiesMatcher = immunitiesPattern.matcher(weaknessesAndImmunities);
                            if (immunitiesMatcher.find()) {
                                String immunitiesString = immunitiesMatcher.group(1);
                                immunities = new ArrayList<>(Arrays.asList(immunitiesString.split(", ")));
                            }
                        }
                        StringBuilder sbuilder = new StringBuilder();
                        sbuilder.append(armies[factionIndex].getFaction());
                        sbuilder.append(" group ");
                        sbuilder.append(armies[factionIndex].getGroups().size() + 1);
                        String id = sbuilder.toString();
                        armies[factionIndex].addGroup(new Group(id, unitCount, hitPoints, attackDamage, damageType, initiative, weaknesses, immunities));
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return armies;
    }
}
