class Main {
    public static void main(String[] args) {
        int v;
        SpecialAttack v2;
        Person v3;
        SuperPerson spiderman;
        SuperPerson magneto;
        Battle showdown;
        Person winner;

        // spiderman
        spiderman = new SuperHero();
        v = spiderman.setStrength(7);
        v2 = spiderman.setSpecialAttack(new SpiderWeb());

        // magneto
        magneto = new SuperVillain();
        v = magneto.setStrength(6);
        v2 = magneto.setSpecialAttack(new MagnetPower());

        // showdown
        showdown = new Battle();
        v3 = showdown.setLeftContestant(spiderman);
        v3 = showdown.setRightContestant(magneto);
        
        // print winners power
        if (!showdown.isDraw()) {
            winner = showdown.getWinner();
            System.out.println(winner.getStrength()); // 7
        } else {
            System.out.println(0);
        }
    }
}


class Battle {
    Person leftContestant;
    Person rightContestant;

    public Person setLeftContestant(Person contestant) {
        leftContestant = contestant;
        return leftContestant;
    }

    public Person setRightContestant(Person contestant) {
        rightContestant = contestant;
        return rightContestant;
    }

    public boolean isDraw() {
        boolean smaller;
        boolean greater;
        boolean equal;
        smaller = leftContestant.getStrength() < rightContestant.getStrength();
        greater = rightContestant.getStrength() < leftContestant.getStrength();
        equal =  !greater && !smaller;
        return equal; // FIXME return and chain directly
    }

    public Person getWinner() {
        Person winner;
        if (leftContestant.getStrength() < rightContestant.getStrength()) {
            winner = rightContestant;
        } else {
            winner = leftContestant;
        }
        return winner;
    }
}

class SpecialAttack {}
class SpiderWeb extends SpecialAttack {}
class MagnetPower extends SpecialAttack {}
class FireBall extends SpecialAttack {}

class Person {
    int strength;

    public int getStrength() {
        return strength;
    }

    public int setStrength(int newStrength) {
        strength = newStrength;
        return strength;
    }
}
class NormalPerson extends Person {}
class SuperPerson extends Person {
    SpecialAttack attack;

    public SpecialAttack setSpecialAttack(SpecialAttack newAttack) {
        attack = newAttack;
        return attack;
    }
}
class SuperHero extends SuperPerson {}
class SuperVillain extends SuperPerson {}