import java.util.ArrayList;

public class Group implements Comparable<Group> {
    String id;
    private static ArrayList<Group> selectedTargets = new ArrayList<>();
    Unit unitType;
    int unitCount;

    public Group(String id, int unitCount, int hitPoints, int attackDamage, String damageType, int initiative, ArrayList<String> weaknesses, ArrayList<String> immunities) {
        this.unitType = new Unit(hitPoints, attackDamage, damageType, initiative, weaknesses, immunities);
        this.unitCount = unitCount;
        this.id = id;
    }

//    for part 2
    public Group(Group group) {
        this.unitType = new Unit(group.getUnitType().getHitPoints(), group.getUnitType().getAttackDamage(), new String(group.getUnitType().getDamageType()),
                group.getUnitType().getInitiative(), group.getUnitType().getWeaknesses(), group.getUnitType().getImmunities());
        this.unitCount = group.getUnitCount();
        this.id = new String(group.getId());
    }

//    for part 2
    public void boostDamage(int boost) {
        this.unitType.attackDamage += boost;
    }

    public String getId() {
        return id;
    }

    public Unit getUnitType() {
        return unitType;
    }

    public int getUnitCount() {
        return unitCount;
    }

    int getEffectivePower() {
        return this.unitCount*this.unitType.getAttackDamage();
    }

    int getInitiative() {
        return this.getUnitType().getInitiative();
    }

    public Group chooseTarget(Army enemyArmy) {
        String damageType = this.getUnitType().getDamageType();
        Group enemy = null;
        for (Group group : enemyArmy.getGroups()) {
            if (selectedTargets.contains(group)) {
                continue;
            } else if (enemy == null) {
                enemy = group;
            } else if (enemy.isWeakTo(damageType)) {
                selectedTargets.add(enemy);
                return enemy;
            } else if (enemy.isImmuneTo(damageType)) {
                if (!group.isImmuneTo(damageType)) {
                    enemy = group;
                }
            } else {
                if (group.isWeakTo(damageType))
                    enemy = group;
            }
        }
        if (enemy == null || enemy.isImmuneTo(damageType)) {
            return null;
        }
        selectedTargets.add(enemy);
        return enemy;
    }

    public void clearSelectedTargets() {
        selectedTargets.clear();
    }

    @Override
    public int compareTo(Group g) {
        if (this.getEffectivePower() > g.getEffectivePower()) {
            return -1;
        } else if (this.getEffectivePower() == g.getEffectivePower()) {
            if (this.getUnitType().getInitiative() > g.getUnitType().getInitiative()) {
                return -1;
            } else if (this.getUnitType().getInitiative() == this.getUnitType().getInitiative()) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    public boolean isWeakTo(String damageType) {
        return this.getUnitType().getWeaknesses().contains(damageType);
    }

    public boolean isImmuneTo(String damageType) {
        return this.getUnitType().getImmunities().contains(damageType);
    }

    public void isAttacked(Group attacker) {
        int damageTaken = attacker.getEffectivePower();
        if (this.isImmuneTo(attacker.getUnitType().getDamageType())) {
            return;
        } else if (this.isWeakTo(attacker.getUnitType().getDamageType())) {
            damageTaken += attacker.getEffectivePower();
        }

        int unitsKilled = Math.floorDiv(damageTaken, this.getUnitType().getHitPoints());
        if (unitsKilled > this.getUnitCount()) {
            unitsKilled = this.getUnitCount();
        }

        StringBuilder sbuilder = new StringBuilder();
        sbuilder.append(attacker.getId());
        sbuilder.append(" attacks ");
        sbuilder.append(this.getId());
        sbuilder.append(" for ");
        sbuilder.append(damageTaken);
        sbuilder.append(" killing ");
        sbuilder.append(unitsKilled);
        sbuilder.append(" units.");
        System.out.println(sbuilder.toString());

        this.unitCount -= unitsKilled;
    }

    public boolean isDead() {
        return unitCount <= 0;
    }
    class Unit {
        int hitPoints;
        int attackDamage;
        String damageType;
        int initiative;
        ArrayList<String> weaknesses;
        ArrayList<String> immunities;

        public Unit(int hitPoints, int attackDamage, String damageType, int initiative, ArrayList<String> weaknesses, ArrayList<String> immunities) {
            this.hitPoints = hitPoints;
            this.attackDamage = attackDamage;
            this.damageType = damageType;
            this.initiative = initiative;
            this.weaknesses = weaknesses;
            this.immunities = immunities;
        }

        public int getHitPoints() {
            return hitPoints;
        }

        public int getAttackDamage() {
            return attackDamage;
        }

        public String getDamageType() {
            return damageType;
        }

        public int getInitiative() {
            return initiative;
        }

        public ArrayList<String> getWeaknesses() {
            return weaknesses;
        }

        public ArrayList<String> getImmunities() {
            return immunities;
        }
    }


}
