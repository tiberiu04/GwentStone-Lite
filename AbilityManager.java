package utils;

import java.util.ArrayList;

public class AbilityManager {

    public AbilityManager() { }

    public void weakKnees(final Card ripper, final Card victim) {
        victim.setAttackDamage((victim.getAttackDamage() > Constants.TWO)
                            ? (victim.getAttackDamage() - Constants.TWO)
                            : Constants.ZERO);

        ripper.setHasAttackedThisTurn(true);
    }

    public void skyjack(final Card miraj, final Card victim) {
        int health = miraj.getHealth();
        miraj.setHealth(victim.getHealth());
        victim.setHealth(health);

        miraj.setHasAttackedThisTurn(true);
    }

    public void shapeshift(final Card cursed,
                           final Card victim,
                           final ArrayList<Card> affectedRow) {

        int health = victim.getHealth();
        victim.setHealth(victim.getAttackDamage());
        victim.setAttackDamage(health);

        if (victim.getHealth() == 0) {
            affectedRow.remove(victim);
        }

        cursed.setHasAttackedThisTurn(true);
    }

    public void godsPlan(final Card disciple, final Card ally) {
        ally.setHealth(ally.getHealth() + 2);

        disciple.setHasAttackedThisTurn(true);
    }

    public void subZero(final Card hero, final ArrayList<Card> affectedRow) {
        Card maxAttackCard = new Minion();
        maxAttackCard.setAttackDamage(-1);

        for (Card card : affectedRow) {
            if (card.getAttackDamage() > maxAttackCard.getAttackDamage()) {
                maxAttackCard = card;
            }
        }

        maxAttackCard.setStunned(true);
        hero.setHasAttackedThisTurn(true);
    }

    public void lowBlow(final Card hero, final ArrayList<Card> affectedRow) {
        Card maxHealthCard = new Minion();
        maxHealthCard.setHealth(-1);

        for (Card card : affectedRow) {
            if (card.getHealth() > maxHealthCard.getHealth()) {
                maxHealthCard = card;
            }
        }

        affectedRow.remove(maxHealthCard);
        hero.setHasAttackedThisTurn(true);
    }

    public void earthBorn(final Card hero, final ArrayList<Card> affectedRow) {
        for (Card card : affectedRow) {
            card.setHealth(card.getHealth() + 1);
        }

        hero.setHasAttackedThisTurn(true);
    }

    public void bloodThirst(final Card hero, final ArrayList<Card> affectedRow) {
        for (Card card : affectedRow) {
            card.setAttackDamage(card.getAttackDamage() + 1);
        }

        hero.setHasAttackedThisTurn(true);
    }
}
