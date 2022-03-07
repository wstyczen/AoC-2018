import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class Army {
    String faction;
    ArrayList<Group> groups;

    public Army(String faction, ArrayList<Group> groups) {
        this.faction = faction;
        this.groups = groups;
    }

//    for part 2
    public Army(Army army) {
        this.faction = new String(army.getFaction());
        this.groups = new ArrayList<>();
        for (Group group : army.getGroups()) {
            this.groups.add(new Group(group));
        }
    }

//    Part 2
    public void boostDamage(int boost) {
        for (Group group : this.groups) {
            group.boostDamage(boost);
        }
    }

    public Army(String faction) {
        this.faction = faction;
        this.groups = new ArrayList<>();
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }

    public String getFaction() {
        return faction;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void removeGroup(Group group) {
        if (!this.groups.contains(group))
            return;
        this.groups.remove(group);
    }

    public boolean isDead() {
        return this.groups.size() == 0;
    }

    public void removeDead() {
        this.groups.removeIf(Group::isDead);
    }

    public void targetSelection(Army enemyArmy, TreeMap<Group, Group> attacking) {
        this.groups.get(0).clearSelectedTargets();
        Collections.sort(enemyArmy.getGroups());
        Collections.sort(this.groups);
        // TARGET SELECTION
        for (Group group : this.groups) {
            Group target = group.chooseTarget(enemyArmy);
            if (target != null) {
                attacking.put(group, target);
            }
        }
    }

    public int getTotalUnits() {
        int total = 0;
        for (Group group : groups) {
            total += group.unitCount;
        }
        return total;
    }

    public void printOverview() {
        System.out.println("======= " + this.faction + " =======");
        if (this.groups.size() == 0) {
            System.out.println("No groups/units remaining.");
        } else {
            System.out.println("Groups remaining: ");
            StringBuilder sbuilder = new StringBuilder();
            for (Group group : this.groups) {
                sbuilder.append(group.getId());
                sbuilder.append(" with ");
                sbuilder.append(group.getUnitCount());
                sbuilder.append(" units remaining.\n");
            }
            sbuilder.append("-> Total units remaining: ");
            sbuilder.append(this.getTotalUnits());
            System.out.println(sbuilder.toString());
        }
        System.out.println("\n");
    }

}
